package com.mealPrep.mealPrep.service;


import com.mealPrep.mealPrep.dto.CommentRequestDTO;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.dto.CommentResponseDTO;
import com.mealPrep.mealPrep.dto.CommentUpdateRequestDTO;
import com.mealPrep.mealPrep.repository.CommentRepository;
import com.mealPrep.mealPrep.repository.BoardRepository;
import com.mealPrep.mealPrep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO request){
        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());
        Board board = boardRepository.findByBoardId(boardId);

        List<Comment> comments = commentRepository.findAllByBoardId(board);
        Comment comment = null;
        if(comments.isEmpty()){ //댓글이 아무것도 안달려있을때
            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .answerNumber(0L)
                    .parentNum(0L)
                    .ref(1L)
                    .refOrder(0L)
                    .step(0L)
                    .build();
        }else{  //댓글이 하나라도 있을 경우
            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .answerNumber(0L)
                    .parentNum(0L)
                    .ref(comments.size()+1L)
                    .refOrder(0L)
                    .step(0L)
                    .build();
        }

        commentRepository.save(comment);

        CommentResponseDTO commentResponse = CommentResponseDTO.builder()
                .commentId(comment.getId())
                .nickname(memberId.getNickname())
                .boardId(comment.getBoardId().getBoardId())
                .status(comment.getCommentState())
                .ref(comment.getRef())
                .refOrder(comment.getRefOrder())
                .step(comment.getStep())
                .answerNum(comment.getAnswerNumber())
                .parentNum(comment.getParentNum())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();

        return commentResponse;
    }

    /**
     * 대댓글 작성
     */
    @Transactional
    public CommentResponseDTO createRecomment(Long parentNum,Long boardId,CommentRequestDTO request){
        Optional<Comment> byId = commentRepository.findById(parentNum);
        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());
        Board board = boardRepository.findByBoardId(boardId);
        Comment comment = null;
        if(byId.isPresent()){
            Comment parentComment = byId.get();   //부모 댓글
            Long reCommentStep = parentComment.getStep()+1L;

            List<Comment> allByRef = commentRepository.findAllByRef(parentComment.getRef());    //부모 댓글의 그룹 댓글 리스트

            Long childAnswerNum=0L;
            Long maxStep = Long.MIN_VALUE;
            for (Comment comment1 : allByRef) {
                childAnswerNum+=comment1.getAnswerNumber();
                if (maxStep < comment1.getStep()) {
                    maxStep = comment1.getStep(); //부모 댓글의 그룹내의 step컬럼 최댓값
                }
            }

            Long refOrder =0L;
            if(reCommentStep>maxStep){
                //저장할 대댓글의 step이 댓글의 그룹내에서 최대 step보다 크다면
                //부모댓글의 그룹내의 순서보다 큰 refOrder는 모두 +1 더해줍니다.
                List<Comment> comments = commentRepository.findAllByBoardIdAndRefOrderIsGreaterThan(board,parentComment.getRefOrder());
                for (Comment comment1 : comments) {
                    refOrder+=refOrder+1L;
                    comment1.setRefOrder(comment1.getRefOrder()+1L);
                    commentRepository.save(comment1);
                }
                refOrder= refOrder+1L;
            }else if(maxStep == reCommentStep){
                //저장할 대댓글의 step이 댓글의 그룹내에서 최대 step 같다면
                //부모댓글의 그룹내의 순서와 자식댓글을 더한값 보다 큰 refOrder는 모두 +1 컬럼을 업데이트
                List<Comment> allByBoardIdAndRefOrderIsGreaterThan = commentRepository.findAllByBoardIdAndRefOrderIsGreaterThan(board, parentComment.getRefOrder() + parentComment.getAnswerNumber());
                for (Comment comment1 : allByBoardIdAndRefOrderIsGreaterThan) {
                    comment1.setRefOrder(comment1.getRefOrder()+1L);
                    commentRepository.save(comment1);
                }
                refOrder = refOrder + childAnswerNum + 1L;
            }else{
                //저장할 대댓글의 step이 댓글의 그룹내에서 최대 step보다 작다면
                //이 그룹내의 합산한 자식수의 댓글에 +1 을하여 refOrder가 정해집니다.
                //refOrder는 answerNumSum + 1L;
                refOrder = childAnswerNum + 1L;
            }

            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .step(reCommentStep)
                    .parentNum(parentComment.getId())
                    .ref(parentComment.getRef())
                    .refOrder(refOrder)
                    .answerNumber(0L)
                    .build();
            //부모댓글 answerNum +1씩
           parentComment.setAnswerNumber(parentComment.getAnswerNumber()+1L);
        }
        commentRepository.save(comment);

        assert comment != null;
        CommentResponseDTO commentResponse = CommentResponseDTO.builder()
                .commentId(comment.getId())
                .nickname(memberId.getNickname())
                .boardId(comment.getBoardId().getBoardId())
                .status(comment.getCommentState())
                .ref(comment.getRef())
                .refOrder(comment.getRefOrder())
                .step(comment.getStep())
                .answerNum(comment.getAnswerNumber())
                .parentNum(comment.getParentNum())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();

        return commentResponse;
    }
    /**
     * 댓글 삭제
     */
    @Transactional
    public Boolean deleteComment(Long commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if(byId.isPresent()){
            Comment comment = byId.get();
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updateComment(Long commentId, CommentUpdateRequestDTO request) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if(byId.isPresent()){
            Comment comment = byId.get();
            comment.setComment(request.getComment());
            comment.setCommentState(request.getStatus());
            return true;
        }
        return false;
    }
}

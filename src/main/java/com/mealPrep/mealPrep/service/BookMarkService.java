package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.BookMark;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.dto.BookmarkRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeFindResponseDTO;
import com.mealPrep.mealPrep.repository.BoardRepository;
import com.mealPrep.mealPrep.repository.BookMarkRepository;
import com.mealPrep.mealPrep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public Boolean createBookmark(BookmarkRequestDTO request) {
        Optional<Member> byId = memberRepository.findById(request.getMemberId());
        if(byId.isPresent()){
            Member member = byId.get();
            Board board = boardRepository.findByBoardId(request.getBoardId());
            board.setLikes(board.getLikes()+1L);
            BookMark bookMark = new BookMark();
            bookMark.setBoard(board);
            bookMark.setMemberId(member.getUserId());
            bookMarkRepository.save(bookMark);
            boardRepository.save(board);
            return true;
        }
        return false;
    }

    public List<RecipeFindResponseDTO> getLikeBoards(Long id) {
        List<BookMark> allByMemberId = bookMarkRepository.findAllByMemberId(id);
        List<RecipeFindResponseDTO> responses = new ArrayList<>();
        for (BookMark bookMark : allByMemberId) {
            Board board = boardRepository.findByBoardId(bookMark.getBoard().getBoardId());
            RecipeFindResponseDTO response = RecipeFindResponseDTO.builder()
                    .recipeId(board.getBoardId())
                    .author(board.getAuthor())
                    .view(board.getView())
                    .title(board.getTitle())
                    .time(board.getCreatedAt())
                    .build();

            responses.add(response);
        }
        return responses;
    }
}

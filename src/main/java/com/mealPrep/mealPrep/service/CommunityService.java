package com.mealPrep.mealPrep.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.domain.*;
import com.mealPrep.mealPrep.dto.CommunityRequestDTO;
import com.mealPrep.mealPrep.dto.CommunityResponseDTO;
import com.mealPrep.mealPrep.dto.CommunityViewDTO;
import com.mealPrep.mealPrep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final BoardRepository boardRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final FirebaseService firebaseService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDTO request, MultipartFile file) throws IOException, FirebaseAuthException {
        Member findMember = memberRepository.findOneByMemberId(request.getMemberId());
        findMember.setReward(findMember.getReward()+10L);
        Community community = new Community();
        community.setAuthor(findMember.getNickname());
        community.setMember(findMember);
        community.setLikes(0L);
        community.setView(0L);
        community.setBody(request.getBody());
        community.setTitle(request.getTitle());
        boardRepository.save(community);

        CommunityResponseDTO communityResponseDTO;

        if(file!=null){
            String imgUrl = firebaseService.uploadFiles(file,community.getBoardId().toString());
            Image image = new Image();
            image.setImage_url(imgUrl);
            image.setId(community.getBoardId());
            image.setBoardId(community);
            imageRepository.save(image);

            communityResponseDTO = CommunityResponseDTO.builder()
                    .boardId(community.getBoardId())
                    .title(community.getTitle())
                    .body(community.getBody())
                    .imgUrl(imgUrl)
                    .createdAt(community.getCreatedAt())
                    .build();
        }else{
            communityResponseDTO = CommunityResponseDTO.builder()
                    .boardId(community.getBoardId())
                    .title(community.getTitle())
                    .body(community.getBody())
                    .createdAt(community.getCreatedAt())
                    .build();
        }
        return communityResponseDTO;
    }

    @Transactional
    public List<CommunityViewDTO> getAllCommunity() {
        List<Community> allByOrderByCreatedAtDesc = communityRepository.findAllByOrderByCreatedAtDesc();
        List<CommunityViewDTO> communityViewDTOs = new ArrayList<>();

        for (Community community : allByOrderByCreatedAtDesc) {
            CommunityViewDTO communityViewDto = new CommunityViewDTO (
                    community.getTitle(),
                    community.getMember(),
                    community.getBoardId(),
                    community.getLikes(),
                    community.getCreatedAt()
            );

            communityViewDTOs.add(communityViewDto);
        }

        return communityViewDTOs;
    }

    @Transactional
    public Optional<Board> getCommunity(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        return findBoard;
    }

    @Transactional
    public void deleteCommunity(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}

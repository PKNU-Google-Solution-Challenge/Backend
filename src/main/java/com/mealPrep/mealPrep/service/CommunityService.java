package com.mealPrep.mealPrep.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Community;
import com.mealPrep.mealPrep.domain.Image;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.dto.CommunityRequestDto;
import com.mealPrep.mealPrep.dto.CommunityResponseDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteResponseDTO;
import com.mealPrep.mealPrep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final BoardRepository communityRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final FirebaseService firebaseService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommunityResponseDTO createCommunity(CommunityRequestDto request, MultipartFile file) throws IOException, FirebaseAuthException {
        Member findMember = memberRepository.findOneByMemberId(request.getMemberId());

        Community community = new Community();
        community.setAuthor(findMember.getNickname());
        community.setMember(findMember);
        community.setLikes(0L);
        community.setView(0L);
        community.setBody(request.getBody());
        community.setTitle(request.getAuthor());
        communityRepository.save(community);

        firebaseService.uploadFiles(file,community.getBoardId().toString());

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
}

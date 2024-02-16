package com.mealPrep.mealPrep.controller;

import com.google.api.Http;
import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Community;
import com.mealPrep.mealPrep.dto.CommunityRequestDTO;
import com.mealPrep.mealPrep.dto.CommunityResponseDTO;
import com.mealPrep.mealPrep.dto.CommunityViewDTO;
import com.mealPrep.mealPrep.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/community/write")
    public ResponseEntity writeCommunity(@RequestPart(value = "image",required = false) MultipartFile file,
                                      @Validated @RequestPart(value = "request") CommunityRequestDTO request) throws IOException, FirebaseAuthException {
        try{
            CommunityResponseDTO community = communityService.createCommunity(request, file);
            return new ResponseEntity(Response.success(community), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/community")
    public ResponseEntity getAllCommunity(){
        try{
            List<CommunityViewDTO> communitys = communityService.getAllCommunity();
            return new ResponseEntity(Response.success(communitys), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/community/{boardId}")
    public ResponseEntity getCommunity(@Validated @PathVariable(value = "boardId") Long boardId){
        try{
            Optional<Board> community = communityService.getCommunity(boardId);
            return new ResponseEntity(Response.success(community), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/community/{boardId}")
    public ResponseEntity deleteCommunity(@Validated @PathVariable(value = "boardId") Long boardId){
        try{
            communityService.deleteCommunity(boardId);
            return new ResponseEntity(Response.success(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
}

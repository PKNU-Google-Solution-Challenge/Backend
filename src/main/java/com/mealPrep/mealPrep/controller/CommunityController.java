package com.mealPrep.mealPrep.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.dto.CommunityRequestDto;
import com.mealPrep.mealPrep.dto.CommunityResponseDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteResponseDTO;
import com.mealPrep.mealPrep.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/community/write")
    public ResponseEntity writeCommunity(@RequestPart(value = "image",required = false) MultipartFile file,
                                      @Validated @RequestPart(value = "request") CommunityRequestDto request) throws IOException, FirebaseAuthException {
        try{
            CommunityResponseDTO community = communityService.createCommunity(request, file);
            return new ResponseEntity(Response.success(community), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
}

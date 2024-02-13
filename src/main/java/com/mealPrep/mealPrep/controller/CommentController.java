package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.dto.CommentRequestDTO;
import com.mealPrep.mealPrep.dto.CommentResponseDTO;
import com.mealPrep.mealPrep.dto.CommentUpdateRequestDTO;
import com.mealPrep.mealPrep.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @Operation(summary = "댓글 달기")
    @PostMapping("/comment/{boardId}")
    public ResponseEntity createComment(@PathVariable Long boardId, @RequestBody CommentRequestDTO request){
        try{
            CommentResponseDTO comment = commentService.createComment(boardId, request);
            return new ResponseEntity(Response.success(comment),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "대댓글 달기")
    @PostMapping("/comment/{boardId}/{parentId}")
    public ResponseEntity createComment(@PathVariable Long boardId,@PathVariable Long parentId, @RequestBody CommentRequestDTO request){
        try{
            CommentResponseDTO comment = commentService.createRecomment(parentId, boardId, request);
            return new ResponseEntity(Response.success(comment),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "댓글 삭제")
    @GetMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        try{
            Boolean comment = commentService.deleteComment(commentId);
            return new ResponseEntity(Response.success(comment),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "댓글 수정")
    @PostMapping("/comment/update/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequestDTO request){
        try{
            Boolean comment = commentService.updateComment(commentId,request);
            return new ResponseEntity(Response.success(comment),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
}

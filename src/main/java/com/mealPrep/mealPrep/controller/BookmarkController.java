package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.dto.BookmarkRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeFindResponseDTO;
import com.mealPrep.mealPrep.service.BookMarkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookMarkService bookMarkService;
    @Operation(summary = "좋아요 표시하기")
    @PostMapping("/bookmark/like")
    public ResponseEntity makeLike(@RequestBody BookmarkRequestDTO request){
        try{
            Boolean b = bookMarkService.createBookmark(request);
            return new ResponseEntity(Response.success(b),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "내가 좋아요한 레시피 보기")
    @PostMapping("/bookmark/recipes/likes/{id}")
    public ResponseEntity getLikeBoards(@RequestParam Long id){
        try{
            List<RecipeFindResponseDTO> response= bookMarkService.getLikeBoards(id);
            return new ResponseEntity(Response.success(response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(), HttpStatus.BAD_REQUEST);
        }
    }
}

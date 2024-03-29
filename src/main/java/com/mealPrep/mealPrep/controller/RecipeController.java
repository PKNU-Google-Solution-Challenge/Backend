package com.mealPrep.mealPrep.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.dto.RecipeFindResponseDTO;
import com.mealPrep.mealPrep.dto.RecipeViewDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteResponseDTO;
import com.mealPrep.mealPrep.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Configuration
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @Operation(summary = "레시피 작성")
    @PostMapping("/recipe/write")
    public ResponseEntity writeRecipe(@RequestPart(value = "image",required = false) MultipartFile file,
                                      @Validated  @RequestPart RecipeWriteRequestDTO request) throws IOException, FirebaseAuthException {
        try{
            RecipeWriteResponseDTO recipe = recipeService.createRecipe(request,file);
            return new ResponseEntity(Response.success(recipe), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "레시피 글 조회")
    @GetMapping("/recipe/recipes")
    public ResponseEntity getRecipes(){
        try{
            List<RecipeViewDTO> recipes = recipeService.getRecipes();
            return new ResponseEntity(Response.success(recipes),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "선택한 레시피 조회")
    @GetMapping("/recipe/{id}")
    public ResponseEntity getRecipe(@PathVariable Long id){
        try{
            RecipeViewDTO recipe = recipeService.getRecipe(id);
            return new ResponseEntity(Response.success(recipe),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "레시피 수정")
    @PostMapping("/recipe/update/{id}")
    public ResponseEntity updateRecipe(@RequestPart(value = "image",required = false) MultipartFile file,@PathVariable Long id,
                                      @Validated  @RequestPart RecipeWriteRequestDTO request) throws IOException, FirebaseAuthException {
        try{
            RecipeWriteResponseDTO recipe = recipeService.updateRecipe(id,request,file);
            return new ResponseEntity(Response.success(recipe), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "레시피 삭제")
    @DeleteMapping("/recipe/delete/{id}")
    public ResponseEntity deleteRecipe(@PathVariable Long id){
        try{
            Boolean b = recipeService.deleteRecipe(id);
            return new ResponseEntity(Response.success(b),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "작성한 레시피 확인")
    @GetMapping("/recipe/member/{id}")
    public ResponseEntity findRecipe(@PathVariable Long id){
        try{
            List<RecipeFindResponseDTO> response = recipeService.findRecipeByMemberId(id);
            return new ResponseEntity(Response.success(response),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(Response.failure(),HttpStatus.BAD_REQUEST);
        }
    }
}

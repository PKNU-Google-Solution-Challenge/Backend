package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Configuration
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @Operation(summary = "글 작성")
    @PostMapping("/recipe/write")
    public ResponseEntity writeRecipe(@RequestBody RecipeWriteRequestDTO request){
        Long recipe = recipeService.createRecipe(request);
        return new ResponseEntity(Response.success(recipe), HttpStatus.OK);
    }
}

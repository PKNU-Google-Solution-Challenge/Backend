package com.mealPrep.mealPrep.Controller;

import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Configuration
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    @Operation(summary = "글 작성")
    @PostMapping("/recipe/write")
    public ResponseEntity writeRecipe(@RequestPart RecipeWriteRequestDTO recipe){
        Long recipeNum = recipeService.createRecipe(recipe);
        return new ResponseEntity(Response.success(recipeNum), HttpStatus.OK);
    }
}

package com.mealPrep.mealPrep.Controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.common.Response;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteResponseDTO;
import com.mealPrep.mealPrep.service.FirebaseService;
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

@Configuration
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
//    private final FirebaseService firebaseService;
    @Operation(summary = "글 작성")
    @PostMapping("/recipe/write")
    public ResponseEntity writeRecipe(@RequestPart(value = "image",required = false) MultipartFile file,
                                      @Validated  @RequestPart RecipeWriteRequestDTO request) throws IOException, FirebaseAuthException {
        RecipeWriteResponseDTO recipe = recipeService.createRecipe(request,file);
//        String imgUrl = firebaseService.uploadFiles(file,recipe.getBoardId().toString());
//        imageService.uploadImg(recipe.getBoardId(),imgUrl);

        return new ResponseEntity(Response.success(recipe), HttpStatus.OK);
    }
}

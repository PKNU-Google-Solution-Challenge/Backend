package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Configuration
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {
    @Operation(summary = "글 작성")
    @PostMapping("/recipe/write")
    public ResponseEntity writeRecipe(){
        return new ResponseEntity(Response.success(null), HttpStatus.OK);
    }
}

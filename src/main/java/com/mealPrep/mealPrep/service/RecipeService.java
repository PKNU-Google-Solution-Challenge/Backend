package com.mealPrep.mealPrep.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.domain.*;
import com.mealPrep.mealPrep.dto.RecipeViewDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.dto.RecipeWriteResponseDTO;
import com.mealPrep.mealPrep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final MemberRepository memberRepository;
    private final FirebaseService firebaseService;
    private final ImageRepository imageRepository;
    @Transactional
    public RecipeWriteResponseDTO createRecipe(RecipeWriteRequestDTO request, MultipartFile file) throws IOException, FirebaseAuthException {

        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : request.getIngredients()) {
            Ingredient ingredient = new Ingredient().builder()
                    .name(s)
                    .build();
            ingredients.add(ingredient);
        }

        ingredientRepository.saveAll(ingredients);

        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());

        Recipe recipe = new Recipe();
        recipe.setCategory(request.getCategory()); // category 필드에 대한 setter 메서드를 호출하여 값을 설정합니다.
        recipe.setPrice(request.getTotalPrice());
        recipe.setAuthor(memberId.getNickname());
        recipe.setCalorie(request.getTotalKcal());
        recipe.setCooking_time(request.getTotalTime());
        recipe.setLikes(0L);
        recipe.setView(0L);
        recipe.setBody(request.getBody());
        recipe.setTitle(request.getTitle());
        recipe.setMember(memberId);

        recipeRepository.save(recipe);

        // RecipeIngredient 엔티티 생성 및 저장
        for (Ingredient ingredient : ingredients) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredientRepository.save(recipeIngredient);
        }

        RecipeWriteResponseDTO recipeWriteResponseDTO;
        //Image 업로드
        if(file!=null){
            String imgUrl = firebaseService.uploadFiles(file,recipe.getBoardId().toString());
            Image image = new Image();
            image.setImage_url(imgUrl);
            image.setId(recipe.getBoardId());
            imageRepository.save(image);

            recipeWriteResponseDTO = RecipeWriteResponseDTO.builder()
                    .boardId(recipe.getBoardId())
                    .title(recipe.getTitle())
                    .body(recipe.getBody())
                    .imgUrl(imgUrl)
                    .createdAt(recipe.getCreatedAt())
                    .build();
        }else{
            recipeWriteResponseDTO = RecipeWriteResponseDTO.builder()
                    .boardId(recipe.getBoardId())
                    .title(recipe.getTitle())
                    .body(recipe.getBody())
                    .createdAt(recipe.getCreatedAt())
                    .build();
        }
        return recipeWriteResponseDTO;
    }
    @Transactional
    public List<RecipeViewDTO> getRecipes(){
        List<Recipe> orderByCreatedAtDesc = recipeRepository.findAllByOrderByCreatedAtDesc();
        ArrayList<RecipeViewDTO> recipeViewDTOS = new ArrayList<>();
        for (Recipe recipe : orderByCreatedAtDesc) {
            RecipeViewDTO recipeViewDTO = new RecipeViewDTO();
            recipeViewDTO.setAuthor(recipe.getAuthor());
        }
        return null;
    }
}
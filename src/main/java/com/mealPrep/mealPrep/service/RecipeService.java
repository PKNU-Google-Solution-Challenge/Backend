package com.mealPrep.mealPrep.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mealPrep.mealPrep.domain.*;
import com.mealPrep.mealPrep.dto.*;
import com.mealPrep.mealPrep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final BoardRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final MemberRepository memberRepository;
    private final FirebaseService firebaseService;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
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
        memberId.setReward(memberId.getReward()+10L);
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
            image.setBoardId(recipe);
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
            recipeViewDTO.setBody(recipe.getBody());
            recipeViewDTO.setLikes(recipe.getLikes());
            recipeViewDTO.setViews(recipe.getView());
            recipeViewDTO.setCategory(recipe.getCategory());
            recipeViewDTO.setTitle(recipe.getTitle());
            recipeViewDTO.setTotalKcal(recipe.getCalorie());
            recipeViewDTO.setBoardId(recipe.getBoardId());
            if(recipe.getImages()!=null){
                recipeViewDTO.setImgUrl(recipe.getImages().getImage_url());
            }
            recipeViewDTO.setCreatedAt(recipe.getCreatedAt());
            recipeViewDTO.setTotalKcal(recipe.getCalorie());
            recipeViewDTO.setTotalPrice(recipe.getPrice());
            recipeViewDTO.setTotalTime(recipe.getCooking_time());
            ArrayList<String> ingredients = new ArrayList<>();
            List<RecipeIngredient> allByRecipeId = recipeIngredientRepository.findAllByRecipe_BoardId(recipe.getBoardId());

            for (RecipeIngredient l : allByRecipeId) {
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(l.getIngredient().getId());
                if (optionalIngredient.isPresent()) {
                    Ingredient ingredient = optionalIngredient.get();
                    ingredients.add(ingredient.getName());
                } else {
                    throw new IllegalStateException();
                }
            }
            recipeViewDTO.setIngredients(ingredients);

            recipeViewDTOS.add(recipeViewDTO);
        }
        return recipeViewDTOS;
    }

    /**
     * 선택한 레시피 조회
     */
    @Transactional
    public RecipeViewDTO getRecipe(Long id) {
        Recipe recipe = (Recipe) recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 아이디 입니다")
        );
        recipe.setView(recipe.getView()+1);
        recipeRepository.save(recipe);

        RecipeViewDTO recipeViewDTO = new RecipeViewDTO();
        recipeViewDTO.setAuthor(recipe.getAuthor());
        recipeViewDTO.setBody(recipe.getBody());
        recipeViewDTO.setLikes(recipe.getLikes());
        recipeViewDTO.setViews(recipe.getView());
        recipeViewDTO.setCategory(recipe.getCategory());
        recipeViewDTO.setTitle(recipe.getTitle());
        recipeViewDTO.setTotalKcal(recipe.getCalorie());
        recipeViewDTO.setBoardId(recipe.getBoardId());
        if(recipe.getImages()!=null){
            recipeViewDTO.setImgUrl(recipe.getImages().getImage_url());
        }
        recipeViewDTO.setCreatedAt(recipe.getCreatedAt());
        recipeViewDTO.setTotalKcal(recipe.getCalorie());
        recipeViewDTO.setTotalPrice(recipe.getPrice());
        recipeViewDTO.setTotalTime(recipe.getCooking_time());
        ArrayList<String> ingredients = new ArrayList<>();
        List<RecipeIngredient> allByRecipeId = recipeIngredientRepository.findAllByRecipe_BoardId(recipe.getBoardId());

        for (RecipeIngredient l : allByRecipeId) {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(l.getIngredient().getId());
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
                ingredients.add(ingredient.getName());
            } else {
                throw new IllegalStateException();
            }
        }
        recipeViewDTO.setIngredients(ingredients);

        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByBoardId(recipe);
        for (Comment comment : comments) {
            CommentResponseDTO commentResponse = CommentResponseDTO.builder()
                    .commentId(comment.getId())
                    .nickname(recipe.getMember().getNickname())
                    .boardId(comment.getBoardId().getBoardId())
                    .status(comment.getCommentState())
                    .ref(comment.getRef())
                    .refOrder(comment.getRefOrder())
                    .step(comment.getStep())
                    .answerNum(comment.getAnswerNumber())
                    .parentNum(comment.getParentNum())
                    .comment(comment.getComment())
                    .createdAt(comment.getCreatedAt())
                    .build();
            commentResponseDTOS.add(commentResponse);
        }
        recipeViewDTO.setComments(commentResponseDTOS);
        return recipeViewDTO;
    }

    @Transactional
    public RecipeWriteResponseDTO updateRecipe(Long id, RecipeWriteRequestDTO request, MultipartFile file) throws IOException, FirebaseAuthException{
        Recipe recipe = (Recipe) recipeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물 입니다")
        );
        recipe.update(request);
        String imgUrl = null;
        //이미지 수정
        if(file!=null){
            imgUrl=firebaseService.uploadFiles(file, recipe.getBoardId().toString());
            Optional<Image> byBoardId = imageRepository.findByBoardId(recipe);
            if(byBoardId.isPresent()){
                Image image = byBoardId.get();
                image.setImage_url(imgUrl);
            }
        }
        //재료 수정
        List<RecipeIngredient> allByRecipeBoardId = recipeIngredientRepository.findAllByRecipe_BoardId(id);
        for (RecipeIngredient recipeIngredient : allByRecipeBoardId) {
            ingredientRepository.delete(recipeIngredient.getIngredient());
        }
        recipeIngredientRepository.deleteAll(allByRecipeBoardId);

        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : request.getIngredients()) {
            Ingredient ingredient = new Ingredient().builder()
                    .name(s)
                    .build();
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);

        for (Ingredient ingredient : ingredients) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredientRepository.save(recipeIngredient);
        }
        RecipeWriteResponseDTO recipeWriteResponseDTO;
        if(file!=null) {
            recipeWriteResponseDTO
                    = RecipeWriteResponseDTO.builder()
                    .boardId(recipe.getBoardId())
                    .title(recipe.getTitle())
                    .body(recipe.getBody())
                    .imgUrl(imgUrl)
                    .createdAt(recipe.getCreatedAt())
                    .build();
        }else{
            recipeWriteResponseDTO
                    = RecipeWriteResponseDTO.builder()
                    .boardId(recipe.getBoardId())
                    .title(recipe.getTitle())
                    .body(recipe.getBody())
                    .createdAt(recipe.getCreatedAt())
                    .build();
        }
        return recipeWriteResponseDTO;
    }

    @Transactional
    public Boolean deleteRecipe(Long id) {
        List<RecipeIngredient> allByRecipeBoardId = recipeIngredientRepository.findAllByRecipe_BoardId(id);
        for (RecipeIngredient recipeIngredient : allByRecipeBoardId) {
            ingredientRepository.delete(recipeIngredient.getIngredient());
        }
        recipeIngredientRepository.deleteAll(allByRecipeBoardId);

        Optional<Board> byId = recipeRepository.findById(id);
        if(byId.isPresent()){
            Recipe recipe = (Recipe) byId.get();
            recipeRepository.delete(recipe);
            imageRepository.deleteByBoardId(recipe);
            List<Comment> comments = commentRepository.findAllByBoardId(recipe);
            commentRepository.deleteAll(comments);
            return true;
        }
        return false;
    }

    public List<RecipeFindResponseDTO> findRecipeByMemberId(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        ArrayList<RecipeFindResponseDTO> recipeFindResponseDTOS = new ArrayList<>();
        if(byId.isPresent()){
            Member member = byId.get();
            List<Recipe> recipes = recipeRepository.findAllByMember(member);
            for (Recipe recipe : recipes) {
                RecipeFindResponseDTO response = RecipeFindResponseDTO.builder()
                        .view(recipe.getView())
                        .author(member.getNickname())
                        .time(recipe.getCreatedAt())
                        .recipeId(recipe.getBoardId())
                        .title(recipe.getTitle())
                        .build();
                recipeFindResponseDTOS.add(response);
            }
        }
        return recipeFindResponseDTOS;
    }
}
package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.converters.RecipeCommandToRecipe;
import dev.gwozdz.DemoRecipe.converters.RecipeToRecipeCommand;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;



@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;
    private final RecipeToRecipeCommand recipeToRecipeCommandConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipeConverter, RecipeToRecipeCommand recipeToRecipeCommandConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
        this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new LinkedHashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(long id) {
        Optional<Recipe> receivedRecipeOptional = recipeRepository.findById(id);
        if(receivedRecipeOptional.isEmpty()){
            throw new RuntimeException("Id not found.");
        } else {
            return receivedRecipeOptional.get();
        }
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe convertedRecipe = recipeCommandToRecipeConverter.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(convertedRecipe);
        return recipeToRecipeCommandConverter.convert(savedRecipe);
    }
}

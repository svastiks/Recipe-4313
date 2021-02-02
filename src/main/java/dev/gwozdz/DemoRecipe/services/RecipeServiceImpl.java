package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
            throw new IllegalArgumentException("Id not found.");
        } else {
            return receivedRecipeOptional.get();
        }
    }
}

package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;
    @Mock
    private RecipeService recipeService;


    @Test
    void getIndexPageShouldReturnProperString(@Mock Model model) {
        //given
        //when
        String returnMsg = indexController.getIndexPage(model);
        //then
        assertThat(returnMsg, equalTo("index"));
    }

    @Test
    void getIndexShouldInvokeGetAllRecipesOnRecipeService(@Mock Model model){
        //given
        //when
        indexController.getIndexPage(model);
        //then
        then(recipeService).should().getAllRecipes();
    }


    private Set<Recipe> getTestRecipes(){
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe;
        for(int i=0; i<5; i++){
            recipe = new Recipe();
            recipe.setDescription("Test recipe no. " + (i+1));
            recipes.add(recipe);
        }
        return recipes;
    }
}
package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;
    @Mock
    private RecipeService recipeService;
    @Captor
    private ArgumentCaptor <Set<Recipe>> argumentCaptor;


    @Test
    void getIndexPageShouldReturnProperString(@Mock Model model) {
        //given
        //when
        String returnMsg = indexController.getIndexPage(model);
        //then
        assertThat(returnMsg, equalTo("index"));
    }

    @Test
    void getIndexShouldGetAllRecipesOnRecipeService(@Mock Model model){
        //given
        //when
        indexController.getIndexPage(model);
        //then
        then(recipeService).should().getAllRecipes();
    }

    @Test
    void getIndexShouldAddAttributeOnModel(@Mock Model model){
        //given
        //when
        indexController.getIndexPage(model);
        //then
        then(model).should().addAttribute(anyString(), any());
    }

    @Test
    void getIndexShouldSendProperArgumentsToAddAttributeOnModel(@Mock Model model){
        //given
        Set<Recipe> givenRecipes = getTestRecipes();
        given(recipeService.getAllRecipes()).willReturn(givenRecipes);
        //when
        indexController.getIndexPage(model);
        //then
        then(model).should().addAttribute(eq("recipes"), argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), hasSize(5));
        assertThat(argumentCaptor.getValue(), equalTo(givenRecipes));
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
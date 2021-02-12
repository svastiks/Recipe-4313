package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @InjectMocks
    private IngredientController ingredientController;
    @Mock
    private RecipeService recipeService;

    @Test
    void showIngredientsShouldReturnProperName(@Mock Model model){
        //given
        String generatedTemplateName = "";
        //when
        generatedTemplateName = ingredientController.showIngredients("1", model);
        //then
        assertThat(generatedTemplateName, equalTo("recipe/ingredient/list"));
    }

    @Test
    void showIngredientsOnRequestShouldGetProperRecipe() throws Exception{
        //given
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        given(recipeService.getRecipeCommandById(anyLong())).willReturn(recipeCommandGiven);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeHasNoErrors("recipe"));
        //then
        verify(recipeService, only()).getRecipeCommandById(anyLong());
    }
}
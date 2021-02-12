package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import javax.script.AbstractScriptEngine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Test
    void showByIdShouldReturnProperName(@Mock Model model) {
        //given
        String generatedTemplateName = "";
        given(recipeService.getRecipeById(anyLong())).willReturn(new Recipe());
        //when
        generatedTemplateName = recipeController.showById("1", model);
        //then
        assertThat(generatedTemplateName, equalTo("recipe/show"));
    }

    @Test
    void showByIdOnRequestShouldAddProperRecipeToModel() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        given(recipeService.getRecipeById(anyLong())).willReturn(recipe);
        //when
        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", recipe))
                .andExpect(model().attributeHasNoErrors("recipe"));
    }

    @Test
    void newRecipeShouldReturnProperName(@Mock Model model){
        //given
        String generatedTemplateName="";
        //when
        generatedTemplateName = recipeController.newRecipe(model);
        //then
        assertEquals("recipe/recipeform", generatedTemplateName);
    }

    @Test
    void newRecipeOnRequestShouldAddNewRecipeCommandToModel() throws Exception{
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        //when
        //then
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeHasNoErrors("recipe"));
    }

    @Test
    void updateRecipeShouldReturnProperName(@Mock Model model){
        //given
        String generatedTemplateName ="";
        //when
        generatedTemplateName = recipeController.updateRecipe("1", model);
        //then
        assertEquals("recipe/recipeform", generatedTemplateName);
    }

    @Test
    void updateRecipeShouldAddProperRecipe() throws Exception{
        //given
        RecipeCommand recipeGiven = new RecipeCommand();
        recipeGiven.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        given(recipeService.getRecipeCommandById(1l)).willReturn(recipeGiven);
        //when
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", recipeGiven))
                .andExpect(model().attributeHasNoErrors("recipe"));
    }

    @Test
    void saveOrUpdateShouldReturnProperName(){
        //given
        String generatedTemplateName = "";
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        RecipeCommand recipeCommandSavedGiven = new RecipeCommand();
        recipeCommandSavedGiven.setId(1l);
        given(recipeService.saveRecipeCommand(recipeCommandGiven)).willReturn(recipeCommandSavedGiven);
        //when
        generatedTemplateName = recipeController.saveOrUpdate(recipeCommandGiven);
        //then
        assertEquals("redirect:/recipe/1/show",generatedTemplateName);
    }

    @Test
    void saveOrUpdateOnPostShouldRedirectToProperView() throws Exception{
        //given
        RecipeCommand recipeCommandSavedGiven = new RecipeCommand();
        recipeCommandSavedGiven.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        given(recipeService.saveRecipeCommand(any())).willReturn(recipeCommandSavedGiven);
        //when
        //then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "test description")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));
    }

    @Test
    void deleteRecipeByIdShouldFetchProperId() throws Exception{
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        //when
        //then
        mockMvc.perform(get("/recipe/2/delete"))
                .andExpect(status().is3xxRedirection());
        verify(recipeService).deleteRecipeById(2);
    }
}
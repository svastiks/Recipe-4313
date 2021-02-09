package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Test
    void shouldGenerateProperTemplateName(@Mock Model model) {
        //given
        String generatedTemplateName = "";
        given(recipeService.getRecipeById(anyLong())).willReturn(new Recipe());
        //when
        generatedTemplateName = recipeController.showById("1", model);
        //then
        assertThat(generatedTemplateName, equalTo("recipe/show"));
    }

    @Test
    void shouldGenerateProperTemplateName() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        given(recipeService.getRecipeById(anyLong())).willReturn(recipe);
        //when
        //then
        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

    }

}
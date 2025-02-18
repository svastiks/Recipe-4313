package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.services.IngredientService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import dev.gwozdz.DemoRecipe.services.UnitOfMeasureService;
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

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @InjectMocks
    private IngredientController ingredientController;
    @Mock
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private MockMvc mockMvc;

    @Test
    void showIngredientsShouldReturnProperName(@Mock Model model) {
        String generatedTemplateName = ingredientController.showIngredients("1", model);
        assertThat(generatedTemplateName, equalTo("recipe/ingredient/list"));
    }

    @Test
    void showIngredientsOnRequestShouldGetProperRecipe() throws Exception {
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        IngredientCommand ingredientCommand = generateTestIngredientCommand();
        given(recipeService.getRecipeCommandById(anyLong())).willReturn(recipeCommandGiven);
        given(ingredientService.getNewIngredientCommandWithRecipeId(anyLong())).willReturn(ingredientCommand);
        given(unitOfMeasureService.getAllUnitsOfMeasureCommands()).willReturn(new HashSet<>());

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe", "ingredientNew", "uoms"))
                .andExpect(model().attributeHasNoErrors("recipe"));

        verify(recipeService, only()).getRecipeCommandById(anyLong());
    }

    @Test
    void addIngredientShouldReturnProperName() {
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);

        String generatedTemplateName = ingredientController.addIngredient(givenIngredientCommand);
        assertThat(generatedTemplateName, equalTo("redirect:/recipe/3/ingredients"));
    }

    @Test
    void ingredientToStringShouldReturnFormattedString() {
        IngredientCommand ingredientCommand = generateTestIngredientCommand();
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        ingredientCommand.getUom().setDescription("Cup");

        String result = ingredientCommand.toString();
        assertThat(result, equalTo("2.0 x Cup of test description"));
    }

    @Test
    void ingredientToStringShouldHandleNullUOM() {
        IngredientCommand ingredientCommand = generateTestIngredientCommand();
        ingredientCommand.setUom(null);

        String result = ingredientCommand.toString();
        assertThat(result, equalTo("test description"));
    }

    @Test
    void addIngredientOnRequestShouldAddProperAttributes() throws Exception {
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        mockMvc.perform(post("/recipe/3/ingredient/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("recipeId", "3")
                .param("description", "test description")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredients"));

        verify(ingredientService, only()).saveIngredientCommand(any(IngredientCommand.class));
    }

    @Test
    void saveOrUpdateShouldReturnProperName() {
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);

        String generatedTemplateName = ingredientController.saveOrUpdate(givenIngredientCommand);
        assertThat(generatedTemplateName, equalTo("redirect:/recipe/3/ingredients"));
    }

    private IngredientCommand generateTestIngredientCommand() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setQuantity(2.0);
        ingredientCommand.setRecipeId(3L);
        ingredientCommand.setDescription("test description");
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        return ingredientCommand;
    }
}
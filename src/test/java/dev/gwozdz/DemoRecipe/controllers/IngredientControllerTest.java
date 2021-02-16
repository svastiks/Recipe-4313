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

    @Test
    void showIngredientsShouldReturnProperName(@Mock Model model) {
        //given
        String generatedTemplateName = "";
        //when
        generatedTemplateName = ingredientController.showIngredients("1", model);
        //then
        assertThat(generatedTemplateName, equalTo("recipe/ingredient/list"));
    }

    @Test
    void showIngredientsOnRequestShouldGetProperRecipe() throws Exception {
        //given
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        IngredientCommand ingredientCommand = generateTestIngredientCommand();
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        given(recipeService.getRecipeCommandById(anyLong())).willReturn(recipeCommandGiven);
        given(ingredientService.getNewIngredientCommandWithRecipeId(anyLong())).willReturn(ingredientCommand);
        given(unitOfMeasureService.getAllUnitsOfMeasureCommands()).willReturn(new HashSet<>());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe", "ingredientNew", "uoms"))
                .andExpect(model().attributeHasNoErrors("recipe"));
        //then
        verify(recipeService, only()).getRecipeCommandById(anyLong());
    }

    @Test
    void editIngredientShouldReturnProperName(@Mock Model model) {
        //given
        String generatedTemplateName = "";
        //when
        generatedTemplateName = ingredientController.editIngredient("1", "2", model);
        //then
        assertThat(generatedTemplateName, equalTo("recipe/ingredient/edit"));
    }

    @Test
    void editIngredientsOnRequestShouldGetProperRecipe() throws Exception {
        //given
        IngredientCommand ingredientCommand = generateTestIngredientCommand();
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        given(ingredientService.getIngredientCommandByRecipeIdAndId(anyLong(), anyLong())).willReturn(ingredientCommand);
        given(unitOfMeasureService.getAllUnitsOfMeasureCommands()).willReturn(new HashSet<>());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        //when
        mockMvc.perform(get("/recipe/3/ingredient/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/edit"))
                .andExpect(model().attributeExists( "ingredient", "uoms"))
                .andExpect(model().attributeHasNoErrors("ingredient"));
        //then
        verify(ingredientService, only()).getIngredientCommandByRecipeIdAndId(anyLong(), anyLong());
    }

    @Test
    void addIngredientShouldReturnProperName() {
        //given
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        String generatedTemplateName = "";
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);
        //when
        generatedTemplateName = ingredientController.addIngredient(givenIngredientCommand);
        //then
        assertThat(generatedTemplateName, equalTo("redirect:/recipe/3/ingredients"));
    }

    @Test
    void addIngredientOnRequestShouldAddProperAttributes() throws Exception {
        //given
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);
        //when
        //then
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
        //given
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        String generatedTemplateName = "";
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);
        //when
        generatedTemplateName = ingredientController.saveOrUpdate(givenIngredientCommand);
        //then
        assertThat(generatedTemplateName, equalTo("redirect:/recipe/3/ingredients"));
    }

    @Test
    void saveOrUpdateOnRequestShouldAddProperAttributes() throws Exception {
        //given
        IngredientCommand givenIngredientCommand = generateTestIngredientCommand();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        given(ingredientService.saveIngredientCommand(any(IngredientCommand.class))).willReturn(givenIngredientCommand);
        //when
        //then
        mockMvc.perform(post("/recipe/3/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("recipeId", "3")
                .param("description", "test description")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredients"));
        verify(ingredientService, only()).saveIngredientCommand(any(IngredientCommand.class));
    }

    private IngredientCommand generateTestIngredientCommand() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1l);
        ingredientCommand.setQuantity(2d);
        ingredientCommand.setRecipeId(3l);
        ingredientCommand.setDescription("test description");
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        return ingredientCommand;
    }
}
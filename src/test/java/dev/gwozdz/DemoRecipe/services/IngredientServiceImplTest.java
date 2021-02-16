package dev.gwozdz.DemoRecipe.services;


import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.converters.IngredientCommandToIngredient;
import dev.gwozdz.DemoRecipe.converters.IngredientToIngredientCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    private final static Long RECIPE_ID = Long.valueOf(1l);
    private final static Long INGREDIENT_ID = Long.valueOf(2l);
    private final static Long NEW_INGREDIENT_ID = Long.valueOf(3l);
    private final static Long UOM_ID = Long.valueOf(4l);
    private final static Double INGREDIENT_QUANTITY = Double.valueOf(5.5d);
    private final static Double NEW_INGREDIENT_QUANTITY = Double.valueOf(6.66d);
    private final static String INGREDIENT_DESCRIPTION = "INGREDIENT DESCRIPTION";
    private final static String NEW_INGREDIENT_DESCRIPTION = "NEW TEST DESCRIPTION";
    private final static String UOM_DESCRIPTION = "UOM DESCRIPTION";

    @InjectMocks
    IngredientServiceImpl ingredientService;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void getNewIngredientCommandWithRecipeIdShouldThrowWhenWrongRecipeId(){
        //given
        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(RuntimeException.class, () -> ingredientService.getIngredientCommandByRecipeIdAndId(12, 12));
    }

    @Test
    void getNewIngredientCommandWithRecipeIdShouldGetProperIngredientCommand(){
        //given
        Optional<Recipe> optionalRecipe = Optional.of(generateTestRecipeWithOneIngredient());
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);
        //when
        IngredientCommand generatedIngredientCommand = ingredientService.getNewIngredientCommandWithRecipeId(RECIPE_ID);
        //then
        assertThat(generatedIngredientCommand.getId(), nullValue());
    }

    @Test
    void getIngredientCommandByRecipeIdAndIdShouldThrowWhenWrongRecipeId(){
        //given
        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(RuntimeException.class, () -> ingredientService.getIngredientCommandByRecipeIdAndId(12, 12));
    }

    @Test
    void getIngredientCommandByRecipeIdAndIdShouldThrowWhenWrongIngredientId(){
        //given
        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);
        //when
        //then
        assertThrows(RuntimeException.class, () -> ingredientService.getIngredientCommandByRecipeIdAndId(12, 12));
    }

    @Test
    void getIngredientCommandByRecipeIdAndIdShouldGetProperIngredientCommand(){
        //given
        Optional<Recipe> optionalRecipe = Optional.of(generateTestRecipeWithOneIngredient());
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);
        IngredientCommand givenIngredientCommand = new IngredientCommand();
        givenIngredientCommand.setId(INGREDIENT_ID);
        given(ingredientToIngredientCommand.convert(ArgumentMatchers.any(Ingredient.class))).willReturn(givenIngredientCommand);
        //when
        IngredientCommand generatedIngredientCommand = ingredientService.getIngredientCommandByRecipeIdAndId(RECIPE_ID, INGREDIENT_ID);
        //then
        assertThat(generatedIngredientCommand.getId(), equalTo(INGREDIENT_ID));
    }

    @Test
    void saveIngredientCommandShouldThrowWhenWrongRecipeId(){
        //given
        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(RuntimeException.class, () -> ingredientService.saveIngredientCommand(new IngredientCommand()));
    }

    @Test
    void saveIngredientCommandShouldThrowWhenUomIsNull(){
        //given
        Optional<Recipe> optionalRecipe = Optional.of(generateTestRecipeWithOneIngredient());
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);
        IngredientCommand givenIngredientCommand = new IngredientCommand();
        givenIngredientCommand.setId(INGREDIENT_ID);
        given(unitOfMeasureRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(RuntimeException.class,() -> ingredientService.saveIngredientCommand(givenIngredientCommand));
    }

    @Test
    void saveIngredientCommandShouldUpdateExistingIngredient(){
        //given
            //create IngredientCommandToSave
        IngredientCommand ingredientCommandToSave = new IngredientCommand();
        ingredientCommandToSave.setId(INGREDIENT_ID);
        ingredientCommandToSave.setRecipeId(RECIPE_ID);
        ingredientCommandToSave.setDescription(NEW_INGREDIENT_DESCRIPTION);
        ingredientCommandToSave.setQuantity(NEW_INGREDIENT_QUANTITY);
        UnitOfMeasureCommand givenUnitOfMeasureCommand = new UnitOfMeasureCommand();
        givenUnitOfMeasureCommand.setId(UOM_ID);
        givenUnitOfMeasureCommand.setDescription(UOM_DESCRIPTION);
        ingredientCommandToSave.setUom(givenUnitOfMeasureCommand);

            //create recipe that repository will return
        Recipe givenRecipe = generateTestRecipeWithOneIngredient();
        Optional<Recipe> optionalRecipe = Optional.of(givenRecipe);
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);

            //create UOM and mock uomRepository
        UnitOfMeasure givenUnitOfMeasure = new UnitOfMeasure();
        givenUnitOfMeasure.setId(UOM_ID);
        given(unitOfMeasureRepository.findById(anyLong())).willReturn(Optional.of(givenUnitOfMeasure));

            //mock repository to return saved recipe
        given(recipeRepository.save(givenRecipe)).willReturn(givenRecipe);
        given(ingredientToIngredientCommand.convert(ArgumentMatchers.any(Ingredient.class))).willReturn(ingredientCommandToSave);
        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandToSave);
        //then
        assertThat(savedIngredientCommand.getId(), equalTo(INGREDIENT_ID));
        assertThat(savedIngredientCommand.getRecipeId(), equalTo(RECIPE_ID));
        assertThat(savedIngredientCommand.getDescription(), equalTo(NEW_INGREDIENT_DESCRIPTION));
        assertThat(savedIngredientCommand.getQuantity(), equalTo(NEW_INGREDIENT_QUANTITY));
    }

    @Test
    void saveIngredientCommandShouldAddNewIngredient(){
        //given
        //create IngredientCommandToSave
        IngredientCommand ingredientCommandToSave = new IngredientCommand();
        ingredientCommandToSave.setId(NEW_INGREDIENT_ID);
        ingredientCommandToSave.setRecipeId(RECIPE_ID);
        ingredientCommandToSave.setDescription(NEW_INGREDIENT_DESCRIPTION);
        UnitOfMeasureCommand givenUnitOfMeasureCommand = new UnitOfMeasureCommand();
        givenUnitOfMeasureCommand.setId(UOM_ID);
        givenUnitOfMeasureCommand.setDescription(UOM_DESCRIPTION);
        ingredientCommandToSave.setUom(givenUnitOfMeasureCommand);

        //create recipe that repository will return
        Recipe givenRecipe = generateTestRecipeWithOneIngredient();
        Optional<Recipe> optionalRecipe = Optional.of(givenRecipe);
        given(recipeRepository.findById(anyLong())).willReturn(optionalRecipe);

        //create ingredient that mock converter will return
        Ingredient ingredientConverted = new Ingredient();
        ingredientConverted.setId(NEW_INGREDIENT_ID);
        ingredientConverted.setRecipe(givenRecipe);
        ingredientConverted.setDescription(NEW_INGREDIENT_DESCRIPTION);
        given(ingredientCommandToIngredient.convert(ingredientCommandToSave)).willReturn(ingredientConverted);

        //mock repository to return saved recipe
        given(recipeRepository.save(givenRecipe)).willReturn(givenRecipe);
        //mock converter to return ingredientCommand
        given(ingredientToIngredientCommand.convert(ArgumentMatchers.any(Ingredient.class))).willReturn(ingredientCommandToSave);
        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandToSave);
        //then
        assertThat(savedIngredientCommand.getId(), equalTo(NEW_INGREDIENT_ID));
        assertThat(savedIngredientCommand.getDescription(), equalTo(NEW_INGREDIENT_DESCRIPTION));
        assertThat(savedIngredientCommand.getRecipeId(), equalTo(RECIPE_ID));
    }

    @Test
    void deleteIngredientByRecipeIdAndIngredientIdShouldThrowWhenWrongRecipeId(){
        //given
        Recipe existingRecipe = generateTestRecipeWithOneIngredient();
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(existingRecipe));
        //when
        //then
        assertThrows(RuntimeException.class, ()-> ingredientService.deleteIngredientByRecipeIdAndIngredientId(12l, 35l));
    }

    @Test
    void deleteIngredientByRecipeIdAndIngredientIdShouldThrowWhenWrongIngredientId(){
        //given
        Recipe existingRecipe = generateTestRecipeWithOneIngredient();
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(existingRecipe));
        //when
        //then
        assertThrows(RuntimeException.class, ()-> ingredientService.deleteIngredientByRecipeIdAndIngredientId(RECIPE_ID, 35l));
    }

    @Test
    void deleteIngredientByRecipeIdAndIngredientIdShouldDeleteExistingIngredient(){
        //given
        Recipe existingRecipe = generateTestRecipeWithOneIngredient();
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(existingRecipe));
        //when
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);
        //then
        assertThat(existingRecipe.getIngredients(), not(contains(Matchers.any(Ingredient.class))));

    }

    private Recipe generateTestRecipeWithOneIngredient(){
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(RECIPE_ID);
        Ingredient givenIngredient = new Ingredient();
        givenIngredient.setId(INGREDIENT_ID);
        givenIngredient.setDescription(INGREDIENT_DESCRIPTION);
        givenIngredient.setQuantity(INGREDIENT_QUANTITY);
        givenRecipe.addIngredient(givenIngredient);
        return givenRecipe;
    }
}

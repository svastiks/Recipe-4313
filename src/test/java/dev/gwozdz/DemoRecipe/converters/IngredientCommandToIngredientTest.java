package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    private final static Long ID_GIVEN = Long.valueOf(1l);
    private final static String DESCRIPTION_GIVEN = "description";
    private final static Double QUANTITY_GIVEN = Double.valueOf(1d);
    private final static UnitOfMeasureCommand UNIT_OF_MEASURE_COMMAND_GIVEN = new UnitOfMeasureCommand();
    private final static UnitOfMeasure UOM_COMMAND_GIVEN = new UnitOfMeasure();
    private final static Long RECIPE_ID = Long.valueOf(2l);

    private IngredientCommandToIngredient converter;

    @Mock
    private static UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(uomConverter, recipeRepository);
    }

    @Test
    void convertShouldHandleNull() {
        //given
        //when
        Ingredient ingredientConverted = converter.convert(null);
        //then
        assertNull(ingredientConverted);
    }

    @Test
    void convertShouldHandleEmptyUom() {
        //given
        IngredientCommand ingredientGiven = new IngredientCommand();
        //when
        Ingredient ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues() {
        //given
        IngredientCommand ingredientCommandGiven = prepareIngredientCommand();
        given(uomConverter.convert(UNIT_OF_MEASURE_COMMAND_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        Ingredient ingredientConverted = converter.convert(ingredientCommandGiven);
        //then
        assertThat(ingredientConverted.getId(), equalTo(ID_GIVEN));
        assertThat(ingredientConverted.getDescription(), equalTo(DESCRIPTION_GIVEN));
        assertThat(ingredientConverted.getUom(), equalTo(UOM_COMMAND_GIVEN));
        assertThat(ingredientConverted.getQuantity(), equalTo(QUANTITY_GIVEN));
        verify(uomConverter, atMost(1)).convert(UNIT_OF_MEASURE_COMMAND_GIVEN);
    }

    @Test
    void convertShouldHandleNullRecipeIdValue(){
        //given
        IngredientCommand ingredientCommandGiven = prepareIngredientCommand();
        given(uomConverter.convert(UNIT_OF_MEASURE_COMMAND_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        Ingredient ingredientConverted = converter.convert(ingredientCommandGiven);
        //then
        verify(uomConverter, atMost(1)).convert(UNIT_OF_MEASURE_COMMAND_GIVEN);
        assertThat(ingredientConverted.getRecipe(), nullValue());
    }

    @Test
    void convertShouldHandleNotNullRecipeIdValue(){
        //given
        IngredientCommand ingredientCommandGiven = prepareIngredientCommand();
        ingredientCommandGiven.setRecipeId(RECIPE_ID);
        Recipe recipeGiven = new Recipe();
        recipeGiven.setId(RECIPE_ID);
        Optional<Recipe> optionalRecipeGiven = Optional.of(recipeGiven);
        given(recipeRepository.findById(RECIPE_ID)).willReturn(optionalRecipeGiven);
        given(uomConverter.convert(UNIT_OF_MEASURE_COMMAND_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        Ingredient ingredientConverted = converter.convert(ingredientCommandGiven);
        //then
        verify(uomConverter, only()).convert(UNIT_OF_MEASURE_COMMAND_GIVEN);
        verify(recipeRepository, only()).findById(RECIPE_ID);
        assertThat(ingredientConverted.getRecipe(), is(recipeGiven));
    }

    private IngredientCommand prepareIngredientCommand() {
        IngredientCommand ingredientCommandGiven = new IngredientCommand();
        ingredientCommandGiven.setId(ID_GIVEN);
        ingredientCommandGiven.setDescription(DESCRIPTION_GIVEN);
        ingredientCommandGiven.setQuantity(QUANTITY_GIVEN);
        ingredientCommandGiven.setUom(UNIT_OF_MEASURE_COMMAND_GIVEN);
        return ingredientCommandGiven;
    }
}
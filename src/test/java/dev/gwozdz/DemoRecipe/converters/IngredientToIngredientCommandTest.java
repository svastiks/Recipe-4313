package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class IngredientToIngredientCommandTest {

    private final static Long ID_GIVEN = Long.valueOf(1l);
    private final static Long RECIPE_ID = Long.valueOf(2l);
    private final static String DESCRIPTION_GIVEN = "description";
    private final static Double QUANTITY_GIVEN = Double.valueOf(1d);
    private final static UnitOfMeasure UOM_GIVEN = new UnitOfMeasure();
    private final static UnitOfMeasureCommand UOM_COMMAND_GIVEN = new UnitOfMeasureCommand();

    private IngredientToIngredientCommand converter;

    @Mock
    private static UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @BeforeEach
    void setUp(){
        converter = new IngredientToIngredientCommand(uomConverter);
    }

    @Test
    void convertShouldHandleNull() {
        //given
        //when
        IngredientCommand ingredientConverted = converter.convert(null);
        //then
        assertNull(ingredientConverted);
    }

    @Test
    void convertShouldHandleEmptyUom() {
        //given
        Ingredient ingredientGiven = new Ingredient();
        //when
        IngredientCommand ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues() {
        //given
        Ingredient ingredientGiven = prepareTestIngredient();
        given(uomConverter.convert(UOM_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        IngredientCommand ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted.getId(), equalTo(ID_GIVEN));
        assertThat(ingredientConverted.getDescription(), equalTo(DESCRIPTION_GIVEN));
        assertThat(ingredientConverted.getUom(), equalTo(UOM_COMMAND_GIVEN));
        assertThat(ingredientConverted.getQuantity(), equalTo(QUANTITY_GIVEN));
        verify(uomConverter, only()).convert(UOM_GIVEN);
    }

    @Test
    void convertShouldHandleRecipeIdNullValue() {
        //given
        Ingredient ingredientGiven = prepareTestIngredient();
        given(uomConverter.convert(UOM_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        IngredientCommand ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted.getRecipeId(), nullValue());
    }

    @Test
    void convertShouldHandleRecipeIdNotNullValue() {
        //given
        Ingredient ingredientGiven = prepareTestIngredient();
        Recipe recipeGiven = new Recipe();
        recipeGiven.setId(RECIPE_ID);
        ingredientGiven.setRecipe(recipeGiven);
        given(uomConverter.convert(UOM_GIVEN)).willReturn(UOM_COMMAND_GIVEN);
        //when
        IngredientCommand ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted.getRecipeId(), equalTo(RECIPE_ID));
    }

    private Ingredient prepareTestIngredient(){
        Ingredient ingredientGiven = new Ingredient();
        ingredientGiven.setId(ID_GIVEN);
        ingredientGiven.setDescription(DESCRIPTION_GIVEN);
        ingredientGiven.setUom(UOM_GIVEN);
        ingredientGiven.setQuantity(QUANTITY_GIVEN);
        return ingredientGiven;
    }

}
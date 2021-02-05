package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    private IngredientCommandToIngredient converter;

    @Mock
    private static UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(uomConverter);
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

        UnitOfMeasureCommand uomGiven = new UnitOfMeasureCommand();
        ingredientCommandGiven.setUom(uomGiven);
        UnitOfMeasure uomCommandGiven = new UnitOfMeasure();
        given(uomConverter.convert(uomGiven)).willReturn(uomCommandGiven);
        //when
        Ingredient ingredientConverted = converter.convert(ingredientCommandGiven);
        //then
        assertThat(ingredientConverted.getId(), equalTo(ingredientCommandGiven.getId()));
        assertThat(ingredientConverted.getDescription(), equalTo(ingredientCommandGiven.getDescription()));
        assertThat(ingredientConverted.getUom(), equalTo(uomCommandGiven));
        assertThat(ingredientConverted.getQuantity(), equalTo(ingredientCommandGiven.getQuantity()));
        verify(uomConverter, atMost(1)).convert(uomGiven);
    }

    private IngredientCommand prepareIngredientCommand() {
        IngredientCommand ingredientCommandGiven = new IngredientCommand();
        Long idGiven = Long.valueOf(1l);
        String descriptionGiven = "description";
        Double quantityGiven = Double.valueOf(1d);
        ingredientCommandGiven.setId(idGiven);
        ingredientCommandGiven.setDescription(descriptionGiven);
        ingredientCommandGiven.setQuantity(quantityGiven);
        return ingredientCommandGiven;
    }
}
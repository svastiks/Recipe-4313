package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeAll;
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
        UnitOfMeasure uomGiven = new UnitOfMeasure();
        ingredientGiven.setUom(uomGiven);
        UnitOfMeasureCommand uomCommandGiven = new UnitOfMeasureCommand();
        given(uomConverter.convert(uomGiven)).willReturn(uomCommandGiven);
        //when
        IngredientCommand ingredientConverted = converter.convert(ingredientGiven);
        //then
        assertThat(ingredientConverted.getId(), equalTo(ingredientGiven.getId()));
        assertThat(ingredientConverted.getDescription(), equalTo(ingredientGiven.getDescription()));
        assertThat(ingredientConverted.getUom(), equalTo(uomCommandGiven));
        assertThat(ingredientConverted.getQuantity(), equalTo(ingredientGiven.getQuantity()));
        verify(uomConverter, atMost(1)).convert(uomGiven);
    }

    private Ingredient prepareTestIngredient(){
        Ingredient ingredientGiven = new Ingredient();
        Long idGiven = Long.valueOf(1l);
        String descriptionGiven = "description";
        Double quantityGiven = Double.valueOf(1d);
        ingredientGiven.setId(idGiven);
        ingredientGiven.setDescription(descriptionGiven);
        ingredientGiven.setQuantity(quantityGiven);
        return ingredientGiven;
    }

}
package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
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

        @Mock
        private static UnitOfMeasureCommandToUnitOfMeasure uomConverter;

        @Test
        void convertShouldHandleNull() {
            //given
            IngredientCommandToIngredient converter = new IngredientCommandToIngredient(uomConverter);
            //when
            Ingredient ingredientConverted = converter.convert(null);
            //then
            assertNull(ingredientConverted);
        }

        @Test
        void convertShouldHandleEmptyUom() {
            //given
            IngredientCommand ingredientGiven = new IngredientCommand();
            IngredientCommandToIngredient converter = new IngredientCommandToIngredient(uomConverter);
            //when
            Ingredient ingredientConverted = converter.convert(ingredientGiven);
            //then
            assertThat(ingredientConverted, notNullValue());
        }

        @Test
        void convertShouldReturnProperValues() {
            //given
            IngredientCommand ingredientGiven = new IngredientCommand();
            Long idGiven = Long.valueOf(1l);
            ingredientGiven.setId(idGiven);
            String descriptionGiven = "description";
            ingredientGiven.setDescription(descriptionGiven);
            Double quantityGiven = Double.valueOf(1d);
            ingredientGiven.setQuantity(quantityGiven);
            IngredientCommandToIngredient converter = new IngredientCommandToIngredient(uomConverter);
            UnitOfMeasureCommand uomGiven = new UnitOfMeasureCommand();
            ingredientGiven.setUom(uomGiven);
            UnitOfMeasure uomCommandGiven = new UnitOfMeasure();
            given(uomConverter.convert(uomGiven)).willReturn(uomCommandGiven);
            //when
            Ingredient ingredientConverted = converter.convert(ingredientGiven);
            //then
            assertThat(ingredientConverted.getId(), equalTo(idGiven));
            assertThat(ingredientConverted.getDescription(), equalTo(descriptionGiven));
            assertThat(ingredientConverted.getUom(), equalTo(uomCommandGiven));
            assertThat(ingredientConverted.getQuantity(), equalTo(quantityGiven));
            verify(uomConverter, atMost(1)).convert(uomGiven);
        }
}
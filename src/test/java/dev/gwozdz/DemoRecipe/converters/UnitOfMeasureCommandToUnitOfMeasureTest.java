package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convertShouldHandleNull(){
        //given
        //when
        UnitOfMeasure uomConverted = converter.convert(null);
        //then
        assertNull(uomConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        UnitOfMeasureCommand uomGiven = new UnitOfMeasureCommand();
        //when
        UnitOfMeasure uomConverted = converter.convert(uomGiven);
        //then
        assertThat(uomConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        UnitOfMeasureCommand uomGiven = new UnitOfMeasureCommand();
        Long idGiven = Long.valueOf(1l);
        String descriptionGiven = "description";
        uomGiven.setId(idGiven);
        uomGiven.setDescription(descriptionGiven);
        //when
        UnitOfMeasure uomConverted = converter.convert(uomGiven);
        //then
        assertThat(uomConverted.getId(), equalTo(idGiven));
        assertThat(uomConverted.getDescription(), equalTo(descriptionGiven));
    }

}
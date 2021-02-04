package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
import dev.gwozdz.DemoRecipe.model.Category;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    @Test
    void convertShouldHandleNull(){
        //given
        CategoryCommandToCategory converter = new CategoryCommandToCategory();
        //when
        Category categoryConverted = converter.convert(null);
        //then
        assertNull(categoryConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        CategoryCommand categoryGiven = new CategoryCommand();
        CategoryCommandToCategory converter = new CategoryCommandToCategory();
        //when
        Category categoryConverted = converter.convert(categoryGiven);
        //then
        assertThat(categoryConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        CategoryCommand categoryGiven = new CategoryCommand();
        Long idGiven = Long.valueOf(1l);
        categoryGiven.setId(idGiven);
        String descriptionGiven = "description";
        categoryGiven.setDescription(descriptionGiven);
        CategoryCommandToCategory converter = new CategoryCommandToCategory();
        //when
        Category categoryConverted = converter.convert(categoryGiven);
        //then
        assertThat(categoryConverted.getId(), equalTo(idGiven));
        assertThat(categoryConverted.getDescription(), equalTo(descriptionGiven));
    }
}
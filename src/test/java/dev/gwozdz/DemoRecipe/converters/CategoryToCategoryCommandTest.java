package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
import dev.gwozdz.DemoRecipe.model.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    private CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp(){
        converter = new CategoryToCategoryCommand();
    }
    @Test
    void convertShouldHandleNull(){
        //given
        //when
        CategoryCommand categoryConverted = converter.convert(null);
        //then
        assertNull(categoryConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        Category categoryGiven = new Category();
        //when
        CategoryCommand categoryConverted = converter.convert(categoryGiven);
        //then
        assertThat(categoryConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        Long idGiven = Long.valueOf(1l);
        String descriptionGiven = "description";
        Category categoryGiven = new Category();
        categoryGiven.setId(idGiven);
        categoryGiven.setDescription(descriptionGiven);
        //when
        CategoryCommand categoryConverted = converter.convert(categoryGiven);
        //then
        assertThat(categoryConverted.getId(), equalTo(idGiven));
        assertThat(categoryConverted.getDescription(), equalTo(descriptionGiven));
    }
}
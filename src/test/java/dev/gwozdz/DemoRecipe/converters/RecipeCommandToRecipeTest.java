package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.NoteCommand;
import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeCommandToRecipeTest {

    private final static Long ID = Long.valueOf(1l);
    private final static Integer COOK_TIME = 10;
    private final static Integer PREP_TIME = 15;
    private final static Integer SERVINGS = 4;
    private final static String DESCRIPTION = "Just an example description";
    private final static String DIRECTIONS = "Just an example directions";
    private final static String SOURCE = "Just an example source";
    private final static String URL = "Just an example URL";
    private final static Difficulty DIFFICULTY = Difficulty.MID;
    private final static NoteCommand NOTE_COMMAND = new NoteCommand();
    private final static Set<IngredientCommand> INGREDIENT_COMMANDS = new HashSet<>();
    private final static Set<CategoryCommand> CATEGORY_COMMANDS = new HashSet<>();
    private final static Note NOTE = new Note();

    @Mock
    private CategoryCommandToCategory categoryConverter;
    @Mock
    private IngredientCommandToIngredient ingredientConverter;
    @Mock
    private NoteCommandToNote noteConverter;

    private RecipeCommandToRecipe converter;


    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(categoryConverter, ingredientConverter, noteConverter);
    }

    @Test
    void convertShouldHandleNull() {
        //given
        //when
        Recipe recipeConverted = converter.convert(null);
        //then
        assertNull(recipeConverted);
    }

    @Test
    void convertShouldHandleEmptyRecipe() {
        //given
        RecipeCommand recipeGiven = new RecipeCommand();
        //when
        Recipe recipeConverted = converter.convert(recipeGiven);
        //then
        assertThat(recipeConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues() {
        //given
        RecipeCommand recipeGiven = prepareTestRecipeWithOneIngredientAndOneCategory();
        given(noteConverter.convert(NOTE_COMMAND)).willReturn(NOTE);
        //when
        Recipe recipeConverted = converter.convert(recipeGiven);
        //then
        assertThat(recipeConverted.getId(), is(ID));
        assertThat(recipeConverted.getCookTime(), is(COOK_TIME));
        assertThat(recipeConverted.getPrepTime(), is(PREP_TIME));
        assertThat(recipeConverted.getServings(), is(SERVINGS));
        assertThat(recipeConverted.getDescription(), is(DESCRIPTION));
        assertThat(recipeConverted.getDirections(), is(DIRECTIONS));
        assertThat(recipeConverted.getSource(), is(SOURCE));
        assertThat(recipeConverted.getUrl(), is(URL));
        assertThat(recipeConverted.getDifficulty(), is(DIFFICULTY));
        assertThat(recipeConverted.getNote(), is(NOTE));
        verify(ingredientConverter, only()).convert(any(IngredientCommand.class));
        verify(categoryConverter, only()).convert(any(CategoryCommand.class));
    }

    private RecipeCommand prepareTestRecipeWithOneIngredientAndOneCategory() {
        RecipeCommand recipeGiven = new RecipeCommand();
        recipeGiven.setId(ID);
        recipeGiven.setCookTime(COOK_TIME);
        recipeGiven.setPrepTime(PREP_TIME);
        recipeGiven.setServings(SERVINGS);
        recipeGiven.setDescription(DESCRIPTION);
        recipeGiven.setDirections(DIRECTIONS);
        recipeGiven.setSource(SOURCE);
        recipeGiven.setUrl(URL);
        recipeGiven.setDifficulty(DIFFICULTY);
        recipeGiven.setNote(NOTE_COMMAND);
        recipeGiven.setIngredients(INGREDIENT_COMMANDS);
        recipeGiven.getIngredients().add(new IngredientCommand());
        recipeGiven.setCategories(CATEGORY_COMMANDS);
        recipeGiven.getCategories().add(new CategoryCommand());
        return recipeGiven;
    }
}
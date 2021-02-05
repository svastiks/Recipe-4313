package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class RecipeToRecipeCommandTest {

    private final static Long ID = Long.valueOf(1l);
    private final static Integer COOK_TIME = 10;
    private final static Integer PREP_TIME = 15;
    private final static Integer SERVINGS = 4;
    private final static String DESCRIPTION = "Just an example description";
    private final static String DIRECTIONS = "Just an example directions";
    private final static String SOURCE = "Just an example source";
    private final static String URL = "Just an example URL";
    private final static Difficulty DIFFICULTY = Difficulty.MID;
    private final static Note NOTE = new Note("TEST NOTE");
    private final static Set<Ingredient> INGREDIENTS = new HashSet<>();
    private final static Set<Category> CATEGORIES  = new HashSet<>();
    private final static NoteCommand NOTE_COMMAND = new NoteCommand();

    @Mock
    private CategoryToCategoryCommand categoryConverter;
    @Mock
    private IngredientToIngredientCommand ingredientConverter;
    @Mock
    private NoteToNoteCommand noteConverter;

    private RecipeToRecipeCommand converter;


    @BeforeEach
    void setUp(){
        converter = new RecipeToRecipeCommand(categoryConverter, ingredientConverter, noteConverter);
    }

    @Test
    void convertShouldHandleNull(){
        //given
        //when
        RecipeCommand recipeConverted = converter.convert(null);
        //then
        assertNull(recipeConverted);
    }

    @Test
    void convertShouldHandleEmptyRecipe(){
        //given
        Recipe recipeGiven = new Recipe();
        //when
        RecipeCommand recipeConverted = converter.convert(recipeGiven);
        //then
        assertThat(recipeConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        Recipe recipeGiven = prepareTestRecipeWithOneIngredientAndOneCategory();
        given(noteConverter.convert(NOTE)).willReturn(NOTE_COMMAND);
        //when
        RecipeCommand recipeConverted = converter.convert(recipeGiven);
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
        assertThat(recipeConverted.getNote(), is(NOTE_COMMAND));
        verify(ingredientConverter, only()).convert(any(Ingredient.class));
        verify(categoryConverter, only()).convert(any(Category.class));



    }

    private Recipe prepareTestRecipeWithOneIngredientAndOneCategory(){
        Recipe recipeGiven = new Recipe();
        recipeGiven.setId(ID);
        recipeGiven.setCookTime(COOK_TIME);
        recipeGiven.setPrepTime(PREP_TIME);
        recipeGiven.setServings(SERVINGS);
        recipeGiven.setDescription(DESCRIPTION);
        recipeGiven.setDirections(DIRECTIONS);
        recipeGiven.setSource(SOURCE);
        recipeGiven.setUrl(URL);
        recipeGiven.setDifficulty(DIFFICULTY);
        recipeGiven.setNote(NOTE);
        recipeGiven.setIngredients(INGREDIENTS);
        recipeGiven.getIngredients().add(new Ingredient());
        recipeGiven.setCategories(CATEGORIES);
        recipeGiven.getCategories().add(new Category());
        return recipeGiven;
    }
}
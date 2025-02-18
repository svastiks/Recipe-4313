package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.converters.RecipeCommandToRecipe;
import dev.gwozdz.DemoRecipe.converters.RecipeToRecipeCommand;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeServiceIT {

    public static final String DESCRIPTION_NEW = "New description";
    private Recipe testRecipe;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe();
        testRecipe.setDescription("Test Recipe");
        testRecipe.setCookTime(10);
        testRecipe.setPrepTime(5);
        testRecipe.setServings(4);
        testRecipe = recipeRepository.save(testRecipe);
    }

    @Test
    void saveRecipeCommandShouldReturnProperValue() {
        //given
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        recipeCommandGiven.setDescription(DESCRIPTION_NEW);

        //when
        RecipeCommand recipeCommandSaved = recipeService.saveRecipeCommand(recipeCommandGiven);

        //then
        assertNotNull(recipeCommandSaved);
        assertEquals(DESCRIPTION_NEW, recipeCommandSaved.getDescription());
    }

    @Transactional
    @Test
    void saveRecipeCommandShouldBeAbleToUpdateExistingRecipe() {
        //given
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        recipeCommand.setDescription(DESCRIPTION_NEW);
        RecipeCommand recipeCommandSaved = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertNotNull(recipeCommandSaved);
        assertEquals(DESCRIPTION_NEW, recipeCommandSaved.getDescription());
        assertEquals(testRecipe.getId(), recipeCommandSaved.getId());
    }

    @Test
    void deleteRecipeByIdShouldRemoveProperRecipe() {
        //given
        Long idToDelete = testRecipe.getId();

        //when
        recipeService.deleteRecipeById(idToDelete);
        Optional<Recipe> result = recipeRepository.findById(idToDelete);

        //then
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() {
        //when
        Optional<Recipe> result = recipeRepository.findById(testRecipe.getId());

        //then
        assertTrue(result.isPresent());
        assertEquals(testRecipe.getId(), result.get().getId());
    }

    @Test
    void getAllRecipesShouldReturnNonEmptyList() {
        //when
        Set<Recipe> recipes = recipeService.getRecipes();

        //then
        assertFalse(recipes.isEmpty());
        assertThat(recipes, hasItem(testRecipe));
    }

    @Test
    void updateRecipeShouldModifyExistingRecipe() {
        //given
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);
        recipeCommand.setCookTime(20);

        //when
        RecipeCommand updatedCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertNotNull(updatedCommand);
        assertEquals(20, updatedCommand.getCookTime());
    }
}
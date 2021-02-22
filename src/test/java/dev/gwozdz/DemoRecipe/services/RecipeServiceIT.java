package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.converters.RecipeCommandToRecipe;
import dev.gwozdz.DemoRecipe.converters.RecipeToRecipeCommand;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class RecipeServiceIT {

    public static final String DESCRIPTION_NEW = "New description";

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Test
    void saveRecipeCommandShouldReturnProperValue(){
        //given
        RecipeCommand recipeCommandGiven = new RecipeCommand();
        recipeCommandGiven.setDescription(DESCRIPTION_NEW);
        //when
        RecipeCommand recipeCommandSaved = recipeService.saveRecipeCommand(recipeCommandGiven);
        //then
        assertNotNull(recipeCommandSaved);
        assertEquals(recipeCommandGiven.getDescription(), DESCRIPTION_NEW);
    }

    @Transactional
    @Test
    void saveRecipeCommandShouldBeAbleToUpdateExistingRecipe(){
        //given
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        Recipe recipeFetched = allRecipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipeFetched);
        //when
        recipeCommand.setDescription(DESCRIPTION_NEW);
        RecipeCommand recipeCommandSaved = recipeService.saveRecipeCommand(recipeCommand);
        //then
        assertThat(recipeCommandSaved.getDescription(), equalTo(DESCRIPTION_NEW));
        assertThat(recipeCommandSaved.getId(), equalTo(recipeFetched.getId()));
        assertThat(recipeCommandSaved.getCategories().size(), equalTo(recipeFetched.getCategories().size()));
        assertThat(recipeCommandSaved.getIngredients().size(), equalTo(recipeFetched.getIngredients().size()));
    }

    @Test
    void deleteRecipeByIdShouldRemoveProperRecipe(){
        //given
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        Recipe recipeFetched = allRecipes.iterator().next();
        Long idToDelete = recipeFetched.getId();
        //when
        recipeService.deleteRecipeById(idToDelete);
        Optional<Recipe> result = recipeRepository.findById(idToDelete);
        //then
        assertThat(result.isEmpty(), is(true));
    }
}

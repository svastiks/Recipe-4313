package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.converters.IngredientCommandToIngredient;
import dev.gwozdz.DemoRecipe.converters.IngredientToIngredientCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class IngredientServiceIT {

    private final static String NEW_DESCRIPTION = "NEW TEST DESCRIPTION";

    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Autowired
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    @Transactional
    void getIngredientCommandByRecipeIdAndIdShouldFindExistingIngredientCommand(){
        //given
        Recipe fetchedRecipe = recipeRepository.findAll().iterator().next();
        Long recipeId = fetchedRecipe.getId();
        Ingredient fetchedIngredient = fetchedRecipe.getIngredients().iterator().next();
        Long ingredientId = fetchedIngredient.getId();
        //when
        IngredientCommand fetchedIngredientCommand = ingredientService.getIngredientCommandByRecipeIdAndId(recipeId, ingredientId);
        //then
        assertThat(fetchedIngredientCommand, notNullValue());
        assertThat(fetchedIngredientCommand.getId(), equalTo(ingredientId));
        assertThat(fetchedIngredientCommand.getRecipeId(), equalTo(recipeId));
    }

    @Test
    @Transactional
    void saveIngredientCommandShouldReturnProperObject(){
        //given
        Recipe fetchedRecipe = recipeRepository.findAll().iterator().next();
        Long recipeId = fetchedRecipe.getId();
        Ingredient fetchedIngredient = fetchedRecipe.getIngredients().iterator().next();
        Long ingredientId = fetchedIngredient.getId();
        IngredientCommand ingredientCommandConverted = ingredientToIngredientCommand.convert(fetchedIngredient);
        ingredientCommandConverted.setDescription(NEW_DESCRIPTION);
        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommandConverted);

        //then
        assertThat(savedIngredientCommand, notNullValue());
        assertThat(savedIngredientCommand.getDescription(), equalTo(NEW_DESCRIPTION));
        assertThat(savedIngredientCommand.getId(), equalTo(ingredientId));
        assertThat(savedIngredientCommand.getRecipeId(), equalTo(recipeId));
        assertTrue(fetchedRecipe.getIngredients().contains(ingredientCommandToIngredient.convert(savedIngredientCommand)));
    }

    @Test
    @Transactional
    void getNewIngredientCommandWithRecipeIdShouldReturnProperObject(){
        //given
        Recipe fetchedRecipe = recipeRepository.findAll().iterator().next();
        Long recipeId = fetchedRecipe.getId();
        //when
        IngredientCommand newIngredientCommandWithRecipeId = ingredientService.getNewIngredientCommandWithRecipeId(recipeId);
        //then
        assertThat(newIngredientCommandWithRecipeId, notNullValue());
        assertThat(newIngredientCommandWithRecipeId.getDescription(), nullValue());
        assertThat(newIngredientCommandWithRecipeId.getId(), nullValue());
        assertThat(newIngredientCommandWithRecipeId.getRecipeId(), equalTo(recipeId));
    }

    @Test
    @Transactional
    void deleteIngredientByRecipeIdAndIngredientIdShouldDeleteProperObject(){
        //given
        Recipe fetchedRecipe = recipeRepository.findAll().iterator().next();
        Long recipeId = fetchedRecipe.getId();
        Ingredient ingredientToDelete = fetchedRecipe.getIngredients().iterator().next();
        Long ingredientId = ingredientToDelete.getId();
        //when
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(recipeId, ingredientId);
        //then
        assertThat(fetchedRecipe.getIngredients(), not(contains(ingredientToDelete)));
    }
}
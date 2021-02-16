package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand getIngredientCommandByRecipeIdAndId(long recipeId, long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    IngredientCommand getNewIngredientCommandWithRecipeId(long recipeId);

    void deleteIngredientByRecipeIdAndIngredientId(long recipeId, long ingredientId);
}

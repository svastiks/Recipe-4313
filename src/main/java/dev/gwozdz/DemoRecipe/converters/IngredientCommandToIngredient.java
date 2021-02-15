package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    private final RecipeRepository recipeRepository;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter, RecipeRepository recipeRepository) {
        this.uomConverter = uomConverter;
        this.recipeRepository = recipeRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if(source==null){
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setQuantity(source.getQuantity());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        if(source.getRecipeId() != null){
            Recipe recipe = recipeRepository.findById(source.getRecipeId()).get();
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        return ingredient;
    }
}

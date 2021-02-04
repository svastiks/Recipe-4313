package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.model.Category;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private CategoryCommandToCategory categoryConverter;
    private IngredientCommandToIngredient ingredientConverter;
    private NoteCommandToNote noteConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter, NoteCommandToNote noteConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.noteConverter = noteConverter;
    }

    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNote(noteConverter.convert(source.getNote()));
        Set<Category> categoryCommands = source.getCategories().stream().map(categoryConverter::convert).collect(Collectors.toSet());
        recipe.setCategories(categoryCommands);
        Set<Ingredient> ingredientCommands = source.getIngredients().stream().map(ingredientConverter::convert).collect(Collectors.toSet());
        recipe.setIngredients(ingredientCommands);

        return recipe;

    }
}


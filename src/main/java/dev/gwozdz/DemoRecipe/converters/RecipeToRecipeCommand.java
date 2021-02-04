package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private CategoryToCategoryCommand categoryConverter;
    private IngredientToIngredientCommand ingredientConverter;
    private NoteToNoteCommand noteConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter, NoteToNoteCommand noteConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.noteConverter = noteConverter;
    }

    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNote(noteConverter.convert(source.getNote()));
        Set<CategoryCommand> categoryCommands = source.getCategories().stream().map(categoryConverter::convert).collect(Collectors.toSet());
        recipeCommand.setCategories(categoryCommands);
        Set<IngredientCommand> ingredientCommands = source.getIngredients().stream().map(ingredientConverter::convert).collect(Collectors.toSet());
        recipeCommand.setIngredients(ingredientCommands);

        return recipeCommand;

    }
}

package dev.gwozdz.DemoRecipe.services;


import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.converters.IngredientCommandToIngredient;
import dev.gwozdz.DemoRecipe.converters.IngredientToIngredientCommand;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Transactional
    @Override
    public IngredientCommand getIngredientCommandByRecipeIdAndId(long recipeId, long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe id not found");
        }
        Set<Ingredient> ingredients = recipeOptional.get().getIngredients();
        Optional<Ingredient> ingredientOptional = ingredients.stream().filter(i -> i.getId() == ingredientId).findFirst();
        if(ingredientOptional.isEmpty()){
            throw new RuntimeException("Ingredient id not found");
        }
        return ingredientToIngredientCommand.convert(ingredientOptional.get());
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe not found");
        }else{
            Recipe recipeFetched = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipeFetched
                    .getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setQuantity(command.getQuantity());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found")));
            }else {
                Ingredient ingredientToSave = ingredientCommandToIngredient.convert(command);
                recipeFetched.addIngredient(ingredientToSave);
            }

            Recipe recipeSaved = recipeRepository.save(recipeFetched);

            Optional<Ingredient> optionalIngredientCommandSaved = recipeSaved.getIngredients().stream()
                    .filter(i -> i.getId().equals(command.getId()))
                    .findFirst();
            if(optionalIngredientCommandSaved.isPresent()){
                return ingredientToIngredientCommand.convert(optionalIngredientCommandSaved.get());
            }else{
                optionalIngredientCommandSaved = recipeSaved
                .getIngredients().stream()
                .filter(i -> i.getDescription().equals(command.getDescription()))
                .filter(i -> i.getQuantity().equals(command.getQuantity()))
                .filter(i-> i.getUom().getId().equals(command.getUom().getId()))
                .findFirst();
                if(optionalIngredientCommandSaved.isEmpty()){
                    throw new RuntimeException("Ingredient not found!");
                }
                else{
                    return ingredientToIngredientCommand.convert(optionalIngredientCommandSaved.get());
                }
            }
        }
    }

    @Override
    @Transactional
    public IngredientCommand getNewIngredientCommandWithRecipeId(long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            throw new RuntimeException("RecipeId not found");
        }
        IngredientCommand ingredientCommandNew = new IngredientCommand();
        ingredientCommandNew.setRecipeId(recipeId);
        return ingredientCommandNew;
    }

}

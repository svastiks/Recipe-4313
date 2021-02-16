package dev.gwozdz.DemoRecipe.services;


import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
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
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommandToSave) {
        //check existance of recipe with recipeID given by ingredientCommandToSave
        Optional<Recipe> recipeOptionalFromRepo = recipeRepository.findById(ingredientCommandToSave.getRecipeId());
        //if not throw exception
        if(recipeOptionalFromRepo.isEmpty()){
            throw new RuntimeException("Recipe not found");
        }else{
            //get right recipe
            Recipe recipeExisting = recipeOptionalFromRepo.get();
            //check if ingredient with given ingredientId exists in recipe
            Optional<Ingredient> optionalIngredientExistingInRecipe = recipeExisting
                    .getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(ingredientCommandToSave.getId()))
                    .findFirst();
            //if ingredient already exists, copy values from ingredientCommandToSave
            if(optionalIngredientExistingInRecipe.isPresent()){
                Ingredient ingredientExisting = optionalIngredientExistingInRecipe.get();
                ingredientExisting.setDescription(ingredientCommandToSave.getDescription());
                ingredientExisting.setQuantity(ingredientCommandToSave.getQuantity());
                ingredientExisting.setUom(unitOfMeasureRepository
                        .findById(ingredientCommandToSave.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found")));
                //if it is not found, convert ingredientCommandToSave to Ingredient
                //and add it to existing recipe
            }else {
                Ingredient notExistingIngredient = ingredientCommandToIngredient.convert(ingredientCommandToSave);
                recipeExisting.addIngredient(notExistingIngredient);
            }
            //save recipe in repo
            Recipe recipeSaved = recipeRepository.save(recipeExisting);
            //find ingredient by its ingredientID
            Optional<Ingredient> optionalIngredientFromSavedRecipe = recipeSaved.getIngredients().stream()
                    .filter(i -> i.getId().equals(ingredientCommandToSave.getId()))
                    .findFirst();
            //if ingredientCommand was existing in recipe before it should be found by ingredientID
            if(optionalIngredientFromSavedRecipe.isPresent()){
                return ingredientToIngredientCommand.convert(optionalIngredientFromSavedRecipe.get());
            }
            else{
                //if ingredientCommand was newly created, find by comparing all fields
                optionalIngredientFromSavedRecipe = recipeSaved
                .getIngredients().stream()
                .filter(i -> i.getDescription().equals(ingredientCommandToSave.getDescription()))
                .filter(i -> i.getQuantity().equals(ingredientCommandToSave.getQuantity()))
                .filter(i-> i.getUom().getId().equals(ingredientCommandToSave.getUom().getId()))
                .findFirst();
                //if not found anyway throw exception
                if(optionalIngredientFromSavedRecipe.isEmpty()){
                    throw new RuntimeException("Ingredient not found!");
                }
                //else return converted ingredientFromSavedRecipe
                else{
                    return ingredientToIngredientCommand.convert(optionalIngredientFromSavedRecipe.get());
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

package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.IngredientCommand;
import dev.gwozdz.DemoRecipe.services.IngredientService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import dev.gwozdz.DemoRecipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
        model.addAttribute("ingredientNew", ingredientService.getNewIngredientCommandWithRecipeId(Long.valueOf(recipeId)));
        model.addAttribute("uoms", unitOfMeasureService.getAllUnitsOfMeasureCommands());
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/edit")
    public String editIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.getIngredientCommandByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uoms", unitOfMeasureService.getAllUnitsOfMeasureCommands());
        return "recipe/ingredient/edit";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredientByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" +recipeId + "/ingredients";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients";
    }

    @PostMapping("recipe/{recipeId}/ingredient/new")
    public String addIngredient(@ModelAttribute IngredientCommand command){
        IngredientCommand addedCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/" + addedCommand.getRecipeId() + "/ingredients";
    }
}

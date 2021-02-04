package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeShowController {

    private RecipeService recipeService;

    public RecipeShowController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(Long.parseLong(id)));
        return "recipe/show";
    }
}

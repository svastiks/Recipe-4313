package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.services.CategoryService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private RecipeService recipeService;
    private CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(Long.parseLong(id)));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.parseLong(id)));
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model){
        recipeService.deleteRecipeById(Long.parseLong(id));
        return "redirect:/";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand recipeCommandSaved = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + recipeCommandSaved.getId() + "/show";
    }
}

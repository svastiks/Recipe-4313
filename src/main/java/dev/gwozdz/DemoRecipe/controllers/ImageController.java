package dev.gwozdz.DemoRecipe.controllers;


import dev.gwozdz.DemoRecipe.services.ImageService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService  imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @RequestMapping("/recipe/{recipeId}/image")
    public String getImageForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(recipeId)));

        return "recipe/imageform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String saveImage(@PathVariable String recipeId, @RequestParam("imagefile")MultipartFile multipartFile) {
        imageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipe/" + recipeId + "/show";
    }
}

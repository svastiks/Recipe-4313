package dev.gwozdz.DemoRecipe.controllers;


import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.converters.ByteClassToBytePrimitive;
import dev.gwozdz.DemoRecipe.converters.BytePrimitiveToByteClass;
import dev.gwozdz.DemoRecipe.services.ImageService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;
    private final ByteClassToBytePrimitive byteClassToBytePrimitive;
    private final BytePrimitiveToByteClass bytePrimitiveToByteClass;

    public ImageController(RecipeService recipeService, ImageService imageService,
                           ByteClassToBytePrimitive byteClassToBytePrimitive,
                           BytePrimitiveToByteClass bytePrimitiveToByteClass) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.byteClassToBytePrimitive = byteClassToBytePrimitive;
        this.bytePrimitiveToByteClass = bytePrimitiveToByteClass;
    }

    @RequestMapping("/recipe/{recipeId}/image")
    public String getImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(recipeId)));

        return "recipe/imageform";
    }

    @GetMapping("/recipe/{recipeId}/imageview")
    public void getImageView(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(recipeId));
        if (recipeCommand.getImage() != null) {
            byte[] bytesImageFromRecipe = byteClassToBytePrimitive.convert(recipeCommand.getImage());
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytesImageFromRecipe);
            IOUtils.copy(is, response.getOutputStream());
        }

    }

    @PostMapping("/recipe/{recipeId}/image")
    public String saveImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {
        imageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipe/" + recipeId + "/show";
    }
}

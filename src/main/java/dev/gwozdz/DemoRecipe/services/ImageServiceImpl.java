package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(long recipeId, MultipartFile file) {

    }
}

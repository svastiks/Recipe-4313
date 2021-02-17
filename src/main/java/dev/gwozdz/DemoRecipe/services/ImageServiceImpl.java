package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.converters.ByteClassToBytePrimitive;
import dev.gwozdz.DemoRecipe.converters.BytePrimitiveToByteClass;
import dev.gwozdz.DemoRecipe.model.Recipe;
import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;
    private final BytePrimitiveToByteClass bytePrimitiveToByteClass;

    public ImageServiceImpl(RecipeRepository recipeRepository, BytePrimitiveToByteClass bytePrimitiveToByteClass) {
        this.recipeRepository = recipeRepository;
        this.bytePrimitiveToByteClass = bytePrimitiveToByteClass;
    }

    @Override
    @Transactional
    public void saveImageFile(long recipeId, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(Long.valueOf(recipeId)).get();
            Byte[] fileAsBytes = bytePrimitiveToByteClass.convert(file.getBytes());
            recipe.setImage(fileAsBytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

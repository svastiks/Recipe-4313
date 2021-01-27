package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @Test
    void getAllRecipesShouldInvokeFindAllFromRepository() {
        //given
        //when
        recipeService.getAllRecipes();
        //then
        then(recipeRepository).should().findAll();
    }
}
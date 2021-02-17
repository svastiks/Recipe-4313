package dev.gwozdz.DemoRecipe.controllers;

import dev.gwozdz.DemoRecipe.commands.RecipeCommand;
import dev.gwozdz.DemoRecipe.services.ImageService;
import dev.gwozdz.DemoRecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @InjectMocks
    ImageController imageController;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    @Test
    void getImageFormShouldReturnProperName(@Mock Model model) {
        //given
        String generatedTemplateName = "";
        //when
        generatedTemplateName = imageController.getImageForm("1", model);
        //then
        assertEquals("recipe/imageform", generatedTemplateName);
    }

    @Test
    void getImageFormOnDemandShouldAddProperAttribute() throws Exception {
        //given
        RecipeCommand givenRecipeCommand = new RecipeCommand();
        givenRecipeCommand.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        given(recipeService.getRecipeCommandById(anyLong())).willReturn(givenRecipeCommand);
        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        //then
        verify(recipeService, only()).getRecipeCommandById(anyLong());
    }

    @Test
    void saveImageShouldReturnProperName() {
        //given
        String generatedTemplateName;
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Bartosz Gwozdz test".getBytes());
        //when
        generatedTemplateName = imageController.saveImage("1", multipartFile);
        //then
        assertEquals("redirect:/recipe/1/show", generatedTemplateName);
    }

    @Test
    void saveImageOnDemandShouldHandleImagePost() throws Exception{
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Bartosz Gwozdz test".getBytes());
        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        //then
        verify(imageService, only()).saveImageFile(1, multipartFile);
    }
}
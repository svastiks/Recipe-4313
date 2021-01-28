package dev.gwozdz.DemoRecipe.repositories;

import dev.gwozdz.DemoRecipe.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByDescriptionShouldFindProperDescription() {
        //given
        //when
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Polska");
        //then
        assertThat(categoryOptional.isPresent(), equalTo(true));
        assertThat(categoryOptional.get().getDescription(), equalTo("Polska"));
    }
    
}
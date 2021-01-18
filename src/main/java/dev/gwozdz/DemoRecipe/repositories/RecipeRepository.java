package dev.gwozdz.DemoRecipe.repositories;

import dev.gwozdz.DemoRecipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

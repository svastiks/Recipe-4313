package dev.gwozdz.DemoRecipe.repositories;

import dev.gwozdz.DemoRecipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}

package dev.gwozdz.DemoRecipe.repositories;

import dev.gwozdz.DemoRecipe.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}

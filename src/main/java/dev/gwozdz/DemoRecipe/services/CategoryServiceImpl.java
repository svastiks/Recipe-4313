package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;
import dev.gwozdz.DemoRecipe.converters.CategoryCommandToCategory;
import dev.gwozdz.DemoRecipe.converters.CategoryToCategoryCommand;
import dev.gwozdz.DemoRecipe.model.Category;
import dev.gwozdz.DemoRecipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand, CategoryCommandToCategory categoryCommandToCategory) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Override
    public Set<CategoryCommand> getAllCategories() {
        Set<CategoryCommand> categoryCommands = new HashSet<>();
        StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .forEach(categoryCommands::add);
        return  categoryCommands;
    }
}

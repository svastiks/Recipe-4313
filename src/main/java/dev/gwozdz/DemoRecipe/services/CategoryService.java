package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> getAllCategories();
}

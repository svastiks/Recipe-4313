package dev.gwozdz.DemoRecipe.commands;

import dev.gwozdz.DemoRecipe.model.Category;
import dev.gwozdz.DemoRecipe.model.Difficulty;
import dev.gwozdz.DemoRecipe.model.Ingredient;
import dev.gwozdz.DemoRecipe.model.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private Integer cookTime;
    private Integer prepTime;
    private Integer servings;
    private String description;
    private String directions;
    private String source;
    private String url;
    private Difficulty difficulty;
    private NoteCommand note;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories  = new HashSet<>();
    private Byte[] image;
}

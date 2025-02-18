package dev.gwozdz.DemoRecipe.commands;

import dev.gwozdz.DemoRecipe.model.Difficulty;
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
    private Set<CategoryCommand> categories = new HashSet<>();
    private Byte[] image;

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setNote(NoteCommand note) {
        this.note = note;
    }

    public void setIngredients(Set<IngredientCommand> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategories(Set<CategoryCommand> categories) {
        this.categories = categories;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Byte[] getImage() {
        return image;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public Integer getServings() {
        return servings;
    }

    public String getDescription() {
        return description;
    }

    public String getDirections() {
        return directions;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public NoteCommand getNote() {
        return note;
    }

    public Set<IngredientCommand> getIngredients() {
        return ingredients;
    }

    public Set<CategoryCommand> getCategories() {
        return categories;
    }
}

package dev.gwozdz.DemoRecipe.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy ="categories", fetch = FetchType.EAGER)
    private Set<Recipe> recipes;

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

	public void setId(Object id2) {
		this.id = (Long) id2;
	}

	public void setDescription(Object description2) {
        this.description = (String) description2;
	}
}

package dev.gwozdz.DemoRecipe.commands;

import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private Double quantity;
    private UnitOfMeasureCommand uom;
    private Long recipeId;

    @Override
    public String toString() {
        if( uom == null || quantity == null){
            return this.description;
        }else {
            return this.quantity + " x " + this.uom.getDescription() + " of " + this.description ;
        }
    }

	public Object getId() {
		return id;
	}

	public Object getRecipeId() {
		return recipeId;
	}

	public Object getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

}

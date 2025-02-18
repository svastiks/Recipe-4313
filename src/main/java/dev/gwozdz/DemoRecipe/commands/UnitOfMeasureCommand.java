package dev.gwozdz.DemoRecipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
	public String getDescription() {
		return description;
	}
	public void setId(Long idGiven) {
		this.id = idGiven;
		
	}
	public void setDescription(String descriptionGiven) {
		this.description = descriptionGiven;
		
	}
	public Object getId() {
		return id;
	}
}

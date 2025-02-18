package dev.gwozdz.DemoRecipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteCommand {
    private Long id;
    private String recipeNotes;
	public void setRecipeNotes(String recipeNotesGiven) {
		this.recipeNotes = recipeNotesGiven;
	}
	
	public void setId(Long idGiven) {
		this.id = idGiven;
	}
	public Object getId() {
		return id;
	}
	public Object getRecipeNotes() {
		return recipeNotes;
	}
}

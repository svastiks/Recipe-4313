package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.NoteCommand;
import dev.gwozdz.DemoRecipe.model.Note;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {

    @Test
    void convertShouldHandleNull(){
        //given
        NoteCommandToNote converter = new NoteCommandToNote();
        //when
        Note uomConverted = converter.convert(null);
        //then
        assertNull(uomConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        NoteCommand uomGiven = new NoteCommand();
        NoteCommandToNote converter = new NoteCommandToNote();
        //when
        Note uomConverted = converter.convert(uomGiven);
        //then
        assertThat(uomConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        NoteCommand uomGiven = new NoteCommand();
        Long idGiven = Long.valueOf(1l);
        uomGiven.setId(idGiven);
        String recipeNotesGiven = "description";
        uomGiven.setRecipeNotes(recipeNotesGiven);
        NoteCommandToNote converter = new NoteCommandToNote();
        //when
        Note noteConverted = converter.convert(uomGiven);
        //then
        assertThat(noteConverted.getId(), equalTo(idGiven));
        assertThat(noteConverted.getRecipeNotes(), equalTo(recipeNotesGiven));
    }

}
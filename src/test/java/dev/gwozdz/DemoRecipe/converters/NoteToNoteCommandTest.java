package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.NoteCommand;
import dev.gwozdz.DemoRecipe.model.Note;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {
    @Test
    void convertShouldHandleNull(){
        //given
        NoteToNoteCommand converter = new NoteToNoteCommand();
        //when
        NoteCommand noteConverted = converter.convert(null);
        //then
        assertNull(noteConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        Note noteGiven = new Note();
        NoteToNoteCommand converter = new NoteToNoteCommand();
        //when
        NoteCommand noteConverted = converter.convert(noteGiven);
        //then
        assertThat(noteConverted, notNullValue());
    }

    @Test
    void convertShouldReturnProperValues(){
        //given
        Note noteGiven = new Note();
        Long idGiven = Long.valueOf(1l);
        noteGiven.setId(idGiven);
        String recipeNotesGiven = "recipeNotes";
        noteGiven.setRecipeNotes(recipeNotesGiven);
        NoteToNoteCommand converter = new NoteToNoteCommand();
        //when
        NoteCommand noteConverted = converter.convert(noteGiven);
        //then
        assertThat(noteConverted.getId(), equalTo(idGiven));
        assertThat(noteConverted.getRecipeNotes(), equalTo(recipeNotesGiven));
    }
}
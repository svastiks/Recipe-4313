package dev.gwozdz.DemoRecipe.converters;

import dev.gwozdz.DemoRecipe.commands.NoteCommand;
import dev.gwozdz.DemoRecipe.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {
    private NoteToNoteCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NoteToNoteCommand();
    }

    @Test
    void convertShouldHandleNull(){
        //given
        //when
        NoteCommand noteConverted = converter.convert(null);
        //then
        assertNull(noteConverted);
    }

    @Test
    void convertShouldHandleEmptyUom(){
        //given
        Note noteGiven = new Note();
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
        String recipeNotesGiven = "recipeNotes";
        noteGiven.setId(idGiven);
        noteGiven.setRecipeNotes(recipeNotesGiven);
        //when
        NoteCommand noteConverted = converter.convert(noteGiven);
        //then
        assertThat(noteConverted.getId(), equalTo(idGiven));
        assertThat(noteConverted.getRecipeNotes(), equalTo(recipeNotesGiven));
    }
    
    @Test
    void convertShouldHandleNullRecipeNotes() {
        // given
        Note noteGiven = new Note();
        noteGiven.setId(2L);
        noteGiven.setRecipeNotes(null);

        // when
        NoteCommand noteConverted = converter.convert(noteGiven);

        // then
        assertThat(noteConverted, notNullValue());
        assertThat(noteConverted.getId(), equalTo(2L));
        assertNull(noteConverted.getRecipeNotes());
    }
    
    @Test
    void convertShouldHandleNullId() {
        // given
        Note noteGiven = new Note();
        noteGiven.setId(null);
        noteGiven.setRecipeNotes("Some recipe notes");

        // when
        NoteCommand noteConverted = converter.convert(noteGiven);

        // then
        assertThat(noteConverted, notNullValue());
        assertNull(noteConverted.getId());
        assertThat(noteConverted.getRecipeNotes(), equalTo("Some recipe notes"));
    }
    
    @Test
    void convertShouldHandleEmptyRecipeNotes() {
        // given
        Note noteGiven = new Note();
        noteGiven.setId(4L);
        noteGiven.setRecipeNotes("");

        // when
        NoteCommand noteConverted = converter.convert(noteGiven);

        // then
        assertThat(noteConverted, notNullValue());
        assertThat(noteConverted.getId(), equalTo(4L));
        assertThat(noteConverted.getRecipeNotes(), equalTo(""));
    } 
}
package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnitOfMeasureServiceIT {

    @Autowired
    UnitOfMeasureService unitOfMeasureService;
    @Autowired
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @Autowired
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void getAllUnitOfMeasureCommandsShouldReturnSetOfUoms(){
        //given
        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.getAllUnitsOfMeasureCommands();
        //then
        assertThat(unitOfMeasureCommandSet, Matchers.<UnitOfMeasureCommand>iterableWithSize(greaterThan(0)));
    }
}
package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
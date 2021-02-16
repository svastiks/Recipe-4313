package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements  UnitOfMeasureService{

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private final UnitOfMeasureRepository  unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand,
                                    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure,
                                    UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    public Set<UnitOfMeasureCommand> getAllUnitsOfMeasureCommands() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(),false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}

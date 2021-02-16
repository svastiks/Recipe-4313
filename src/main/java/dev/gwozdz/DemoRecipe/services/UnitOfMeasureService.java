package dev.gwozdz.DemoRecipe.services;

import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> getAllUnitsOfMeasureCommands();
}

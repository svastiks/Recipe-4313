package dev.gwozdz.DemoRecipe.services;


import dev.gwozdz.DemoRecipe.commands.UnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dev.gwozdz.DemoRecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.gwozdz.DemoRecipe.model.UnitOfMeasure;
import dev.gwozdz.DemoRecipe.repositories.UnitOfMeasureRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
public class UnitOfMeasureServiceImplTest {

    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    @Mock
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void getAllUnitsOfMeasureCommandsShouldInvokeProperMethods(){
        //given
        //when
        unitOfMeasureService.getAllUnitsOfMeasureCommands();
        //then
        verify(unitOfMeasureRepository, only()).findAll();
    }

    @Test
    void getAllUnisOfMeasureCommandsShouldReturnSetOfUOMSC(){
        //given
        //when
        Set<UnitOfMeasureCommand> retrievedUoms = unitOfMeasureService.getAllUnitsOfMeasureCommands();
        //then
        assertThat(retrievedUoms, emptyCollectionOf(UnitOfMeasureCommand.class));
    }

    @Test
    void getAllUnisOfMeasureCommandsShouldReturnSetOfProperUOMS(){
        //given
        UnitOfMeasureCommand uomc1 = new UnitOfMeasureCommand();
        uomc1.setId(1l);
        UnitOfMeasureCommand uomc2 = new UnitOfMeasureCommand();
        uomc1.setId(2l);
        Set<UnitOfMeasure> givenUoms = prepareTestUomSet();
        given(unitOfMeasureRepository.findAll()).willReturn(givenUoms);
        when(unitOfMeasureToUnitOfMeasureCommand.convert(ArgumentMatchers.any(UnitOfMeasure.class)))
                .thenReturn(uomc1)
                .thenReturn(uomc2);
        //when
        Set<UnitOfMeasureCommand> retrievedUoms = unitOfMeasureService.getAllUnitsOfMeasureCommands();
        //then
        assertThat(retrievedUoms, hasSize(2));
    }

    private Set<UnitOfMeasure> prepareTestUomSet(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1l);
        uom1.setDescription("uom1 test");
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2l);
        uom2.setDescription("uom2 test");
        Set<UnitOfMeasure> givenUoms = Set.of(uom1, uom2);
        return givenUoms;
    }
}

package tn.esprit.spring;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {
    @Mock
    private ChambreRepository chambreRepository;
    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private ChambreService chambreService;

    @Test
    void testFindAll() {
        // Arrange: Create mock data
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.SIMPLE);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.DOUBLE);

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act: Call the method
        List<Chambre> result = chambreService.findAll();

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(101L, result.get(0).getNumeroChambre());
        assertEquals(102L, result.get(1).getNumeroChambre());

        // Verify that the repository method was called
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testAddOrUpdate() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addOrUpdate(chambre);

        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testFindById() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        chambreService.deleteById(1L);
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        Chambre chambre = new Chambre();
        chambreService.delete(chambre);
        verify(chambreRepository, times(1)).delete(chambre);
    }

    @Test
    void testGetChambresParNomBloc() {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());

        when(chambreRepository.findByBlocNomBloc("BlocA")).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomBloc("BlocA");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc("BlocA");
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        Chambre chambre1 = new Chambre();
        chambre1.setTypeC(TypeChambre.SIMPLE);
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        chambre1.setBloc(bloc);

        when(chambreRepository.findAll()).thenReturn(Collections.singletonList(chambre1));

        long result = chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);

        assertEquals(1, result);
        verify(chambreRepository, times(1)).findAll();
    }


    @Test
    void testListeChambresParBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocA");
        bloc.setCapaciteBloc(100);
        bloc.setChambres(Collections.emptyList());

        when(blocRepository.findAll()).thenReturn(Collections.singletonList(bloc));

        chambreService.listeChambresParBloc();

        verify(blocRepository, times(1)).findAll();
    }

}

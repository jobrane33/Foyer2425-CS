package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Foyer.FoyerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FoyerServiceTest {

    @InjectMocks
    private FoyerService foyerService;

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer result = foyerService.addOrUpdate(foyer);
        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testFindAll() {
        Foyer foyer1 = new Foyer();
        Foyer foyer2 = new Foyer();
        List<Foyer> foyers = Arrays.asList(foyer1, foyer2);

        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.deleteById(1L);

        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        Foyer foyer = new Foyer();
        doNothing().when(foyerRepository).delete(foyer);

        foyerService.delete(foyer);

        verify(foyerRepository, times(1)).delete(foyer);
    }

    @Test
    void testAffecterFoyerAUniversite() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        universite.setNomUniversite("Test University");

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite("Test University")).thenReturn(universite);
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = foyerService.affecterFoyerAUniversite(1L, "Test University");
        assertNotNull(result);
        assertEquals("Test University", result.getNomUniversite());
        assertEquals(foyer, result.getFoyer());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        Universite universite = new Universite();
        Foyer foyer = new Foyer();

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = foyerService.desaffecterFoyerAUniversite(1L);
        assertNotNull(result);
        assertNull(result.getFoyer());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() {
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        blocs.add(bloc1);
        blocs.add(bloc2);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(foyer)).thenReturn(foyer);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1).thenReturn(bloc2);

        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);
        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
        verify(blocRepository, times(2)).save(any(Bloc.class));
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testAjoutFoyerEtBlocs() {
        Foyer foyer = new Foyer();
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        blocs.add(bloc1);
        blocs.add(bloc2);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(foyer)).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1).thenReturn(bloc2);

        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);
        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
        verify(blocRepository, times(2)).save(any(Bloc.class));
    }
}

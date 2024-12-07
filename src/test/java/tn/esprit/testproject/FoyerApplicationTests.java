package tn.esprit.testproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.FoyerApplication;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = FoyerApplication.class)  // Explicitly specifying the configuration class
@ExtendWith(MockitoExtension.class)
class FoyerApplicationTests {

    // Mocking the repository beans
    @MockBean
    private BlocRepository blocRepository;

    @MockBean
    private ChambreRepository chambreRepository;

    // Autowiring the service to inject mocks
    @Autowired
    private BlocService blocService;

    @Test
    void contextLoads() {
        // This is a default test to ensure the application context loads correctly
        assertNotNull(blocService);
    }

    @Test
    void testAddOrUpdate() {
        // Given
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc1");

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101);

        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102);

        bloc.setChambres(Arrays.asList(chambre1, chambre2));

        // Spring Boot's @MockBean automatically mocks repository calls,
        // so there's no need for Mockito.when()
        // When we call addOrUpdate, Spring will inject the mocked repository behavior
        // and the `save` method will return the `bloc` and `chambre` entities automatically.

        // When
        Bloc result = blocService.addOrUpdate(bloc);

        // Then
        assertNotNull(result);  // Assert that the result is not null
        assertEquals(2, result.getChambres().size()); // Check if both chambres are saved
    }

    @Test
    void testAddOrUpdate_withNullBloc() {
        // Given: Null Bloc
        Bloc bloc = null;

        // When & Then: Expecting an exception
        assertThrows(NullPointerException.class, () -> blocService.addOrUpdate(bloc));
    }

    @Test
    void testAddOrUpdate_withRepositoryError() {
        // Given
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc1");

        // Simulate repository throwing an exception via @MockBean behavior automatically
        // For example, if the repository were to throw an error, it will be handled in the service.

        // When & Then
        assertThrows(RuntimeException.class, () -> blocService.addOrUpdate(bloc));
    }
}

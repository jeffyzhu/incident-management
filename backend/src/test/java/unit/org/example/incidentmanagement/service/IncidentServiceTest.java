package unit.org.example.incidentmanagement.service;

import org.example.incidentmanagement.exception.ResourceNotFoundException;
import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.repository.IncidentRepository;
import org.example.incidentmanagement.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    void setUp() {
        incident = new Incident();
        incident.setId(1L);
        incident.setTitle("Test Incident");
        incident.setDescription("Test Description");
        incident.setStatus("Open");
    }

    @Test
    void testAddIncidentSuccess() {
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

        Incident savedIncident = incidentService.addIncident(incident);

        assertNotNull(savedIncident);
        assertEquals(incident.getTitle(), savedIncident.getTitle());
    }

    @Test
    void testAddIncidentDuplicateKeyException() {
        when(incidentRepository.save(any(Incident.class)))
                .thenThrow(new DuplicateKeyException("Incident with the same title already exists."));

        DuplicateKeyException e = assertThrows(DuplicateKeyException.class, () -> {
            incidentService.addIncident(incident);
        });

        assertEquals("Incident with the same title already exists.", e.getMessage());
    }

    @Test
    void testGetIncidentByIdSuccess() {
        when(incidentRepository.findById(anyLong())).thenReturn(Optional.of(incident));

        Optional<Incident> foundIncident = incidentService.getIncidentById(1L);

        assertTrue(foundIncident.isPresent());
        assertEquals(incident.getTitle(), foundIncident.get().getTitle());
    }

    @Test
    void testGetIncidentByIdNotFound() {
        when(incidentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            incidentService.getIncidentById(1L).orElseThrow(
                    () -> new ResourceNotFoundException("Incident not found with ID: 1"));
        });
    }

    @Test
    void testUpdateIncident() {
        when(incidentRepository.findById(anyLong())).thenReturn(Optional.of(incident));
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

        Incident updatedIncident = incidentService.updateIncident(1L, incident);

        assertEquals(incident.getTitle(), updatedIncident.getTitle());
    }

    @Test
    void testDeleteIncident() {
        when(incidentRepository.findById(anyLong())).thenReturn(Optional.of(incident));
        doNothing().when(incidentRepository).delete(any(Incident.class));

        boolean deleted = incidentService.deleteIncident(1L);

        assertTrue(deleted);
    }
}

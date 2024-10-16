package org.example.incidentmanagement.service;

import org.example.incidentmanagement.exception.ResourceNotFoundException;
import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.repository.IncidentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IncidentServiceIntegrationTest {

    @Autowired
    private IncidentRepository incidentRepository;

    private IncidentService incidentService;

    private Incident incident;

    @BeforeEach
    void setUp() {
        incidentService = new IncidentService(incidentRepository);

        incident = new Incident();
        incident.setTitle("Test Incident");
        incident.setDescription("Test Description");
        incident.setStatus("Open");
    }


    @Test
    void testAddIncidentSuccess() {
        Incident savedIncident = incidentService.addIncident(incident);

        assertNotNull(savedIncident);
        assertEquals(incident.getTitle(), savedIncident.getTitle());
    }

    @Test
    void testAddIncidentDuplicateKeyException() {
        incidentService.addIncident(incident);

        Incident duplicateIncident = new Incident();
        duplicateIncident.setId(2L);
        duplicateIncident.setTitle("Test Incident");
        Exception exception = assertThrows(DuplicateKeyException.class, () -> {
            incidentService.addIncident(duplicateIncident);
        });

        String expectedMessage = "Incident with the same title already exists.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetIncidentByIdSuccess() {
        Incident savedIncident = incidentService.addIncident(incident);

        Optional<Incident> foundIncident = incidentService.getIncidentById(savedIncident.getId());

        assertTrue(foundIncident.isPresent());
        assertEquals(incident.getTitle(), foundIncident.get().getTitle());
    }

    @Test
    void testGetIncidentByIdResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            incidentService.getIncidentById(1L).orElseThrow(
                    () -> new ResourceNotFoundException("Incident not found with ID: 1"));
        });
    }

    @Test
    void testUpdateIncident() {
        Incident savedIncident = incidentService.addIncident(incident);
        Incident updatedIncident = new Incident();
        updatedIncident.setTitle("Updated Title");
        updatedIncident.setDescription("Updated Description");
        updatedIncident.setStatus("Closed");

        Incident result = incidentService.updateIncident(savedIncident.getId(), updatedIncident);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Closed", result.getStatus());
    }

    @Test
    void testDeleteIncident() {
        Incident savedIncident = incidentService.addIncident(incident);
        boolean deleted = incidentService.deleteIncident(savedIncident.getId());

        assertTrue(deleted);
    }
}
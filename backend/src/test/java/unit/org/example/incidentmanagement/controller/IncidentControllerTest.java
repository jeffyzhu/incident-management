package unit.org.example.incidentmanagement.controller;

import org.example.incidentmanagement.controller.IncidentController;
import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentControllerTest {

    @Mock
    private IncidentService incidentService;

    @InjectMocks
    private IncidentController incidentController;

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
    void testGetAllIncidents() {
        when(incidentService.getAllIncidents()).thenReturn(Collections.singletonList(incident));

        List<Incident> incidents = incidentController.getAllIncidents();

        assertNotNull(incidents);
        assertEquals(1, incidents.size());
    }

    @Test
    void testGetIncidentByIdSuccess() {
        when(incidentService.getIncidentById(anyLong())).thenReturn(Optional.of(incident));

        ResponseEntity<Incident> responseEntity = incidentController.getIncidentById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(incident.getTitle(), responseEntity.getBody().getTitle());
    }

    @Test
    void testGetIncidentByIdNotFound() {
        when(incidentService.getIncidentById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Incident> responseEntity = incidentController.getIncidentById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testAddIncident() {
        when(incidentService.addIncident(any(Incident.class))).thenReturn(incident);

        ResponseEntity<Incident> responseEntity = incidentController.addIncident(incident);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(incident.getTitle(), responseEntity.getBody().getTitle());
    }

    @Test
    void testUpdateIncident() {
        when(incidentService.updateIncident(anyLong(), any(Incident.class))).thenReturn(incident);

        ResponseEntity<Void> responseEntity = incidentController.updateIncident(1L, incident);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteIncident() {
        when(incidentService.deleteIncident(anyLong())).thenReturn(true);

        ResponseEntity<Void> responseEntity = incidentController.deleteIncident(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}

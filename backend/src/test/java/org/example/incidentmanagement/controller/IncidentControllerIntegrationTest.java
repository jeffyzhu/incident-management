package org.example.incidentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.repository.IncidentRepository;
import org.example.incidentmanagement.service.IncidentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class IncidentControllerIntegrationTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "adminpassword";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private IncidentRepository incidentRepository;

    private Incident incident;

    @BeforeEach
    void setUp() {
        incident = new Incident();
        incident.setTitle("Test Incident");
        incident.setDescription("Test Description");
        incident.setStatus("Open");
    }

    @AfterEach
    void tearDown() {
        incidentRepository.deleteAll(); // Clean up the repository after each test
    }

    @Test
    void testGetAllIncidents() throws Exception {
        incidentService.addIncident(incident);

        mockMvc.perform(get("/incidents")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Incident"));
    }

    @Test
    void testGetIncidentByIdSuccess() throws Exception {
        Incident savedIncident = incidentService.addIncident(incident);

        mockMvc.perform(get("/incidents/" + savedIncident.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Incident"));
    }

    @Test
    void testGetIncidentByIdNotFound() throws Exception {
        mockMvc.perform(get("/incidents/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddIncident() throws Exception {
        mockMvc.perform(post("/incidents")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incident)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Incident"));
    }

    @Test
    void testUpdateIncident() throws Exception {
        Incident savedIncident = incidentService.addIncident(incident);
        Incident updatedIncident = new Incident();
        updatedIncident.setTitle("Updated Title");
        updatedIncident.setDescription("Updated Description");
        updatedIncident.setStatus("Closed");

        mockMvc.perform(put("/incidents/" + savedIncident.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIncident)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/incidents/" + savedIncident.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("Closed"));
    }

    @Test
    void testDeleteIncident() throws Exception {
        Incident savedIncident = incidentService.addIncident(incident);

        mockMvc.perform(delete("/incidents/" + savedIncident.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/incidents/" + savedIncident.getId())
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
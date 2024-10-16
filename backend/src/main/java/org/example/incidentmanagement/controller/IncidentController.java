package org.example.incidentmanagement.controller;

import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.service.IncidentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        return incident.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Incident> addIncident(@RequestBody Incident incident) {
        Incident savedIncident = incidentService.addIncident(incident);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIncident(@PathVariable Long id, @RequestBody Incident incident) {
        incidentService.updateIncident(id, incident);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        boolean deleted = incidentService.deleteIncident(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

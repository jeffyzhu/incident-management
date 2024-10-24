package org.example.incidentmanagement.service;

import org.example.incidentmanagement.exception.ResourceNotFoundException;
import org.example.incidentmanagement.model.Incident;
import org.example.incidentmanagement.repository.IncidentRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final CacheManager cacheManager;

    public IncidentService(IncidentRepository incidentRepository, CacheManager cacheManager) {
        this.incidentRepository = incidentRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "incidents", key = "'all'")
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @Cacheable(value = "incidents", key = "#id")
    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    @CachePut(value = "incidents", key = "#result.id")
    @CacheEvict(value = "incidents", key = "'all'")
    public Incident addIncident(Incident incident) {
        try {
            return incidentRepository.save(incident);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                throw new DuplicateKeyException("Incident with the same title already exists.");
            } else {
                throw e;
            }
        }
    }

    @CachePut(value = "incidents", key = "#id")
    @CacheEvict(value = "incidents", key = "'all'")
    public Incident updateIncident(Long id, Incident updatedIncident) {
        try {
            return incidentRepository.findById(id).map(existingIncident -> {
                existingIncident.setTitle(updatedIncident.getTitle());
                existingIncident.setDescription(updatedIncident.getDescription());
                existingIncident.setStatus(updatedIncident.getStatus());
                return incidentRepository.save(existingIncident);
            }).orElseThrow(() -> new ResourceNotFoundException("Incident not found with ID: " + id));
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                throw new DuplicateKeyException("Incident with the same title already exists.");
            } else {
                throw e;
            }
        }
    }


    public boolean deleteIncident(Long id) {
        return incidentRepository.findById(id).map(incident -> {
            incidentRepository.delete(incident);

            if (cacheManager.getCache("incidents") != null) {
                cacheManager.getCache("incidents").evict(id);
                cacheManager.getCache("incidents").clear();
            }

            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("Incident not found with ID: " + id));
    }

    @CacheEvict(value = "incidents", allEntries = true)
    public void deleteAllIncidents() {
        incidentRepository.deleteAll();
    }
}

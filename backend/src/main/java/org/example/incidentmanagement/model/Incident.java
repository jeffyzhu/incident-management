package org.example.incidentmanagement.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    private String status;
}

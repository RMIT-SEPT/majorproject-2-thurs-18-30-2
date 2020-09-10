package com.majorproject.backend.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @NotBlank(message = "Service description required")
    private String description;

    public Service() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

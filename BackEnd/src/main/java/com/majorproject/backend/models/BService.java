package com.majorproject.backend.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="BSERVICE")
public class BService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bservice_id")
    private Long id;

    @NotBlank(message = "B Service name required")
    private String name;

    @NotBlank(message = "B Service description required")
    private String description;

    public BService() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

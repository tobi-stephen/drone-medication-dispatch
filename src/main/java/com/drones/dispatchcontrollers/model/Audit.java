package com.drones.dispatchcontrollers.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author tobi
 * @created 19/05/2022
 */

@Data
@MappedSuperclass
public abstract class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @NotNull
    @CreatedDate
    protected LocalDateTime dateCreated;

    @CreatedBy
    protected long created_by;

    @LastModifiedBy
    protected long last_modified_by;

    @NotNull
    @LastModifiedDate
    protected LocalDateTime lastModified;

    @PrePersist
    public void prePersist() {
        lastModified = dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastModified = LocalDateTime.now();
    }
}

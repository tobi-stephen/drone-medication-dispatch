package com.drones.dispatchcontrollers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Data
@Entity
@Table(name = "dispatches")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dispatch extends Audit{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "droneId", referencedColumnName = "id")
    private Drone drone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicationId", referencedColumnName = "id", unique = true)
    private Medication medication;

    @Column(nullable = false)
    private Boolean completed;

}

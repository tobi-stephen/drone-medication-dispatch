package com.drones.dispatchcontrollers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author tobi
 * @created 19/05/2022
 */

@Data
@Entity
@Table(name = "drones")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Drone extends Audit{

    @Column(unique=true)
    private String serialNumber;

    @Column(nullable = false)
    private String model;

    private long weightLimit;
    private long batteryCapacity;

    @Column(nullable = false)
    private String state;

    private long weightOccupied;
}

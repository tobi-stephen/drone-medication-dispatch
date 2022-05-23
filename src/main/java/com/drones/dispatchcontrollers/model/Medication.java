package com.drones.dispatchcontrollers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Data
@Entity
@Table(name = "medications")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Medication extends Audit{

    @Column(unique=true)
    private String code;

    @Column(nullable = false)
    private String name;

    private long weight;

    @Column(nullable = true, length = 128)
    private String image;

}

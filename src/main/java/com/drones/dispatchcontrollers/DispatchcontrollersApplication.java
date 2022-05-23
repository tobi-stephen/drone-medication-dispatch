package com.drones.dispatchcontrollers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tobi
 * @created 19/05/2022
 */

@SpringBootApplication
@EnableScheduling
public class DispatchcontrollersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatchcontrollersApplication.class, args);
	}

}

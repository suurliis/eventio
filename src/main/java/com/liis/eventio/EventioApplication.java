package com.liis.eventio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class EventioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventioApplication.class, args);
	}

}

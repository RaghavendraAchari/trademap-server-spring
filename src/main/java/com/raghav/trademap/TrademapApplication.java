package com.raghav.trademap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan({"com.raghav.trademap"})
public class TrademapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrademapApplication.class, args);
	}

}

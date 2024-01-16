package com.raghav.trademap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public class TrademapApplicationTest {

	public static void main(String[] args) {
		System.out.println(SpringVersion.getVersion());
	}

}

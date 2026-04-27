package com.Alejandro.BolsaDeValores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BolsaDeValoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(BolsaDeValoresApplication.class, args);
	}

}

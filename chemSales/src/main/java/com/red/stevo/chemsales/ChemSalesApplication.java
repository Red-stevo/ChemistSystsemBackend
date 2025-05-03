package com.red.stevo.chemsales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.red.stevo.chemsales.entities")
public class ChemSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChemSalesApplication.class, args);
	}

}

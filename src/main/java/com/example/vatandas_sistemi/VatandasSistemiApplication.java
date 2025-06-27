package com.example.vatandas_sistemi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VatandasSistemiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VatandasSistemiApplication.class, args);
	}

}

package br.fsa.guilhermecassiano.itens.itens_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.fsa.guilhermecassiano.itens")
@EnableJpaRepositories(basePackages = "br.fsa.guilhermecassiano.itens.repository")
@EntityScan(basePackages = "br.fsa.guilhermecassiano.itens.entity")
public class ItensServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItensServiceApplication.class, args);
	}

}

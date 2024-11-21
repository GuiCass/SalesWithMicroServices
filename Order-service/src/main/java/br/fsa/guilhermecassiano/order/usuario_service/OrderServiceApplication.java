package br.fsa.guilhermecassiano.order.usuario_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.fsa.guilhermecassiano.order")
@EnableJpaRepositories(basePackages = "br.fsa.guilhermecassiano.order.repository")
@EntityScan(basePackages = "br.fsa.guilhermecassiano.order.entity")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}

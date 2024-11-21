package br.fsa.guilhermecassiano.usuario.usuario_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.fsa.guilhermecassiano.usuario")
@EnableJpaRepositories(basePackages = "br.fsa.guilhermecassiano.usuario.repository")
@EntityScan(basePackages = "br.fsa.guilhermecassiano.usuario.entity")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}

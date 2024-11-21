package br.fsa.guilhermecassiano.product.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "br.fsa.guilhermecassiano.product")
@EnableJpaRepositories(basePackages = "br.fsa.guilhermecassiano.product.repository")
@EntityScan(basePackages = "br.fsa.guilhermecassiano.product.entity")
@EnableDiscoveryClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

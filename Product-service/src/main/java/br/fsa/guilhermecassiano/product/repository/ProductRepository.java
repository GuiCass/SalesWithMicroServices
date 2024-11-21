package br.fsa.guilhermecassiano.product.repository;

import br.fsa.guilhermecassiano.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}

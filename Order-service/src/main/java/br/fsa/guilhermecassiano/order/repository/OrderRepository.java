package br.fsa.guilhermecassiano.order.repository;

import br.fsa.guilhermecassiano.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    // Busca todos os pedidos associados a um usuário específico
    List<Order> findByUsuarioId(Long usuarioId);
}

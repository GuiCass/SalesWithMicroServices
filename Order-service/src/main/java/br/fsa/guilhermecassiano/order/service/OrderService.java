package br.fsa.guilhermecassiano.order.service;

import br.fsa.guilhermecassiano.order.entity.Order;
import br.fsa.guilhermecassiano.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order addOrder(Order order){
        return orderRepository.save(order);
    }
    public List<Order> fetchOrders(){
        return orderRepository.findAll();
    }
    public Order fetchOrderById(int id){
        return orderRepository.findById(id).orElse(null);
    }

    public boolean deleteOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Busca todos os pedidos de um usuário específico
    public List<Order> listarPedidosDoUsuario(Long usuarioId) {
        return orderRepository.findByUsuarioId(usuarioId);
    }
}

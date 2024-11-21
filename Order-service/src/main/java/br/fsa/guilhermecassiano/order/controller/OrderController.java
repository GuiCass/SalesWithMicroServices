package br.fsa.guilhermecassiano.order.controller;

import br.fsa.guilhermecassiano.order.entity.Order;
import br.fsa.guilhermecassiano.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Order addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }

    @GetMapping
    public List<Order> fetchOrders(){
        return  orderService.fetchOrders();
    }

    @GetMapping("/{id}")
    public Order fetchOrderById(@PathVariable int id){
        return orderService.fetchOrderById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable int id) {
        boolean isDeleted = orderService.deleteOrderById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Order>> listarPedidosDoUsuario(@PathVariable Long usuarioId) {
        try {
            List<Order> pedidos = orderService.listarPedidosDoUsuario(usuarioId);
            System.out.println(pedidos);
            if (pedidos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedidos);
            }
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
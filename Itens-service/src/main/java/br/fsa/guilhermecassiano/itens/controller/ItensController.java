package br.fsa.guilhermecassiano.itens.controller;

import br.fsa.guilhermecassiano.itens.entity.Itens;
import br.fsa.guilhermecassiano.itens.service.ItensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping(value = "/itens")
@RestController
public class ItensController {
    @Autowired
    private ItensService itensService;

    @PostMapping("/add-multiple")
    public ResponseEntity<String> addItens(@RequestBody List<Map<String, Object>> itensData) {
        try {
            itensService.addMultipleItens(itensData);
            return ResponseEntity.ok("Itens adicionados com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao adicionar itens: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Itens> fetchUsers(){
        return  itensService.fetchItens();
    }

    @GetMapping("/{id}")
    public Itens fetchUserById(@PathVariable int id){
        return itensService.fetchItensById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        boolean isDeleted = itensService.deleteItensById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Item deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
        }
    }

    @GetMapping("/order/{idPedido}")
    public ResponseEntity<List<Itens>> fetchItensByPedidoId(@PathVariable Long idPedido) {
        try {
            List<Itens> itens = itensService.findItensByPedidoId(idPedido);
            if (itens.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(itens); // Retorna 204 se não houver itens
            }
            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retorna 500 em caso de erro
        }
    }
}
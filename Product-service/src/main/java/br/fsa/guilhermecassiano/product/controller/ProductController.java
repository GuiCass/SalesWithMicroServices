package br.fsa.guilhermecassiano.product.controller;

import br.fsa.guilhermecassiano.product.entity.Product;
import br.fsa.guilhermecassiano.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping(value = "/product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> fetchProduct(){
        return  productService.fetchProduct();
    }

    @GetMapping("/{id}")
    public Product fetchProductById(@PathVariable int id){
        return productService.fetchProductById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Produto deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    @PostMapping("/finalizar/{id}")
    public ResponseEntity<String> finalizarCompra(@PathVariable Long id, @RequestBody Map<Integer, Integer> carrinho) {
        try {
            productService.finalizarCompra(carrinho, id);
            return ResponseEntity.ok("Compra finalizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao finalizar a compra.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProductDetails(@PathVariable Integer id, @RequestBody Product updatedDetails) {
        try {
            productService.updateProductDetails(id, updatedDetails);
            return ResponseEntity.ok("Produto atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar produto.");
        }
    }
}
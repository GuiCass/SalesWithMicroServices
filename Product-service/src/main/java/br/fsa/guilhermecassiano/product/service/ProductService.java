package br.fsa.guilhermecassiano.product.service;

import br.fsa.guilhermecassiano.product.entity.Product;
import br.fsa.guilhermecassiano.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> fetchProduct(){
        return productRepository.findAll();
    }
    public Product fetchProductById(int id){
        return productRepository.findById(id).orElse(null);
    }

    public boolean deleteProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void finalizarCompra(Map<Integer, Integer> carrinho, Long userId) {
        try {
            Double valorTotal = 0.0;
            Long orderId;

            // Processa cada item no carrinho
            for (Map.Entry<Integer, Integer> entry : carrinho.entrySet()) {
                Integer productId = entry.getKey();
                Integer quantidade = entry.getValue();

                // Verifica o produto no banco de dados
                Product produto = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + productId));

                // Verifica se há estoque suficiente
                if (produto.getQuantidade() >= quantidade) {
                    // Atualiza a quantidade no estoque
                    produto.setQuantidade(produto.getQuantidade() - quantidade);
                    productRepository.save(produto);

                    // Soma ao valor total da compra
                    valorTotal += produto.getPreco() * quantidade;
                } else {
                    throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
                }
            }

            // Monta os dados da ordem como um mapa
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("usuarioId", userId);
            orderData.put("valorTotal", valorTotal);
            orderData.put("data", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // Faz a requisição para o endpoint de pedido
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://ORDER-SERVICE/order/add",
                    orderData,
                    Map.class
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Falha ao criar a ordem: " + response.getBody());
            }

            // Recupera o ID da ordem criada
            Map<String, Object> responseBody = response.getBody();
            orderId = Long.valueOf(responseBody.get("id").toString());

            // Cria a lista de itens para enviar ao serviço de itens
            List<Map<String, Object>> itensData = new ArrayList<>();

            for (Map.Entry<Integer, Integer> entry : carrinho.entrySet()) {
                Integer productId = entry.getKey();
                Integer quantidade = entry.getValue();

                Product produto = productRepository.findById(productId).orElse(null);
                if (produto == null) continue;

                // Adiciona os dados do item à lista
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("nome", produto.getNome());
                itemData.put("preco", produto.getPreco() * produto.getQuantidade());
                itemData.put("quantidade", quantidade);
                itemData.put("idPedido", orderId);
                itemData.put("idProduto", productId);

                itensData.add(itemData);
            }

            // Envia a lista de itens ao serviço de itens
            ResponseEntity<String> itemResponse = restTemplate.postForEntity(
                    "http://ITENS-SERVICE/itens/add-multiple",
                    itensData,
                    String.class
            );

            if (itemResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Falha ao adicionar itens ao serviço de itens: " + itemResponse.getBody());
            }

            System.out.println("Compra finalizada com sucesso para o usuário ID: " + userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao finalizar a compra: " + e.getMessage());
        }
    }

    public void updateProductDetails(Integer id, Product updatedDetails) {
        // Busca o produto no banco de dados
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        // Atualiza apenas os campos de preço e quantidade
        if (updatedDetails.getPreco() != null) {
            existingProduct.setPreco(updatedDetails.getPreco());
        }

        if (updatedDetails.getQuantidade() != null) {
            existingProduct.setQuantidade(updatedDetails.getQuantidade());
        }

        // Salva as alterações
        productRepository.save(existingProduct);
    }
}

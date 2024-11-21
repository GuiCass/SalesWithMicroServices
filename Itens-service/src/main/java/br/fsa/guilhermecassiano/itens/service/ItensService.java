package br.fsa.guilhermecassiano.itens.service;

import br.fsa.guilhermecassiano.itens.entity.Itens;
import br.fsa.guilhermecassiano.itens.repository.ItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItensService {
    @Autowired
    private ItensRepository itensRepository;

    public void addMultipleItens(List<Map<String, Object>> itensData) {
        for (Map<String, Object> itemData : itensData) {
            Itens item = new Itens();

            // Converte os dados do mapa para a entidade Itens
            item.setNome(itemData.get("nome").toString());
            item.setPreco(Double.valueOf(itemData.get("preco").toString()));
            item.setQuantidade(Integer.valueOf(itemData.get("quantidade").toString()));
            item.setIdPedido(Long.valueOf(itemData.get("idPedido").toString()));
            item.setIdProduto(Long.valueOf(itemData.get("idProduto").toString()));

            // Salva o item no banco
            itensRepository.save(item);
        }
    }

    public List<Itens> fetchItens(){
        return itensRepository.findAll();
    }
    public Itens fetchItensById(int id){
        return itensRepository.findById(id).orElse(null);
    }

    public boolean deleteItensById(int id) {
        Optional<Itens> user = itensRepository.findById(id);
        if (user.isPresent()) {
            itensRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Itens> findItensByPedidoId(Long idPedido) {
        return itensRepository.findByIdPedido(idPedido);
    }
}

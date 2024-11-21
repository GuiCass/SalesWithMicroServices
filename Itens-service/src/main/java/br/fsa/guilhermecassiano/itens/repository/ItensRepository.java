package br.fsa.guilhermecassiano.itens.repository;

import br.fsa.guilhermecassiano.itens.entity.Itens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensRepository extends JpaRepository<Itens,Integer> {
    List<Itens> findByIdPedido(Long idPedido);
}

package br.fsa.guilhermecassiano.usuario.repository;

import br.fsa.guilhermecassiano.usuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByNomeAndPassword(String nome, String password);
}

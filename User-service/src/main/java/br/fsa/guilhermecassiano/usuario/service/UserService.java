package br.fsa.guilhermecassiano.usuario.service;

import br.fsa.guilhermecassiano.usuario.entity.User;
import br.fsa.guilhermecassiano.usuario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }
    public List<User> fetchUsers(){
        return userRepository.findAll();
    }
    public User fetchUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User fetchUserByNameAndPassword(String nome, String password) {
        return userRepository.findByNomeAndPassword(nome, password);
    }

    public boolean deleteUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

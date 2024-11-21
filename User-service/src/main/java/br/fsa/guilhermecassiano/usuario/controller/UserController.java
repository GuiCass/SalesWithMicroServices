package br.fsa.guilhermecassiano.usuario.controller;

import br.fsa.guilhermecassiano.usuario.entity.User;
import br.fsa.guilhermecassiano.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> fetchUsers(){
        return  userService.fetchUsers();
    }

    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable int id){
        return userService.fetchUserById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<User> fetchUserByNameAndPassword(@RequestParam String nome, @RequestParam String password) {
        User user = userService.fetchUserByNameAndPassword(nome, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}
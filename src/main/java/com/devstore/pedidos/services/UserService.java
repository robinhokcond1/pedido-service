package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.User;
import com.devstore.pedidos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}

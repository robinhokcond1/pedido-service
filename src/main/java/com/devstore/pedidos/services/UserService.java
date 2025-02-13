package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.User;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.exceptions.ResourceNotFoundException;
import com.devstore.pedidos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retorna todos os usuários cadastrados no sistema.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID, lançando exceção caso não seja encontrado.
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    /**
     * Salva um novo usuário, garantindo que o e-mail não esteja duplicado.
     */
    @Transactional
    public User save(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new BusinessException("Já existe um usuário cadastrado com este e-mail.");
        }
        return userRepository.save(user);
    }

    /**
     * Deleta um usuário garantindo que ele exista antes da exclusão.
     */
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado para exclusão.");
        }
        userRepository.deleteById(id);
    }
}

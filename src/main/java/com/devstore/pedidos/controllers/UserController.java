package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.UserDTO;
import com.devstore.pedidos.entities.User;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retorna todos os usuários cadastrados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.findAll()
                .stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }

    /**
     * Busca um usuário pelo ID, lançando exceção caso o ID seja inválido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do usuário deve ser um número positivo.");
        }

        User user = userService.findById(id);
        return ResponseEntity.ok(DTOMapper.toUserDTO(user));
    }

    /**
     * Cria um novo usuário, validando os dados antes de salvar.
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (!userDTO.email().contains("@")) {
            throw new BusinessException("O e-mail informado não é válido.");
        }
        if (userDTO.password().length() < 6) {
            throw new BusinessException("A senha deve ter pelo menos 6 caracteres.");
        }

        User user = DTOMapper.toUserEntity(userDTO);
        User savedUser = userService.save(user);
        return ResponseEntity.status(201).body(DTOMapper.toUserDTO(savedUser));
    }

    /**
     * Deleta um usuário garantindo que o ID seja válido antes de chamar a Service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do usuário deve ser um número positivo.");
        }

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

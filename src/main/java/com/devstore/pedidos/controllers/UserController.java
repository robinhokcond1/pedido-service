package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.UserDTO;
import com.devstore.pedidos.entities.User;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.UserService;
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

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.findAll()
                .stream()
                .map(DTOMapper::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(DTOMapper.toUserDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = DTOMapper.toUserEntity(userDTO);
        User savedUser = userService.save(user);
        UserDTO responseDTO = DTOMapper.toUserDTO(savedUser);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

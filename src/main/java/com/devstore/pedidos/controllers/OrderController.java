package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.OrderDTO;
import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.enums.OrderStatus;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retorna todos os pedidos cadastrados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOS = orderService.findAll()
                .stream()
                .map(DTOMapper::toOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOS);
    }

    /**
     * Busca um pedido pelo ID, lançando exceção caso o ID seja inválido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do pedido deve ser um número positivo.");
        }

        Order order = orderService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }

    /**
     * Cria um novo pedido, validando os dados antes de salvar.
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = DTOMapper.toOrderEntity(orderDTO);
        Order savedOrder = orderService.save(order);
        return ResponseEntity.status(201).body(DTOMapper.toOrderDTO(savedOrder));
    }

    /**
     * Atualiza o status de um pedido existente, garantindo que o ID é válido.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        if (id <= 0) {
            throw new BusinessException("O ID do pedido deve ser um número positivo.");
        }

        orderService.updateStatus(id, status);
        Order updatedOrder = orderService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(updatedOrder));
    }

    /**
     * Deleta um pedido garantindo que o ID seja válido antes de chamar a Service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do pedido deve ser um número positivo.");
        }

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

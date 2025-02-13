package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.OrderDTO;
import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.enums.OrderStatus;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.OrderService;
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

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOS = orderService.findAll()
                .stream()
                .map(DTOMapper::toOrderDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = DTOMapper.toOrderEntity(orderDTO);
        Order saveOrder = orderService.save(order);
        OrderDTO responseDTO = DTOMapper.toOrderDTO(saveOrder);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
        Order updatedOrder = orderService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

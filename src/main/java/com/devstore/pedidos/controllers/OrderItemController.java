package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.OrderItemDTO;
import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.entities.OrderItem;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        List<OrderItemDTO> orderItemDTOS = orderItemService.findAll()
                .stream()
                .map(DTOMapper::toOrderItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderItemDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderItemDTO(orderItem));
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        OrderItem orderItem = DTOMapper.toOrderItemEntity(orderItemDTO);
        OrderItem orderItemSaved = orderItemService.save(orderItem);
        OrderItemDTO responseDTO = DTOMapper.toOrderItemDTO(orderItemSaved);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

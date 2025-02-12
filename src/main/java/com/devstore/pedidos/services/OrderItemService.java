package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.OrderItem;
import com.devstore.pedidos.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item do pedido não encontrado"));
    }

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void delete(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("Item do pedido não encontrado");
        }
        orderItemRepository.deleteById(id);
    }
}

package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.enums.OrderStatus;
import com.devstore.pedidos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void updateStatus(Long id, OrderStatus status) {
        Order order = findById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado");
        }
        orderRepository.deleteById(id);
    }
}

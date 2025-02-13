package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.OrderItem;
import com.devstore.pedidos.exceptions.ResourceNotFoundException;
import com.devstore.pedidos.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * Retorna todos os itens de pedidos cadastrados no sistema.
     */
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    /**
     * Busca um item do pedido pelo ID, lançando exceção caso não exista.
     */
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido com ID " + id + " não encontrado."));
    }

    /**
     * Salva um novo item de pedido no banco de dados.
     */
    @Transactional
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Deleta um item do pedido garantindo que ele exista antes da exclusão.
     */
    @Transactional
    public void delete(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item do pedido com ID " + id + " não encontrado para exclusão.");
        }
        orderItemRepository.deleteById(id);
    }
}

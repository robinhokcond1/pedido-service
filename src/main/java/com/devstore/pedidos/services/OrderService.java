package com.devstore.pedidos.services;

import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.enums.OrderStatus;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.exceptions.ResourceNotFoundException;
import com.devstore.pedidos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retorna todos os pedidos cadastrados no sistema.
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Busca um pedido pelo ID, lançando uma exceção caso não seja encontrado.
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido com ID " + id + " não encontrado."));
    }

    /**
     * Salva um novo pedido no banco de dados.
     */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Atualiza o status de um pedido existente, lançando exceções caso:
     * - O pedido não seja encontrado.
     * - O status seja alterado para um inválido.
     */
    @Transactional
    public void updateStatus(Long id, OrderStatus status) {
        Order order = findById(id);

        if (order.getStatus() == OrderStatus.PAID) {
            throw new BusinessException("Não é possível alterar o status de um pedido já pago.");
        }

        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * Deleta um pedido do banco de dados, garantindo que ele exista antes de excluir.
     */
    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido com ID " + id + " não encontrado para exclusão.");
        }
        orderRepository.deleteById(id);
    }
}

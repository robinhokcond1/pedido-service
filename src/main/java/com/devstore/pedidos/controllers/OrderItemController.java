package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.OrderItemDTO;
import com.devstore.pedidos.entities.OrderItem;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.OrderItemService;
import jakarta.validation.Valid;
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

    /**
     * Retorna todos os itens de pedidos cadastrados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        List<OrderItemDTO> orderItemDTOS = orderItemService.findAll()
                .stream()
                .map(DTOMapper::toOrderItemDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderItemDTOS);
    }

    /**
     * Busca um item de pedido pelo ID, lançando exceção caso o ID seja inválido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do item do pedido deve ser um número positivo.");
        }

        OrderItem orderItem = orderItemService.findById(id);
        return ResponseEntity.ok(DTOMapper.toOrderItemDTO(orderItem));
    }

    /**
     * Cria um novo item de pedido, validando os dados antes de salvar.
     */
    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
        OrderItem orderItem = DTOMapper.toOrderItemEntity(orderItemDTO);
        OrderItem orderItemSaved = orderItemService.save(orderItem);
        return ResponseEntity.status(201).body(DTOMapper.toOrderItemDTO(orderItemSaved));
    }

    /**
     * Deleta um item de pedido garantindo que o ID seja válido antes de chamar a Service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do item do pedido deve ser um número positivo.");
        }

        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

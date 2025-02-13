package com.devstore.pedidos.mapper;

import com.devstore.pedidos.dto.OrderDTO;
import com.devstore.pedidos.dto.OrderItemDTO;
import com.devstore.pedidos.dto.ProductDTO;
import com.devstore.pedidos.dto.UserDTO;
import com.devstore.pedidos.entities.Order;
import com.devstore.pedidos.entities.OrderItem;
import com.devstore.pedidos.entities.Product;
import com.devstore.pedidos.entities.User;

import java.util.HashSet;
import java.util.stream.Collectors;

public class DTOMapper {

    // Converter User -> UserDTO
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getOrders().stream().map(DTOMapper::toOrderDTO).collect(Collectors.toList())
        );
    }

    // Converter Order -> OrderDTO
    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getUsername(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getItems().stream().map(DTOMapper::toOrderItemDTO).collect(Collectors.toList())
        );
    }

    // Converter OrderItem -> OrderItemDTO
    public static OrderItemDTO toOrderItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getId(),
                toProductDTO(item.getProduct()),
                item.getQuantity(),
                item.getPrice()
        );
    }

    // Converter Product -> ProductDTO
    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    // Converter UserDTO -> User
    public static User toUserEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setRole(dto.role());

        // Converte List<OrderDTO> para Set<Order> para evitar erro de tipo
        if (dto.orders() != null) {
            user.setOrders(dto.orders().stream()
                    .map(DTOMapper::toOrderEntity)
                    .collect(Collectors.toSet())); // Converte para Set<Order>
        } else {
            user.setOrders(new HashSet<>()); // Evita NullPointerException
        }

        return user;
    }

    public static Order toOrderEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.id());
        order.setStatus(dto.status());
        order.setCreatedAt(dto.createdAt());

        // Convertendo List<OrderItemDTO> para Set<OrderItem>
        if (dto.items() != null) {
            order.setItems(dto.items().stream()
                    .map(DTOMapper::toOrderItemEntity)
                    .collect(Collectors.toSet())); // Agora converte corretamente para Set<OrderItem>
        } else {
            order.setItems(new HashSet<>()); // Evita NullPointerException
        }

        return order;
    }

    // Converter OrderItemDTO -> OrderItem
    public static OrderItem toOrderItemEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setId(dto.id());
        item.setProduct(toProductEntity(dto.product()));
        item.setQuantity(dto.quantity());
        item.setPrice(dto.price());

        return item;
    }

    // Converter ProductDTO -> Product
    public static Product toProductEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());

        return product;
    }
}

package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.ProductDTO;
import com.devstore.pedidos.entities.Product;
import com.devstore.pedidos.exceptions.BusinessException;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Retorna todos os produtos cadastrados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = productService.findAll()
                .stream()
                .map(DTOMapper::toProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    /**
     * Busca um produto pelo ID, lançando exceção caso o ID seja inválido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do produto deve ser um número positivo.");
        }

        Product product = productService.findById(id);
        return ResponseEntity.ok(DTOMapper.toProductDTO(product));
    }

    /**
     * Cria um novo produto, validando os dados antes de salvar.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        if (productDTO.price() < 0) {
            throw new BusinessException("O preço do produto não pode ser negativo.");
        }
        if (productDTO.stockQuantity() < 0) {
            throw new BusinessException("A quantidade em estoque não pode ser negativa.");
        }

        Product product = DTOMapper.toProductEntity(productDTO);
        Product productSaved = productService.save(product);
        return ResponseEntity.status(201).body(DTOMapper.toProductDTO(productSaved));
    }

    /**
     * Deleta um produto garantindo que o ID seja válido antes de chamar a Service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (id <= 0) {
            throw new BusinessException("O ID do produto deve ser um número positivo.");
        }

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

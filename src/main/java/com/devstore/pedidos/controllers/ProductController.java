package com.devstore.pedidos.controllers;

import com.devstore.pedidos.dto.ProductDTO;
import com.devstore.pedidos.entities.Product;
import com.devstore.pedidos.mapper.DTOMapper;
import com.devstore.pedidos.services.ProductService;
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

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = productService.findAll()
                .stream()
                .map(DTOMapper::toProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(DTOMapper.toProductDTO(product));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = DTOMapper.toProductEntity(productDTO);
        Product productSaved = productService.save(product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(productSaved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

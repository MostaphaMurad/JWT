package com.jwt.Controllers;

import com.jwt.Models.Product;
import com.jwt.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApi {
    @Autowired private ProductRepository productRepository;
    @GetMapping
    public List<Product> allProductst(){
        return productRepository.findAll();
    }
    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product savedProduct=productRepository.save(product);
        URI productUri=URI.create("/products/create/"+savedProduct.getId());
        return ResponseEntity.created(productUri).body(savedProduct);
    }
}

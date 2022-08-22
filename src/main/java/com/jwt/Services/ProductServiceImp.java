package com.jwt.Services;

import com.jwt.Models.Product;
import com.jwt.Repositories.ProductRepository;
import com.speedment.jpastreamer.application.JPAStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImp implements ProductServices {
    @Autowired private JPAStreamer jpaStreamer;
    @Autowired private ProductRepository productRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        System.out.println("Processing stream");
        List<Product>productList =new ArrayList<>();
        try (Stream<Product> emps = productRepository.Products()) {
            emps.forEach(item -> {
                System.out.println(item.getName());
                productList.add(item);
            });
        }
        return productList;
    }
}

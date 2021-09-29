package com.productapi.controller;

import com.productapi.model.Product;
import com.productapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private IProductService productService;
@GetMapping("/products")
    public ResponseEntity<Iterable<Product>> findAllApi(){
    Iterable<Product>products = productService.findAll();
    return new ResponseEntity<>(products, HttpStatus.OK);
}
@PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody Product product){
    productService.save(product);
    return new ResponseEntity(HttpStatus.CREATED);
}
@PutMapping("/products/{id}")
    public ResponseEntity<Void> editProduct(@PathVariable Long id, @RequestBody Product product){
    Optional<Product> productOptional = productService.findById(id);
    if (!productOptional.isPresent()) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    product.setId(id);
    productService.save(product);
    return new ResponseEntity(HttpStatus.OK);
}
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product>deleteProduct(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if(!productOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.delete(id);
        return new ResponseEntity<>(productOptional.get(),HttpStatus.NO_CONTENT);
    }
}

package com.crudapp.products.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crudapp.products.model.Product;
import com.crudapp.products.service.ProductService;


@RestController
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
   
	@GetMapping(value = "/products")
	public List<Product> getAll() {
        logger.info("Getting All Products");
		return productService.getAllProducts();
	}
	
    @PostMapping(path = "/product")
    public ResponseEntity<String> saveProduct(@RequestBody Product product){
        logger.info("Saving the Product");
    	 return productService.saveProduct(product);
    }
    
    @GetMapping(path="/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
    	logger.info("Getting the product by id: {}",id);
        return productService.getProductById(id);
    }
    
    @PutMapping(path="/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id,@RequestBody Product product){
        logger.info("Updating the product");
        return productService.updateProductById(id,product);
    }

    @DeleteMapping(path="/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id){
        logger.info("Deleting the Product by id: {}", id);
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	
}

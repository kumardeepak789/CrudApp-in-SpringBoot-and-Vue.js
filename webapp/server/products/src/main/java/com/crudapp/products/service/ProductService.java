package com.crudapp.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crudapp.products.model.Product;
import com.crudapp.products.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
	private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<String> saveProduct(Product item) {
        String message=validateRequest(item);
        if(message!=null)
            return ResponseEntity.badRequest().body(message); 
        Product savedItem=productRepository.save(item); 
        if(savedItem!=null) {
    		return ResponseEntity.ok("Successfully saved"); // 200 OK  
    	}
    	else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong"); // 500 Internal Server Error 
		}
    }

    public ResponseEntity<Product> getProductById(long id) {
        
    	Optional<Product> itemOptional = productRepository.findById(id);

        if (itemOptional.isPresent()) {
            Product item = itemOptional.get();
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> updateProductById(long id,Product item) {
        Optional<Product> itemOptional = productRepository.findById(id);
        
        String message =validateRequest(item);
        if(message!=null){
            return ResponseEntity.badRequest().body(message);
        }
        if (itemOptional.isPresent()) {
        	Product existingItem = itemOptional.get();

            existingItem.setName(item.getName());
            existingItem.setPrice(item.getPrice());
            existingItem.setDescription(item.getDescription());
            
            Product savedItem = productRepository.save(existingItem);
            
            return savedItem!=null ? new ResponseEntity<>("Successfully updated", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
        	Product savedItem = productRepository.save(item);
        	return savedItem!=null ? new ResponseEntity<>("Successfully created",HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
            
        }
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
    
    public String validateRequest(Product product){
        String errorMessage=null;
        if( product.getName()==null || product.getName().isEmpty() || product.getName().isBlank()){
            errorMessage="product name cannot be empty";
            return errorMessage;
        }    
        else if(product.getPrice()<=0)
            errorMessage="product price should be greater than 0";
         
        return errorMessage;

    }


}
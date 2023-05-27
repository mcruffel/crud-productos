package com.agenciacristal.crud.product;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping(path = "api/v2/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService ){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<Object> registrarProducto(@RequestBody Product product){
        return this.productService.newProduct(product);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarProducto(@RequestBody Product product){
        try{
            return this.productService.newProduct(product);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no existe ese producto");
        }
    }

    @DeleteMapping(path="{productId}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable ("productId")Long id){
        return this.productService.deleteProduct(id);
    }
}

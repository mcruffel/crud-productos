package com.agenciacristal.crud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    HashMap<String,Object> datos;
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public List<Product> getProducts(){
        return this.productRepository.findAll();
    }
    public ResponseEntity<Object> newProduct(Product product) {
        Optional<Product> res = productRepository.findProductByName(product.getName());
        datos=new HashMap<>();

        if(res.isPresent()&& product.getId()==null){
            datos.put("error", true);
            datos.put("menssage","ya existe un producto con ese nombre");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        datos.put("menssage","se guardó con exito");
        if(product.getId()!=null){
            datos.put("menssage","se actualizó con exito");
        }
        productRepository.save(product);
        datos.put("data", product);
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteProduct(Long id){
        datos=new HashMap<>();
        boolean existe=this.productRepository.existsById(id);
        if(!existe){
            datos.put("error", true);
            datos.put("menssage","no existe un producto con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }else{
            datos.put("menssage","producto eliminado");
            this.productRepository.deleteById(id);
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.ACCEPTED
            );
        }
    }
}

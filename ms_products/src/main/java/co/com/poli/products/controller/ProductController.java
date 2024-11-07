package co.com.poli.products.controller;

import co.com.poli.products.domain.Product;
import co.com.poli.products.service.interfaces.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("{id}")
    public Mono<Product> getProduct(@PathVariable("id")  Long id){
        return productService.getProduct(id);
    }

    @GetMapping()
    public Flux<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping()
    public Mono<Product> createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public Mono<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }
}

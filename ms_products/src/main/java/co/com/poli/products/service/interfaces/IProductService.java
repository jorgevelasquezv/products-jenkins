package co.com.poli.products.service.interfaces;

import co.com.poli.products.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> getProduct(Long id);
    Flux<Product> getProducts();
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Long id, Product product);
    Mono<Void> deleteProduct(Long id);
}

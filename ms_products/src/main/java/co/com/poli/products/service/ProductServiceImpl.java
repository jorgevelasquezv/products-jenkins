package co.com.poli.products.service;

import co.com.poli.products.domain.Product;
import co.com.poli.products.exceptions.NotFoundException;
import co.com.poli.products.repository.ProductRepository;
import co.com.poli.products.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> getProduct(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")));
    }

    @Override
    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> updateProduct(Long id, Product product) {
        return getProduct(id)
                .flatMap(partialUpdate(product));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return getProduct(id)
                .flatMap(productRepository::delete);
    }

    private Function<Product, Mono<Product>> partialUpdate(Product product) {
        return p -> {
            if (product.getName() != null) p.setName(product.getName());
            if (product.getPrice() != null) p.setPrice(product.getPrice());
            if (product.getDescription() != null) p.setDescription(product.getDescription());
            if (product.getQuantity() != null) p.setQuantity(product.getQuantity());
            return productRepository.save(p);
        };
    }
}

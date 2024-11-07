package co.com.poli.products.service;

import co.com.poli.products.domain.Product;
import co.com.poli.products.exceptions.NotFoundException;
import co.com.poli.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProduct() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(1000.0);
        product.setQuantity(10L);

        when(productRepository.findById(id)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProduct(id))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void getProductException() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(productService.getProduct(id))
                .verifyErrorMatches(throwable ->
                        throwable instanceof NotFoundException && throwable.getMessage().equals("Product not found"));
    }

    @Test
    void getProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(1000.0);
        product1.setQuantity(10L);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(2000.0);
        product2.setQuantity(20L);

        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(productService.getProducts())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(1000.0);
        product.setQuantity(10L);

        when(productRepository.save(product)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.createProduct(product))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void updateProduct() {
        Long id = 1L;

        Product productDB = new Product();
        productDB.setId(id);
        productDB.setName("Product 1");
        productDB.setDescription("Description 1");
        productDB.setPrice(1000.0);
        productDB.setQuantity(10L);

        Product productToUpdate = new Product();
        productToUpdate.setName("Product 1");
        productToUpdate.setDescription("Description 1");
        productToUpdate.setPrice(2000.0);
        productToUpdate.setQuantity(20L);

        Product productUpdated = new Product();
        productUpdated.setId(id);
        productUpdated.setName("Product 1");
        productUpdated.setDescription("Description 1");
        productUpdated.setPrice(2000.0);
        productUpdated.setQuantity(20L);

        when(productRepository.findById(id)).thenReturn(Mono.just(productDB));
        when(productRepository.save(productDB)).thenReturn(Mono.just(productUpdated));

        StepVerifier.create(productService.updateProduct(id, productToUpdate))
                .expectNext(productUpdated)
                .verifyComplete();
    }

    @Test
    void partialUpdate() {
        Long id = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        existingProduct.setQuantity(10L);

        Product updateProduct = new Product();
        updateProduct.setPrice(200.0);
        updateProduct.setQuantity(20L);

        when(productRepository.findById(id)).thenReturn(Mono.just(existingProduct));

        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setQuantity(updateProduct.getQuantity());

        when(productRepository.save(existingProduct)).thenReturn(Mono.just(existingProduct));

        StepVerifier.create(productService.updateProduct(id, updateProduct))
                .expectNext(existingProduct)
                .verifyComplete();
    }

    @Test
    void partialUpdateNotParameters() {
        Long id = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        existingProduct.setQuantity(10L);

        Product updateProduct = new Product();

        when(productRepository.findById(id)).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(Mono.just(existingProduct));

        StepVerifier.create(productService.updateProduct(id, updateProduct))
                .expectNext(existingProduct)
                .verifyComplete();
    }

    @Test
    void updateProductException() {
        Long id = 1L;

        Product productToUpdate = new Product();
        productToUpdate.setName("Product 1");
        productToUpdate.setDescription("Description 1");
        productToUpdate.setPrice(2000.0);
        productToUpdate.setQuantity(20L);

        when(productRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(productService.updateProduct(id, productToUpdate))
                .verifyErrorMatches(throwable ->
                        throwable instanceof NotFoundException && throwable.getMessage().equals("Product not found"));
    }

    @Test
    void deleteProduct() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(1000.0);
        product.setQuantity(10L);

        when(productRepository.findById(id)).thenReturn(Mono.just(product));
        when(productRepository.delete(product)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(id))
                .verifyComplete();
    }

    @Test
    void deleteProductException() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(productService.deleteProduct(id))
                .verifyErrorMatches(throwable ->
                        throwable instanceof NotFoundException && throwable.getMessage().equals("Product not found"));
    }
}
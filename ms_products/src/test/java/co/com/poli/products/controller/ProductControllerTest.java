package co.com.poli.products.controller;

import co.com.poli.products.domain.Product;
import co.com.poli.products.exceptions.NotFoundException;
import co.com.poli.products.exceptions.handler.GlobalHandlerError;
import co.com.poli.products.exceptions.response.ErrorResponse;
import co.com.poli.products.repository.ProductRepository;
import co.com.poli.products.service.interfaces.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        ProductController.class,
        IProductService.class,
        GlobalHandlerError.class,
        ProductRepository.class})
@WebFluxTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProduct() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(1000.0);
        product.setQuantity(10L);

        when(productService.getProduct(id)).thenReturn(Mono.just(product));

        webTestClient.get()
                .uri("/api/v1/products/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .value(returnedProduct -> {
                    assertEquals(product.getId(), returnedProduct.getId());
                    assertEquals(product.getName(), returnedProduct.getName());
                    assertEquals(product.getDescription(), returnedProduct.getDescription());
                    assertEquals(product.getPrice(), returnedProduct.getPrice());
                    assertEquals(product.getQuantity(), returnedProduct.getQuantity());
                });
    }

    @Test
    void getProductNotFound() {
        Long id = 1L;
        when(productService.getProduct(id))
                .thenReturn(Mono.error(new NotFoundException("Product not found")));

        webTestClient.get()
                .uri("/api/v1/products/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void getProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(10.99);
        product1.setQuantity(100L);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(20.99);
        product2.setQuantity(200L);

        when(productService.getProducts()).thenReturn(Flux.just(product1, product2));

        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2)
                .value(products -> {
                    assertEquals(product1.getId(), products.get(0).getId());
                    assertEquals(product1.getName(), products.get(0).getName());
                    assertEquals(product1.getDescription(), products.get(0).getDescription());
                    assertEquals(product1.getPrice(), products.get(0).getPrice());
                    assertEquals(product1.getQuantity(), products.get(0).getQuantity());

                    assertEquals(product2.getId(), products.get(1).getId());
                    assertEquals(product2.getName(), products.get(1).getName());
                    assertEquals(product2.getDescription(), products.get(1).getDescription());
                    assertEquals(product2.getPrice(), products.get(1).getPrice());
                    assertEquals(product2.getQuantity(), products.get(1).getQuantity());
                });
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(10.99);
        product.setQuantity(100L);

        Product productCreated = new Product();
        productCreated.setId(1L);
        productCreated.setName("Product 1");
        productCreated.setDescription("Description 1");
        productCreated.setPrice(10.99);
        productCreated.setQuantity(100L);

        when(productService.createProduct(product)).thenReturn(Mono.just(productCreated));

        webTestClient.post()
                .uri("/api/v1/products")
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void createProductBadRequest() {
        Product product = new Product();
        product.setDescription("Description 1");
        product.setPrice(10.99);
        product.setQuantity(100L);

        when(productService.createProduct(product))
                .thenReturn(Mono.error(new IllegalArgumentException("Invalid product")));

        webTestClient.post()
                .uri("/api/v1/products")
                .bodyValue(product)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(IllegalArgumentException.class);
    }

    @Test
    void updateProduct() {
        Long id = 1L;
        Product productToUpdate = new Product();
        productToUpdate.setName("Product 1");
        productToUpdate.setDescription("Description 1");
        productToUpdate.setPrice(10.99);
        productToUpdate.setQuantity(100L);

        Product productUpdated = new Product();
        productUpdated.setId(id);
        productUpdated.setName("Product 1 Updated");
        productUpdated.setDescription("Description 1 Updated");
        productToUpdate.setPrice(10.99);
        productToUpdate.setQuantity(100L);

        when(productService.updateProduct(id, productToUpdate)).thenReturn(Mono.just(productUpdated));

        webTestClient.put()
                .uri("/api/v1/products/{id}", id)
                .bodyValue(productToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void deleteProduct() {
        Long id = 1L;

        when(productService.deleteProduct(id)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/products/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    void deleteProductNotFound() {
        Long id = 1L;

        when(productService.deleteProduct(id))
                .thenReturn(Mono.error(new NotFoundException("Product not found")));

        webTestClient.delete()
                .uri("/api/v1/products/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void responseStatusException() {
        webTestClient.get()
                .uri("/api/v1/products-*")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class);
    }

    @Test
    void createProductIllegalArgumentException() {
        Product invalidProduct = new Product();
        invalidProduct.setName("");
        invalidProduct.setDescription("");
        invalidProduct.setPrice(-10.99);
        invalidProduct.setQuantity(-100L);

        when(productService.createProduct(invalidProduct))
                .thenThrow(new IllegalArgumentException("Invalid product"));

        webTestClient.post()
                .uri("/api/v1/products")
                .bodyValue(invalidProduct)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .value(errorResponse -> {
                    assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getCode());
                    assertEquals("Validation error", errorResponse.getMessage());
                });
    }
}
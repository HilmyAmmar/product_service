package id.ac.ui.cs.advprog.product_service.controller;

import id.ac.ui.cs.advprog.product_service.model.Market;
import id.ac.ui.cs.advprog.product_service.model.Product;
import id.ac.ui.cs.advprog.product_service.service.MarketService;
import id.ac.ui.cs.advprog.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Object> findAllProduct() {
        List<Product> productList = productService.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addProducts")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

@RestController
@RequestMapping("/markets")
class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping
    public ResponseEntity<Object> findAllMarket() {
        List<Market> marketList = marketService.findAll();
        return new ResponseEntity<>(marketList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findMarketById(@PathVariable Long id) {
        Optional<Market> market = marketService.findById(id);
        return market.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Market not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Object> findProductByMarket(@PathVariable Long id) {
        List<Product> productList = marketService.findProductByMarket(id);
        if (productList.isEmpty()) {
            return new ResponseEntity<>("No products found for market with id: " + id, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }
}
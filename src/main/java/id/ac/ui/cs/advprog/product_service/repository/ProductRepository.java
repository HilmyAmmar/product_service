package id.ac.ui.cs.advprog.product_service.repository;

import id.ac.ui.cs.advprog.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

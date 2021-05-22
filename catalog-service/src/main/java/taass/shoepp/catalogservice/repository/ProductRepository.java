package taass.shoepp.catalogservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import taass.shoepp.catalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = { "sizes", "categories", "brands" })
    List<Product> findAllEagerBy();

    @EntityGraph(attributePaths = { "sizes", "categories", "brands" })
    Optional<Product> findById(Long id);
}

package taass.shoepp.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taass.shoepp.catalogservice.entity.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByName(String brand);

    @Query("SELECT DISTINCT b.name FROM Brand b")
    List<String> findAllBrands();
}

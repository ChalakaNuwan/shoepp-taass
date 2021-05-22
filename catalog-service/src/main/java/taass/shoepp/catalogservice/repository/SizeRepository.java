package taass.shoepp.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taass.shoepp.catalogservice.entity.Brand;
import taass.shoepp.catalogservice.entity.Size;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {

    List<Size> findByValue(String size);

    @Query("SELECT DISTINCT s.value FROM Size s")
    List<String> findAllSizes();
}

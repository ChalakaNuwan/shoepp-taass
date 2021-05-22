package taass.shoepp.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.catalogservice.entity.ProductConsumption;

import java.util.UUID;

public interface ProductConsumptionRepository extends JpaRepository<ProductConsumption, UUID> {
}

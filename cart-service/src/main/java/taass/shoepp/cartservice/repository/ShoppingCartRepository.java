package taass.shoepp.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.cartservice.entity.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    void deleteByUserId(Long userId);

    Optional<ShoppingCart> findByUserId(Long userId);
}

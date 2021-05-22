package taass.shoepp.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.cartservice.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

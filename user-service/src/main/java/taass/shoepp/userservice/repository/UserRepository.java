package taass.shoepp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

package taass.shoepp.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.userservice.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

package taass.shoepp.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.shoepp.paymentservice.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    void deleteByOrderId(UUID orderId);

    Optional<Payment> findByOrderId(UUID orderId);

    Optional<Payment> findByIdAndUserId(Long paymentId, Long userId);
}

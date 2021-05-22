package taass.shoepp.paymentservice.service;

import org.springframework.http.ResponseEntity;
import taass.shoepp.events.order.OrderEvent;
import taass.shoepp.events.payment.PaymentEvent;
import taass.shoepp.events.payment.PaymentStatus;
import taass.shoepp.paymentservice.dto.PaymentForm;
import taass.shoepp.paymentservice.entity.Payment;

import java.util.UUID;

public interface PaymentService {

    PaymentEvent checkOrderEvent(OrderEvent orderEvent);

    void cancelOrderEvent(OrderEvent orderEvent);

    void payWithCard(PaymentForm paymentForm, Long userId);

    ResponseEntity payWithPaypal(PaymentForm paymentForm, Long userId);

    PaymentStatus getPaymentStatus(Long paymentId, Long userId);

    Payment getPayment(UUID orderId, Long userId);

    ResponseEntity executePaypalPayment(UUID orderId, String paymentId, String payerId);

    ResponseEntity cancelPayPalPayment(UUID orderId, String paymentId, String payerId);
}

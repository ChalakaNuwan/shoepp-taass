package taass.shoepp.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.shoepp.events.payment.PaymentStatus;
import taass.shoepp.paymentservice.dto.PaymentForm;
import taass.shoepp.paymentservice.entity.Payment;
import taass.shoepp.paymentservice.service.PaymentService;
import taass.shoepp.paymentservice.utility.RestUtility;

import javax.validation.Valid;

import java.util.UUID;

import static taass.shoepp.paymentservice.utility.RestUtility.HEADER_AUTH;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> payWithCard(@RequestHeader(HEADER_AUTH) String tokenHeader, @RequestBody PaymentForm paymentForm) {
        Long userId = RestUtility.getUserId(tokenHeader);
        paymentService.payWithCard(paymentForm, userId);
        return ResponseEntity.ok("Payment request received");
    }

    @PostMapping("/paypal")
    public ResponseEntity payWithPayPal(@RequestHeader(HEADER_AUTH) String tokenHeader, @RequestBody @Valid PaymentForm paymentForm) {
        Long userId = RestUtility.getUserId(tokenHeader);
        return paymentService.payWithPaypal(paymentForm, userId);
    }

    @PostMapping(value = "/paypal/execute/{orderId}/{paymentId}/{payerId}")
    public ResponseEntity executePayPalPayment(@PathVariable UUID orderId, @PathVariable String paymentId, @PathVariable String payerId) {
        return paymentService.executePaypalPayment(orderId, paymentId, payerId);
    }

    @PostMapping(value = "/paypal/cancel/{orderId}/{paymentId}/{payerId}")
    public ResponseEntity cancelPayPalPayment(@PathVariable UUID orderId, @PathVariable String paymentId, @PathVariable String payerId) {
        return paymentService.cancelPayPalPayment(orderId, paymentId, payerId);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@RequestHeader(HEADER_AUTH) String tokenHeader, @PathVariable Long paymentId) {
        Long userId = RestUtility.getUserId(tokenHeader);
        PaymentStatus paymentStatus = paymentService.getPaymentStatus(paymentId, userId);
        return ResponseEntity.ok(paymentStatus);
    }

    @GetMapping("/payment/{orderId}")
    public ResponseEntity<Payment> getPayment(@RequestHeader(HEADER_AUTH) String tokenHeader, @PathVariable UUID orderId) {
        Long userId = RestUtility.getUserId(tokenHeader);
        Payment payment = paymentService.getPayment(orderId, userId);
        return ResponseEntity.ok(payment);
    }
}

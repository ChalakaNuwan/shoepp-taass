package taass.shoepp.paymentservice.service;

import com.paypal.api.payments.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import taass.shoepp.dto.PaymentDto;
import taass.shoepp.events.order.OrderEvent;
import taass.shoepp.events.payment.PaymentEvent;
import taass.shoepp.events.payment.PaymentStatus;
import taass.shoepp.paymentservice.dto.PaymentForm;
import taass.shoepp.paymentservice.entity.Payment;
import taass.shoepp.paymentservice.repository.PaymentRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private PaymentStatusPublisher paymentStatusPublisher;

    @Override
    @Transactional
    public PaymentEvent checkOrderEvent(OrderEvent orderEvent) {
        var purchaseOrder = orderEvent.getPurchaseOrder();
        Optional<Payment> paymentOptional = paymentRepository.findByOrderId(purchaseOrder.getOrderId());

        if (paymentOptional.isEmpty()) return null;

        Payment payment = paymentOptional.get();
        PaymentDto paymentDto = PaymentDto.of(payment.getOrderId(), purchaseOrder.getUserId(), payment.getTotal());
        return new PaymentEvent(paymentDto, payment.getStatus());
    }

    @Override
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        paymentRepository.deleteByOrderId(orderEvent.getPurchaseOrder().getOrderId());

        //Qui dentro logica per il rimborso di un pagamento non andato a buon fine.
    }

    @Override
    public void payWithCard(PaymentForm paymentForm, Long userId) {
        Payment payment = new Payment();
        payment.setOrderId(paymentForm.getOrderId());
        payment.setUserId(userId);
        payment.setTotal(paymentForm.getTotal());
        payment.setCardName(paymentForm.getCardName());
        payment.setCardNumber(paymentForm.getCardNumber());
        payment.setHolderName(paymentForm.getHolderName());
        payment.setExpiryMonth(paymentForm.getExpiryMonth());
        payment.setExpiryYear(paymentForm.getExpiryYear());
        payment.setCvc(paymentForm.getCvc());
        payment.setType("CC"); //CreditCard

        try {
            //Qui fare validazione carta e relative connessioni a sistemi bancari.
            payment.setStatus(PaymentStatus.RESERVED); //Supponiamo che se arriviamo fin qui non incontriamo problemi nel pagamento e quindi lo consideriamo valido.
        } catch (Exception e) {
           payment.setStatus(PaymentStatus.REJECTED); //Supponiamo che in caso di errore del pagamento si ottenga una eccezione.
        }

        paymentRepository.save(payment); //La transazione viene registrata sia in caso di successo che non.

        PaymentDto paymentDto = PaymentDto.of(payment.getOrderId(), userId, payment.getTotal());
        paymentStatusPublisher.raiserPaymentStauts(paymentDto, payment.getStatus());
    }

    @Override
    public ResponseEntity payWithPaypal(PaymentForm paymentForm, Long userId) {
        Payment payment = new Payment();
        payment.setOrderId(paymentForm.getOrderId());
        payment.setUserId(userId);
        payment.setTotal(paymentForm.getTotal());
        payment.setType("PP"); //PayPal

        com.paypal.api.payments.Payment paypalPayment = paypalService.createPayment(payment.getOrderId(), payment.getTotal(), "Shoepp");

        if (paypalPayment != null) {
            Links links = paypalService.getLink(paypalPayment);
            payment.setPaypalPaymentId(paypalPayment.getId());
            paymentRepository.save(payment);
            HashMap<String, Object> response = new HashMap<>();
            response.put("link", links);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity executePaypalPayment(UUID orderId, String paymentId, String payerId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);

        if (payment.isPresent()) {
            boolean paymentStatus = false;
            paymentStatus = paypalService.executePayment(payerId, paymentId);

            PaymentDto paymentDto = PaymentDto.of(payment.get().getOrderId(), payment.get().getUserId(), payment.get().getTotal());

            if (paymentStatus) {
                payment.get().setStatus(PaymentStatus.RESERVED);
                paymentRepository.save(payment.get());

                System.out.println(">>> STATUS: OK SALVO?");
                paymentStatusPublisher.raiserPaymentStauts(paymentDto, payment.get().getStatus());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                paymentStatusPublisher.raiserPaymentStauts(paymentDto, payment.get().getStatus());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity cancelPayPalPayment(UUID orderId, String paymentId, String payerId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if (payment.isPresent()) {
            payment.get().setStatus(PaymentStatus.REJECTED);

            PaymentDto paymentDto = PaymentDto.of(payment.get().getOrderId(), payment.get().getUserId(), payment.get().getTotal());
            paymentStatusPublisher.raiserPaymentStauts(paymentDto, payment.get().getStatus());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public PaymentStatus getPaymentStatus(Long paymentId, Long userId) throws ResponseStatusException {
        Payment payment = paymentRepository.findByIdAndUserId(paymentId, userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment doesn't exist"));

        return payment.getStatus();
    }

    @Override
    public Payment getPayment(UUID orderId, Long userId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow((()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paymeny dosn't exists")));
        return payment;
    }
}

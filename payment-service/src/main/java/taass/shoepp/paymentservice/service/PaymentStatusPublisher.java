package taass.shoepp.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import taass.shoepp.dto.PaymentDto;
import taass.shoepp.events.payment.PaymentEvent;
import taass.shoepp.events.payment.PaymentStatus;

@Service
public class PaymentStatusPublisher {

    @Autowired
    private Sinks.Many<PaymentEvent> orderSink;

    public void raiserPaymentStauts(final PaymentDto paymentDto, PaymentStatus paymentStatus) {
        PaymentEvent paymentEvent = new PaymentEvent(paymentDto, paymentStatus);
        orderSink.tryEmitNext(paymentEvent); //Viene mandato l'evento con l'esito sul pagamento.
    }
}

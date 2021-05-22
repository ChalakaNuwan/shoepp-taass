package taass.shoepp.paymentservice.config;

import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import taass.shoepp.events.order.OrderEvent;
import taass.shoepp.events.order.OrderStatus;
import taass.shoepp.events.payment.PaymentEvent;
import taass.shoepp.paymentservice.service.PaymentService;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Configuration
public class PaymentConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Sinks.Many<PaymentEvent> orderSink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return flux -> flux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent){
        System.out.println("PAYMENT: ARRIVATO QUALCOSA -> " + orderEvent.toString());
        if (orderEvent.getOrderStatus().equals(OrderStatus.ORDER_CREATED)){
            try {
                System.out.println("MI FERMO 5SEC");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("DOPO 5SEC");
            return Mono.fromSupplier(() -> paymentService.checkOrderEvent(orderEvent));
        } else {
            return Mono.fromRunnable(() -> paymentService.cancelOrderEvent(orderEvent));
        }
    }

}

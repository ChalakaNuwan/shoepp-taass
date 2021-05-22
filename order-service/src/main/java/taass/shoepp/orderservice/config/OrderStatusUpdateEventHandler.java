package taass.shoepp.orderservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taass.shoepp.events.inventory.InventoryStatus;
import taass.shoepp.events.order.OrderStatus;
import taass.shoepp.events.payment.PaymentStatus;
import taass.shoepp.orderservice.entity.Order;
import taass.shoepp.orderservice.repository.OrderRepository;
import taass.shoepp.orderservice.service.OrderStatusPublisher;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class OrderStatusUpdateEventHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(final UUID id, Consumer<Order> consumer) {
        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(Order purchaseOrder) {
        if (Objects.isNull(purchaseOrder.getInventoryStatus()) || Objects.isNull(purchaseOrder.getPaymentStatus())) {
            return;
        }
        //Se il pagamento è stato eseguito con successo così come i prodotti richiesti sono stati riservati l'ordine è COMPLETED.
        boolean isComplete = PaymentStatus.RESERVED.equals(purchaseOrder.getPaymentStatus()) && InventoryStatus.RESERVED.equals(purchaseOrder.getInventoryStatus());
        var orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isComplete) {
            this.publisher.raiseOrderEvent(purchaseOrder, orderStatus);
        }
        orderRepository.save(purchaseOrder);
    }
}


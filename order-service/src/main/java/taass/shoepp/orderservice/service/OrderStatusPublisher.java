package taass.shoepp.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import taass.shoepp.dto.PurchaseOrderDto;
import taass.shoepp.dto.Triple;
import taass.shoepp.events.order.OrderEvent;
import taass.shoepp.events.order.OrderStatus;
import taass.shoepp.orderservice.entity.Order;
import taass.shoepp.orderservice.entity.OrderItem;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSink;

    public void raiseOrderEvent(final Order order, OrderStatus orderStatus){

        Set<Triple> products = new HashSet<>();
        for (OrderItem orderItem : order.getProducts()) {
            products.add(Triple.of(orderItem.getProductId(), orderItem.getQuantity(), orderItem.getSize()));
        }

        PurchaseOrderDto purchaseOrderDto = PurchaseOrderDto.of(
                order.getId(),
                products,
                order.getTotal(),
                order.getUserId()
        );

        var orderEvent = new OrderEvent(purchaseOrderDto, orderStatus);
        this.orderSink.tryEmitNext(orderEvent);
    }
}

package taass.shoepp.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.shoepp.events.order.OrderStatus;
import taass.shoepp.orderservice.entity.Order;
import taass.shoepp.orderservice.modal.OrderForm;
import taass.shoepp.orderservice.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher publisher;

    @Override
    @Transactional
    public Order createOrder(OrderForm orderForm, Long accountId) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(accountId);
        order.setProducts(orderForm.getProducts(), orderForm.getSizes());
        order.setTotal(orderForm.getTotal());
        order.setDate(new Date());
        order.setOrderStatus(OrderStatus.ORDER_CREATED);
        orderRepository.save(order);
        publisher.raiseOrderEvent(order, OrderStatus.ORDER_CREATED);
        return order;
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order getOrder(Long userId, UUID orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesnt exist"));
    }


    /*@Scheduled(cron="0 0 3 * * *", zone="Europe/Rome")
    private void orderStatusCheck() {
        List<Order> orders = orderRepository.findByOrderStatus(OrderStatus.ORDER_CREATED);
        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.ORDER_CANCELLED);
            orderRepository.save(order);
            publisher.raiseOrderEvent(order, order.getOrderStatus());
        }
    }*/

}

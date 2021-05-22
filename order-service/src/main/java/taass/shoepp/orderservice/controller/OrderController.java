package taass.shoepp.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.shoepp.orderservice.entity.Order;
import taass.shoepp.orderservice.modal.OrderForm;
import taass.shoepp.orderservice.service.OrderService;
import taass.shoepp.orderservice.utility.RestUtility;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static taass.shoepp.orderservice.utility.RestUtility.HEADER_AUTH;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestHeader(HEADER_AUTH) String tokenHeader, @Valid @RequestBody OrderForm orderForm){
        System.out.println("ORDER: " + orderForm.toString());
        Long accountId = RestUtility.getUserId(tokenHeader);
        Order order = orderService.createOrder(orderForm, accountId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrders(@RequestHeader(HEADER_AUTH) String tokenHeader){
        Long userId = RestUtility.getUserId(tokenHeader);
        List<Order> orders = orderService.getAllOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrders(@RequestHeader(HEADER_AUTH) String tokenHeader, @PathVariable UUID orderId){
        Long userId = RestUtility.getUserId(tokenHeader);
        Order order = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(order);
    }
}

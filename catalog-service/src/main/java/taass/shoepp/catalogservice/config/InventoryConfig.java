package taass.shoepp.catalogservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import taass.shoepp.catalogservice.service.InventoryService;
import taass.shoepp.catalogservice.service.InventoryServiceImpl;
import taass.shoepp.events.inventory.InventoryEvent;
import taass.shoepp.events.order.OrderEvent;
import taass.shoepp.events.order.OrderStatus;

import java.util.function.Function;

@Configuration
public class InventoryConfig {

    @Autowired
    private InventoryService inventoryService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<InventoryEvent>> inventoryProcessor() {
        return flux -> flux.flatMap(this::processInventory);
    }

    private Mono<InventoryEvent> processInventory(OrderEvent event){
        System.out.println("INVENTORY: ARRIVATO QUALCOSA -> " + event);

        if(event.getOrderStatus().equals(OrderStatus.ORDER_CREATED)){
            return Mono.fromSupplier(() -> inventoryService.newOrderInventory(event));
        }
        return Mono.fromRunnable(() -> inventoryService.cancelOrderInventory(event));
    }
}

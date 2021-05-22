package taass.shoepp.catalogservice.service;

import taass.shoepp.events.inventory.InventoryEvent;
import taass.shoepp.events.order.OrderEvent;

public interface InventoryService {

    InventoryEvent newOrderInventory(OrderEvent orderEvent);

    void cancelOrderInventory(OrderEvent orderEvent);
}

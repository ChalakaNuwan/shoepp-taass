package taass.shoepp.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taass.shoepp.catalogservice.entity.Product;
import taass.shoepp.catalogservice.entity.ProductConsumption;
import taass.shoepp.catalogservice.repository.ProductConsumptionRepository;
import taass.shoepp.catalogservice.repository.ProductRepository;
import taass.shoepp.dto.InventoryDto;
import taass.shoepp.dto.PurchaseOrderDto;
import taass.shoepp.dto.Triple;
import taass.shoepp.events.inventory.InventoryEvent;
import taass.shoepp.events.inventory.InventoryStatus;
import taass.shoepp.events.order.OrderEvent;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConsumptionRepository productConsumptionRepository;

    @Transactional
    public InventoryEvent newOrderInventory(OrderEvent orderEvent){
        InventoryDto dto = InventoryDto.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProducts());

        for (Triple entry : orderEvent.getPurchaseOrder().getProducts()) {
            Long productId = entry.getProductId();
            int quantity = entry.getQuantity();
            int size = entry.getSize();
            Optional<Product> optProduct = productRepository.findById(productId);
            if (!optProduct.isPresent() || optProduct.get().getStock() < quantity)
                return new InventoryEvent(dto, InventoryStatus.REJECTED); //Un prodotto da acquistare non c'è, rifiuto.
        }

        //Se arrivo qui tutti i prodotti che voglio acquistare esistono e sono disponibili: riservo la quantità
        orderEvent.getPurchaseOrder().getProducts().forEach( triple ->
                productConsumptionRepository.save(new ProductConsumption(orderEvent.getPurchaseOrder().getOrderId(), triple.getProductId(), triple.getQuantity())));
        return new InventoryEvent(dto, InventoryStatus.RESERVED);
    }

    @Transactional
    public void cancelOrderInventory(OrderEvent orderEvent){
       productConsumptionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(productConsumption -> {
                    productRepository.findById(productConsumption.getProductId())
                            .ifPresent(i ->
                                    i.setStock(i.getStock() + productConsumption.getQuantityConsumed()) //Libero i prodotti riservati
                            );
                    productConsumptionRepository.delete(productConsumption);
                });
    }
}

package taass.shoepp.orderservice.modal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.UUID;

public class OrderForm {

    @NotNull
    private HashMap<Long, Integer> products;  //(productId, quantity)

    @NotNull
    private HashMap<Long, Integer> sizes;  //(productId, size)

    @NotNull
    private Double total;

    public HashMap<Long, Integer> getProducts() {
        return products;
    }

    public HashMap<Long, Integer> getSizes() {
        return sizes;
    }

    public Double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "products=" + products +
                "products=" + sizes +
                ", total=" + total +
                '}';
    }

}

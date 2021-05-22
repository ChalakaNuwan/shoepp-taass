package taass.shoepp.cartservice.modal;

import javax.validation.constraints.NotNull;

public class ProductForm {
    @NotNull
    private Long productId;
    @NotNull
    private  int quantity;
    @NotNull
    private  int size;
    @NotNull
    private  int unitPrice;

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSize() {
        return size;
    }

    public int getUnitPrice() {
        return unitPrice;
    }
}

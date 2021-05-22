package taass.shoepp.paymentservice.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PaymentForm {

    @NotNull
    private String orderId;
    @NotNull
    private Double total;

    private String cardName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvc;
    private String holderName;

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public int getCvc() {
        return cvc;
    }

    public String getHolderName() {
        return holderName;
    }

    public Double getTotal() {
        return total;
    }

    public UUID getOrderId() {
        return UUID.fromString(orderId);
    }

    @Override
    public String toString() {
        return "PaymentForm{" +
                "orderId=" + orderId +
                ", total=" + total +
                ", cardName='" + cardName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expiryMonth=" + expiryMonth +
                ", expiryYear=" + expiryYear +
                ", cvc=" + cvc +
                ", holderName='" + holderName + '\'' +
                '}';
    }
}

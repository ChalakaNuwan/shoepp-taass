package taass.shoepp.paymentservice.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;

    public Payment createPayment(UUID orderId, Double total, String description) {

        Details details = new Details();
        details.setSubtotal(String.valueOf(total));
        details.setShipping("0");
        details.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(String.valueOf(total));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("PAYPAL");

        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/orderSubmitted;id="+orderId);
        redirectUrls.setReturnUrl("http://localhost:4200/orderSubmitted;id="+orderId);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment;

        try {
            createdPayment = payment.create(apiContext);
            System.out.println(">>> CREATING PAYMENT STATUS: SUCCESS!");
            return createdPayment;
        } catch (PayPalRESTException e) {
            System.out.println(">>> CREATING PAYMENT STATUS: ERROR");
        }
        return null;
    }

    public boolean executePayment(String payerId, String paymentId) {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        try {
            Payment createPayment = payment.execute(apiContext, paymentExecute);
            System.out.println(">>> STATUS: FATTO! IN PAYPAL SERVICE");
            return true;
        } catch (PayPalRESTException e) {
            System.out.println(">>> STATUS: ERROR");
            System.err.println(e.getDetails());
        }

        return false;
    }

    public Links getLink(Payment payment){

        Iterator links = payment.getLinks().iterator();
        while (links.hasNext()) {
            Links link = (Links) links.next();
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                return link;
            }
        }
        return null;

    }

}

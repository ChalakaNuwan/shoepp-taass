package taass.shoepp.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.shoepp.cartservice.entity.ShoppingCart;
import taass.shoepp.cartservice.modal.ProductForm;
import taass.shoepp.cartservice.service.ShoppingCartService;
import taass.shoepp.cartservice.utility.RestUtility;

import javax.validation.Valid;

import static taass.shoepp.cartservice.utility.RestUtility.HEADER_AUTH;

@RestController
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public ResponseEntity<ShoppingCart> getCart(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        ShoppingCart shoppingCart = shoppingCartService.getCart(accountId);
        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> deleteCart(@RequestHeader(HEADER_AUTH) String tokenHeader) {
        Long accountId = RestUtility.getUserId(tokenHeader);
        shoppingCartService.deleteCart(accountId);
        return ResponseEntity.ok("Cart deleted");
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addProduct(@RequestHeader(HEADER_AUTH) String tokenHeader, @Valid @RequestBody ProductForm productForm) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        shoppingCartService.addProduct(accountId, productForm);
        return ResponseEntity.ok("Product added");
    }

    @PostMapping("/product")
    public ResponseEntity<String> deleteProduct(@RequestHeader(HEADER_AUTH) String tokenHeader, @Valid @RequestBody ProductForm productForm) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        shoppingCartService.deleteProduct(accountId, productForm);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/cartTotal")
    public ResponseEntity<Double> getCartTotal(@RequestHeader(HEADER_AUTH) String tokenHeader) throws Exception {
        Long accountId = RestUtility.getUserId(tokenHeader);
        double cartTotal = shoppingCartService.getCartTotal(accountId);

        return ResponseEntity.ok(cartTotal);
    }

}

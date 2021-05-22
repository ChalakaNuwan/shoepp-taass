package taass.shoepp.cartservice.service;

import taass.shoepp.cartservice.entity.ShoppingCart;
import taass.shoepp.cartservice.modal.ProductForm;

public interface ShoppingCartService {

    ShoppingCart getCart(Long userId) throws Exception;

    void deleteCart(Long userId);

    void addProduct(Long userId, ProductForm productForm) throws Exception;

    void deleteProduct(Long userId, ProductForm productForm) throws Exception;

    double getCartTotal(Long userId) throws Exception;
}

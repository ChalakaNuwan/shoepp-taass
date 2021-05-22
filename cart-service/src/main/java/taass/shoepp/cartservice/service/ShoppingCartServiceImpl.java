package taass.shoepp.cartservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import taass.shoepp.cartservice.entity.CartItem;
import taass.shoepp.cartservice.entity.ShoppingCart;
import taass.shoepp.cartservice.modal.ProductForm;
import taass.shoepp.cartservice.repository.CartItemRepository;
import taass.shoepp.cartservice.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart getCart(Long userId) throws Exception {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userId);

        if (shoppingCart.isPresent()) return shoppingCart.get();
        else {
            ShoppingCart newShoppingCart = new ShoppingCart(userId);
            shoppingCartRepository.save(newShoppingCart);
            return newShoppingCart;
        }
    }

    @Override
    @Transactional
    public void deleteCart(Long userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }

    @Override
    public void addProduct(Long userId, ProductForm productForm) throws Exception {
        ShoppingCart shoppingCart = getCart(userId);

        List<CartItem> cartItemList = shoppingCart.getProducts();
        Optional<CartItem> cartItem = cartItemList.stream().parallel().filter(item ->
                item.getProductId().equals(productForm.getProductId())).findAny();

        cartItem.ifPresentOrElse(
                item -> {
                    item.setQuantity(item.getQuantity() + productForm.getQuantity());
                    cartItemRepository.save(item);
                },
                () -> {
                    CartItem newCartItem = new CartItem(productForm.getProductId(), productForm.getQuantity());
                    newCartItem.setSize(productForm.getSize());
                    newCartItem.setUnitPrice(productForm.getUnitPrice());
                    shoppingCart.addProduct(newCartItem);
                    cartItemRepository.save(newCartItem);
                });
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteProduct(Long userId, ProductForm productForm) throws Exception {
        ShoppingCart shoppingCart = getCart(userId);
        List<CartItem> cartItemList = shoppingCart.getProducts();
        CartItem cartItem = cartItemList.stream().parallel().filter(item ->
                item.getProductId().equals(productForm.getProductId())).findAny().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in cart"));

        if (cartItem.getQuantity() <= productForm.getQuantity()) {
            shoppingCart.removeCartItem(cartItem);
            shoppingCartRepository.save(shoppingCart);
            cartItemRepository.deleteById(cartItem.getId());
        } else {
            cartItem.setQuantity(cartItem.getQuantity()-productForm.getQuantity());
            cartItemRepository.save(cartItem);
            shoppingCartRepository.save(shoppingCart);
        }

    }

    @Override
    public double getCartTotal(Long userId) throws Exception {
        ShoppingCart shoppingCart = getCart(userId);
        if (shoppingCart == null || shoppingCart.getGrandTotal() == 0) return 0;
        else return shoppingCart.getGrandTotal();
    }
}

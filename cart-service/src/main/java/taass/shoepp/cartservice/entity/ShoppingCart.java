package taass.shoepp.cartservice.entity;

import taass.shoepp.cartservice.modal.ProductForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
    }

    public ShoppingCart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public void addProduct(CartItem product) {
        products.add(product);
    }

    public void clearCart() {
        products.clear();
    }

    public void removeProduct(int index) {
        products.remove(index);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getGrandTotal() {
        double cartTotal = 0;
        if (products == null) return (double) 0;
        for (CartItem item : products) {
            cartTotal+= item.getUnitPrice() * item.getQuantity();
        }
        return cartTotal;
    }

    public void removeCartItem(CartItem cartItem) {
        products.removeIf(item -> item.getProductId() == cartItem.getProductId());
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products +
                '}';
    }



}

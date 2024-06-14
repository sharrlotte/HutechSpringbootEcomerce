package vn.edu.hutech.bai2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import vn.edu.hutech.bai2.model.CartItem;
import vn.edu.hutech.bai2.model.Product;
import vn.edu.hutech.bai2.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        cartItems.add(new CartItem(product, quantity));
    }

    public void update(Long productId, int quantity) {
        cartItems.stream().filter(item -> item.getProduct().getId() == productId)
                .forEach(item -> item.setQuantity(quantity));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

    public void removeFromCart(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == (productId));
    }

    public void clearCart() {
        cartItems.clear();
    }
}

package vn.edu.hutech.bai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import vn.edu.hutech.bai2.service.CartService;
import vn.edu.hutech.bai2.service.ProductService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "/cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {

        cartService.addToCart(productId, quantity);

        if (quantity > productService.getProductById(productId).orElseThrow().getQuantity()) {
            throw new IllegalStateException("Invalid quantity: " + quantity);
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long productId, @RequestParam int quantity) {

        cartService.update(productId, quantity);

        if (quantity > productService.getProductById(productId).orElseThrow().getQuantity()) {
            throw new IllegalStateException("Invalid quantity: " + quantity);
        }

        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable int productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}

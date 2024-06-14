package vn.edu.hutech.bai2.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.hutech.bai2.model.CartItem;
import vn.edu.hutech.bai2.model.Checkout;
import vn.edu.hutech.bai2.service.CartService;
import vn.edu.hutech.bai2.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }

        model.addAttribute("checkout", new Checkout());

        return "/cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(@Validated Checkout checkout, BindingResult result) {
        List<CartItem> cartItems = cartService.getCartItems();

        if (result.hasErrors()) {
            return "/cart/checkout";
        }

        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        orderService.createOrder(checkout, cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }
}

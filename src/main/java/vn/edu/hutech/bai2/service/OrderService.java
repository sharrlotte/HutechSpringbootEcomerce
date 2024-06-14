package vn.edu.hutech.bai2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hutech.bai2.model.CartItem;
import vn.edu.hutech.bai2.model.Checkout;
import vn.edu.hutech.bai2.model.Order;
import vn.edu.hutech.bai2.model.OrderDetail;
import vn.edu.hutech.bai2.repository.OrderDetailRepository;
import vn.edu.hutech.bai2.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService

    @Transactional
    public Order createOrder(Checkout checkout, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(checkout.getCustomerName()); // Gọi phương thức setCustomerName
        order.setAddress(checkout.getAddress()); // Gọi phương thức setCustomerName
        order.setNote(checkout.getNote()); // Gọi phương thức setCustomerName
        order.setPhone(checkout.getPhone()); // Gọi phương thức setCustomerName

        // Lưu đơn hàng vào cơ sở dữ liệu
        order = orderRepository.save(order);

        // Lưu các chi tiết đơn hàng vào cơ sở dữ liệu
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Xóa giỏ hàng sau khi đặt hàng (tùy chọn)
        cartService.clearCart();

        return order;
    }
}

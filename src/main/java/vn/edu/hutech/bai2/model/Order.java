package vn.edu.hutech.bai2.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    String address;

    String phone;

    String note;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

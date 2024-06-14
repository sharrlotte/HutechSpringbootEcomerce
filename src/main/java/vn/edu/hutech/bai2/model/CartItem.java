package vn.edu.hutech.bai2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Entity
@Table(name = "CartItem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARTITEM")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_PRO") // Thêm JoinColumn để chỉ định cách ánh xạ
    private Product product;

    @Column(name = "QUANTITY", nullable = false)
    @Min(1)
    private int quantity = 1;

    // Constructors
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}

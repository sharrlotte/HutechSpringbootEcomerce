package vn.edu.hutech.bai2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cart_Detail")
public class CartDetail {

    @EmbeddedId
    private CartDetailId id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "ID_CART", referencedColumnName = "ID_CART")
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "ID_PRO", referencedColumnName = "ID_PRO")
    private Product product;

    @Column(name = "SOLD_NUM", nullable = false)
    private int soldNum;

    @Column(name = "META")
    private String meta;

    @Column(name = "`ORDER`", nullable = false)
    private int order;

    @Column(name = "LINK")
    private String link;

    @Column(name = "HIDE", nullable = false)
    private boolean hide;
}

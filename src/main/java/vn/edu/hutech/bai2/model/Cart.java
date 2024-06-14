package vn.edu.hutech.bai2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CART")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_USERS", referencedColumnName = "ID_USERS")
    private User user;

    @Column(name = "DATEBEGIN", nullable = false)
    private java.sql.Date dateBegin;

    @Column(name = "META")
    private String meta;

    @Column(name = "`ORDER`", nullable = false)
    private int order;

    @Column(name = "LINK")
    private String link;

    @Column(name = "HIDE", nullable = false)
    private boolean hide;

    @OneToMany(mappedBy = "cart")
    private Set<CartDetail> cartDetails;
}

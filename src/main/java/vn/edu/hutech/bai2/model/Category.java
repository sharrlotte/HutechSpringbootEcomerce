package vn.edu.hutech.bai2.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAT")
    private int id;

    @Column(name = "NAME_CAT", nullable = false)
    private String name;

    @Column(name = "META")
    private String meta;

    @Column(name = "`ORDER`", nullable = false)
    private int order;

    @Column(name = "LINK")
    private String link;

    @Column(name = "HIDE", nullable = false)
    private boolean hide;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}

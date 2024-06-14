package vn.edu.hutech.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hutech.bai2.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

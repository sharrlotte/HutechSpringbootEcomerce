package vn.edu.hutech.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hutech.bai2.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}

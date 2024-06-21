package vn.edu.hutech.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.hutech.bai2.model.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    
    boolean existsByUsername(String phone);
}

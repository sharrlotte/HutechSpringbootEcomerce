package vn.edu.hutech.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.hutech.bai2.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}

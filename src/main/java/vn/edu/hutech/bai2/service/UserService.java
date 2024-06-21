package vn.edu.hutech.bai2.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import vn.edu.hutech.bai2.enums.RoleEnum;
import vn.edu.hutech.bai2.model.Role;
import vn.edu.hutech.bai2.model.User;
import vn.edu.hutech.bai2.repository.IRoleRepository;
import vn.edu.hutech.bai2.repository.IUserRepository;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @PostConstruct
    public void init() {
        var roles = List.of(RoleEnum.values()).stream().map(role -> new Role().setId(role.value).setName(role.name()))
                .toList();

        roleRepository.saveAll(roles);
    }

    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        userRepository.findByUsername(username).ifPresentOrElse(
                user -> {

                    user.getRoles().add(roleRepository.findRoleById(RoleEnum.USER.value));
                    userRepository.save(user);
                },
                () -> {
                    throw new UsernameNotFoundException("User not found");
                });
    }

    // Tải thông tin chi tiết người dùng để xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public boolean existsByUsername(String phone) {
        return userRepository.existsByUsername(phone);
    }
}

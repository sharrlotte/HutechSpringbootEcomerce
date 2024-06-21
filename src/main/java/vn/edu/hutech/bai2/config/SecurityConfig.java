package vn.edu.hutech.bai2.config;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import vn.edu.hutech.bai2.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration // Đánh dấu lớp này là một lớp cấu hình cho Spring Context.
@EnableWebSecurity // Kích hoạt tính năng bảo mật web của Spring Security.
@RequiredArgsConstructor // Lombok tự động tạo constructor có tham số cho tất cả
public class SecurityConfig {
    private final UserService userService; // Tiêm UserService vào lớp cấu hình

    @Bean // Đánh dấu phương thức trả về một bean được quản lý bởi Spring Context.
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean mã hóa mật khẩu sử dụng BCrypt.
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider(); // Tạo nhà cung cấp xác thực.
        auth.setUserDetailsService(userDetailsService()); // Thiết lập dịch vụ chi
        auth.setPasswordEncoder(passwordEncoder()); // Thiết lập cơ chế mã hóa mật
        return auth; // Trả về nhà cung cấp xác thực.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http)
            throws Exception {
        return http
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Trang đăng nhập.
                        .loginProcessingUrl("/login") // URL xử lý đăng nhập.
                        .defaultSuccessUrl("/") // Trang sau đăng nhập thành công.
                        .failureUrl("/login?error") // Trang đăng nhập thất bại.
                        .permitAll())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/", "/oauth/**",
                                "/register", "/error", "/products", "/cart", "/cart/**", "/images/**", "/vendor/**",
                                "/403")
                        .permitAll() // Cho phép truy cập không cần xác thực.
                        .requestMatchers("/products/edit/**", "/products/add",
                                "/products/delete", "/categories/edit/**", "/categories/add",
                                "/categories/delete")
                        .hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN truy cập.
                        .requestMatchers("/api/**")
                        .permitAll() // API mở cho mọi người dùng.
                        .anyRequest().authenticated() // Bất kỳ yêu cầu nào khác
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // Trang chuyển hướng sau khi
                        .deleteCookies("JSESSIONID") // Xóa cookie.
                        .invalidateHttpSession(true) // Hủy phiên làm việc.
                        .clearAuthentication(true) // Xóa xác thực.
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("hutech")
                        .rememberMeCookieName("hutech")
                        .tokenValiditySeconds(24 * 60 * 60) // Thời gian nhớ đăng
                        .userDetailsService(userDetailsService()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403") // Trang báo lỗi khi truy cập
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập.
                        .expiredUrl("/login") // Trang khi phiên hết hạn.
                )
                .oauth2Login(oauth -> oauth.loginPage("/login")
                        .defaultSuccessUrl("/", true))

                .httpBasic(httpBasic -> httpBasic
                        .realmName("hutech") // Tên miền cho xác thực cơ bản.
                )
                .build(); // Xây dựng và trả về chuỗi lọc bảo mật.
    }

}

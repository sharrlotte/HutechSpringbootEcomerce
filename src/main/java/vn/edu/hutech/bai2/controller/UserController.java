package vn.edu.hutech.bai2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import vn.edu.hutech.bai2.model.Register;
import vn.edu.hutech.bai2.model.User;
import vn.edu.hutech.bai2.service.UserService;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller // Đánh dấu lớp này là một Controller trong Spring MVC.
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "users/login";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }

        model.addAttribute("user", new Register()); // Thêm một đối tượng User mới
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") Register register, // Validate
            @NotNull BindingResult bindingResult, // Kết quả của quá
            Model model) {
        if (bindingResult.hasErrors()) { // Kiểm tra nếu có lỗi validate
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }

        if (!register.getPassword().trim().equals(register.getConfirmPassword().trim())) {
            String[] errors = { "Mật khẩu phải trùng" };
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }

        if (userService.existsByUsername(register.getUsername())) {
            String[] errors = { "Username đã tồn tại" };
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }

        if (userService.existsByEmail(register.getEmail())) {
            String[] errors = { "Email đã tồn tại" };
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }

        if (userService.existsByPhone(register.getPhone())) {
            String[] errors = { "Phone đã tồn tại" };
            model.addAttribute("errors", errors);
            return "users/register"; // Trả về lại view "register" nếu có lỗi
        }

        var user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setEmail(register.getEmail());
        user.setPhone(register.getPhone());

        userService.save(user); // Lưu người dùng vào cơ sở dữ liệu
        userService.setDefaultRole(user.getUsername()); // Gán vai trò mặc định cho
        return "redirect:/login"; // Chuyển hướng người dùng tới trang "login"
    }

}

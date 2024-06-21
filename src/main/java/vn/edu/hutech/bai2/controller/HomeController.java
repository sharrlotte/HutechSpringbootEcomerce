package vn.edu.hutech.bai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "XIN CHÀO TRƯỜNG ĐẠI HỌC CÔNG NGHỆ THÀNH PHỐ HỒ CHÍ MINH!");
        return "home/home";
    }

    @GetMapping("/403")
    public String denied() {
        return "403";
    }
}

package vn.edu.hutech.bai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vn.edu.hutech.bai2.model.Student;

@RequestMapping("/students")
@Controller
public class StudentController {
    @GetMapping()
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form-student";
    }

    @PostMapping()
    public String submitForm(//
            @Valid Student student, //
            BindingResult bindingResult,
            Model model//
    ) {

        if (bindingResult.hasErrors()) {
            return "student/form-student";
        }
        model.addAttribute("message", "Sinh viên đã được thêm thành công!");
        return "student/result-student";
    }
}

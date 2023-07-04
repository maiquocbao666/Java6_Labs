package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.poly.bean.Student2;

import jakarta.validation.Valid;

@Controller
public class ValidationController {

    @GetMapping("/validation/form")
    public String form(Model model) {
        Student2 student = new Student2();
        model.addAttribute("sv", student);
        return "validation/form";
    }

    @PostMapping("/validation/form")
    public String save(@Valid @ModelAttribute("sv") Student2 student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau");
            return "validation/form";
        }
        return "validation/success";
    }

}

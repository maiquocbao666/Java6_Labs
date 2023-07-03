package com.poly.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.bean.Student;

// Bài 2
@Controller
public class HomeController {

    @RequestMapping("/home/index")
    public String index(Model model) throws Exception {
        model.addAttribute("message", "Welcome to Thymeleaf");

        ObjectMapper mapper = new ObjectMapper();
        String path = "baomqpc03196_lab2_sof306_sd17301\\src\\main\\resources\\static\\student.json";
        Student student = mapper.readValue(new File(path), Student.class);
        model.addAttribute("sv", student);

        return "home/index";
    }

}

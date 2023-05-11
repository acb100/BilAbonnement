package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Service service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String index(@RequestParam ("username") String employeeUsername, @RequestParam ("password") String employeePassword, HttpSession session) {
        if (service.getEmployee(employeeUsername, employeePassword) != null) {
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("employeeId", service.getEmployeeByUsername(employeeUsername).getEmployeeId());
            return "redirect:/dashboard";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            return "redirect:/";
        }
        return "dashboard";
    }

    @GetMapping("/createRentalContract")
    public String createRentalContract(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            return "redirect:/";
        }
        return "createRentalContract";
    }
}

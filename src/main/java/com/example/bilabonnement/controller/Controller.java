package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    //TODO refresh function for dashboard
    //TODO discuss renaming header css to main
    //TODO review damage report reference
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
            session.setAttribute("employeeId", service.getEmployeeID(employeeUsername));
            session.setAttribute("employeeUsername", service.getEmployee(employeeUsername).getEmployee_username());
            return "redirect:/dashboard";
        } else {
            return "redirect:/";
        }
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
    public String createRentalContract(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            return "redirect:/";
        }
        model.addAttribute("customers", service.getAllCustomers());
        model.addAttribute("cars", service.getAllCars());
        return "createRentalContract";
    }

    @PostMapping("/createRentalContract")
    public String createRentalContract(@ModelAttribute RentalContract rentalContract) {
        service.addRentalContract(rentalContract);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

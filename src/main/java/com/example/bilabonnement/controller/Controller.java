package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.List;

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
            session.setAttribute("employeeId", service.getEmployeeID(employeeUsername));
            session.setAttribute("employeeUsername", service.getEmployee(employeeUsername).getEmployeeUsername());
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
    public String dashboard(Model model,HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            return "redirect:/";
        }
        model.addAttribute("employeeId", session.getAttribute("employeeId"));
        return "dashboard";
    }

    @PostMapping("/createRentalContract")
    public String createRentalContract(@ModelAttribute RentalContract rentalContract) {
        service.addRentalContract(rentalContract);
        return "redirect:/dashboard";
    }

    @GetMapping("/createContractPage")
    public String createContractPage(HttpSession session){
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            return "redirect:/";
        } else {
            return "redirect:/createrentalcontract";
            return "redirect:/";
        }
    }
    @GetMapping("/viewRentalContracts/{employeeId}")
    public String viewRentalContractsByEmployeeId(@PathVariable("employeeId") int employeeId, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<RentalContract> contractList = service.fetchAllRentalContractsForEmployee(employeeId);
        String id = (String) session.getAttribute("employeeId");
        model.addAttribute("contractlist", contractList);
        model.addAttribute("employeeId", id);
        return "contractoverviewpage";
    }
    @GetMapping("/deleteRentalContract/{contractId}")
    public String deleteRentalContract(@PathVariable("contractId") int contractId){
        boolean deleted = service.deleteRentalContract(contractId);
        if(deleted){
            return "redirect:/contractoverviewpage";
        } else
            return "redirect:/contractoverviewpage";

    }
    //TODO bliver ikke brugt lige nu, men kan bruges til at finde en enkelt contract gennem ID
    @GetMapping("/viewRentalContract/{contractId}")
    public String viewRentalContract(@PathVariable("contractId") int contractId, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        model.addAttribute("contract", service.findRentalContractById(contractId));
        return "/viewcontractpage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

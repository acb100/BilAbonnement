package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.List;

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

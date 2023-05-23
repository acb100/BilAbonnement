package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    //TODO refresh function for dashboard
    //TODO discuss renaming header css to main
    //TODO review damage report reference
    //TODO ADM user
    @Autowired
    Service service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/myProfile")
    public String myProfile(){
        return "myProfile";
    }

    @PostMapping("/")
    public String index(@RequestParam("username") String employeeUsername, @RequestParam("password") String employeePassword, HttpSession session, Model model) {
        if (service.employeeExists(employeeUsername, employeePassword)) {
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("employeeId", service.getEmployeeID(employeeUsername));
            session.setAttribute("employeeUsername", service.getEmployee(employeeUsername).getEmployee_username());
            session.setAttribute("employeeTypeId", service.getEmployee(employeeUsername).getEmployee_type_id());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("loginError", true);
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) throws JsonProcessingException {
        List<CarWithCount> allCars = service.getAllCarModels();
        ObjectMapper objectMapper = new ObjectMapper();
        String allCarsJson = objectMapper.writeValueAsString(allCars);
        model.addAttribute("usedCars", service.getAllUsedCars());
        model.addAttribute("unusedCars", service.getAllUnusedCars());
        model.addAttribute("usedCarRows", service.getAllUsedCarRows());
        model.addAttribute("unusedCarRows", service.getAllUnusedCarRows());
        try {
            if (session.getAttribute("employeeTypeId").equals(3)) {
                model.addAttribute("carSum", service.fetchSumOfLeasedCars());
            }

        } catch (NullPointerException ignored){

        }
        model.addAttribute("allCars", allCarsJson);
        return loginCheck("dashboard");
    }

    @GetMapping("/createRentalContract")
    public String createRentalContract(Model model) {
        model.addAttribute("customers", service.getAllCustomers());
        model.addAttribute("cars", service.getAllCars());
        return loginCheck("createRentalContract");
    }

    @PostMapping("/createRentalContract")
    public String createRentalContract(@ModelAttribute RentalContract rentalContract) {
        service.addRentalContract(rentalContract);
        return "redirect:/dashboard";
    }

    @GetMapping("/viewRentalContracts")
    public String viewRentalContracts(Model model) {
        model.addAttribute("contractList", service.getAllRentalContracts());
        return loginCheck("contractOverview");

    }

    @GetMapping("/viewRentalContracts/{employeeId}")
    public String viewRentalContractsByEmployeeId(@PathVariable("employeeId") int employeeId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<RentalContract> contractList = service.fetchAllRentalContractsForEmployee(employeeId);
        String id = (String) session.getAttribute("employeeId");
        model.addAttribute("contractlist", contractList);
        model.addAttribute("employeeId", id);
        return "contractOverview";
    }

    @GetMapping("/deleteRentalContract/{contractId}")
    public String deleteRentalContract(@PathVariable("contractId") int contractId) {
        boolean deleted = service.deleteRentalContract(contractId);
        if (deleted) {
            return "redirect:/viewRentalContracts";
        } else {
            return "redirect:/viewRentalContracts";
        }
    }

    //TODO bliver ikke brugt lige nu, men kan bruges til at finde en enkelt contract gennem ID
    @GetMapping("/viewRentalContract/{contractId}")
    public String viewRentalContract(@PathVariable("contractId") int contractId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        model.addAttribute("contract", service.findRentalContractById(contractId));
        return "viewContractPage";
    }

    @GetMapping("/createDamageReport")
    public String createDamageReport(Model model) {
        model.addAttribute("contractList", service.getAllRentalContracts());
        model.addAttribute("damageTypeList", service.getAllDamageTypes());
        return loginCheck("createDamageReport");
    }

    @PostMapping("/createDamageReport")
    public String createDamageReport(@ModelAttribute DamageReport damageReport,
                                     @RequestParam(name = "rental_contract_id") int rentalContractId){
        System.out.println(Arrays.toString(damageReport.getDamage_type_ids()));
        service.updateDamageReport(damageReport, rentalContractId);
        return loginCheck("dashboard");
    }

    /**
     * Method for checking if user is logged in. used in the return statement instead of just inputting String;
     *
     * @param mapping is the string that points to the html document
     */
    private String loginCheck(String mapping) {
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
            Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
            if (isLoggedIn == null || !isLoggedIn) {
                return "redirect:/";
            }
            return mapping;
        } catch (NullPointerException e) {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/carOverview")
    public String fetchAllCars(Model model){
        model.addAttribute("carList", service.fetchAllCars());
        return loginCheck("carOverviewPage");

    }


    @GetMapping("/search")
    public String search(@RequestParam("search_filter") String searchFilter, @RequestParam("search_value") String keyword, Model model) {
        //List<SearchResult> results = service.searchForKeyword(searchFilter, keyword);
        //model.addAttribute("cars", carList);
        return "searchResults";
    }
    /*@GetMapping("/leasedCarSum")
    public String sumOfLeasedCars(Model model){
        model.addAttribute("carSum", service.fetchSumOfLeasedCars());
        return loginCheck("dashboard");
    }*/
}

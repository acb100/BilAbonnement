package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

        model.addAttribute("allCurrentDayCars", service.fetchAllCurrentDayCars());
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
        model.addAttribute("cars", service.getAllUnusedCars());
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

    @GetMapping("/deleteRentalContract/{contractId}")
    public String deleteRentalContract(@PathVariable("contractId") int contractId) {
        boolean deleted = service.deleteRentalContract(contractId);
        return "redirect:/viewRentalContracts";
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
        model.addAttribute("carList", service.orderCarsByOngoing());
        return loginCheck("carOverviewPage");
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@ModelAttribute Car car) {
        System.out.println("Vin_nr: " + car.getVin_nr());
        System.out.println("Equipment: " + car.getEquipment_level());
        System.out.println("base_price: " + car.getBase_price());
        System.out.println("vat: " + car.getVat());
        System.out.println("emission: " + car.getEmission());
        System.out.println("model_id: " + car.getModel_id());

            service.addCar(car);
            return "redirect:/carOverview";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") int car_id) {
        service.deleteCar(car_id);
        return "redirect:/carOverview";
    }

    @GetMapping("/search")
    public String search(@RequestParam("search_filter") String searchFilter,
                         @RequestParam("search_value") String keyword, Model model ) {
        if (keyword.isBlank()){
            keyword  = "   ";
            model.addAttribute("search_error", true);
        }
        SearchResult results = service.searchForKeyword(searchFilter, keyword);
        model.addAttribute("results", results);
        return loginCheck("searchResults");
    }

    @GetMapping("/leasedCarSum")
    public String sumOfLeasedCars(Model model){
        model.addAttribute("carSum", service.fetchSumOfLeasedCars());
        return loginCheck("dashboard");
    }
    @GetMapping("/createAdvanceAgreement")
    public String createAdvanceAgreement(Model model){
        model.addAttribute("cars", service.getAllCars());
        model.addAttribute("buyers", service.getAllBuyers());
        return loginCheck("createAdvanceAgreement");
    }
    @PostMapping("/createAdvanceAgreement")
    public String createAdvanceAgreement(@ModelAttribute AdvanceAgreement advanceAgreement){
        service.addAdvanceAgreement(advanceAgreement);
        return loginCheck("createAdvanceAgreement");
    }
    @GetMapping("/viewAdvanceAgreements")
    public String viewAdvanceAgreements(Model model){
        model.addAttribute("cars", service.getAllCars());
        model.addAttribute("advanceAgreements", service.getAllAdvanceAgreements());
        model.addAttribute("buyers", service.getAllBuyers());
        return loginCheck("viewAdvanceAgreements");
    }
    @GetMapping("/deleteAdvanceAgreement/{agreementId}")
    public String deleteAdvanceAgreement(@PathVariable("agreementId") int agreementId){
        boolean deleted = service.deleteAdvanceAgreement(agreementId);
        return loginCheck("viewAdvanceAgreements");
    }
}

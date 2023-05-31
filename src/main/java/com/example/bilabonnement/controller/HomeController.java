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
    //TODO ADM user
    @Autowired
    Service service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/myProfile")
    public String myProfile() {
        return loginCheck("myProfile");
    }

    /**
     * method for logging in user and set the users employee attributes
     * @param employeeUsername the username of the employee trying to log in
     * @param employeePassword the password of the employee trying to log in
     * @param session variable name of the HttpSession
     * @param model a model of the fetched object
     * @return returns either dashboard for correct login or index for failed login
     */
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

    /**
     * method for showing the dashboard with attributes for the KPI's
     * @param model a model of the fetched object
     * @param session variable name of the HttpSession
     * @return returns the logincheck and the dashboard HTML template
     * @throws JsonProcessingException javaScript exception thrower
     */
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
                model.addAttribute("showCarSum", true);
                model.addAttribute("carSum", service.fetchSumOfLeasedCars());
            }

        } catch (NullPointerException ignored) {

        }
        model.addAttribute("allCars", allCarsJson);
        return loginCheck("dashboard");
    }

    /**
     * models the attributes customers and cars, which makes thymeleaf able to parse the objects
     * @param model a model of the fetched object
     * @return returns login check and the createRentalContract HTML template
     */
    @GetMapping("/createRentalContract")
    public String createRentalContract(Model model) {
        model.addAttribute("customers", service.getAllCustomers());
        model.addAttribute("cars", service.getAllUnusedCars());
        return loginCheck("createRentalContract");
    }

    /**
     * creates a rental contract by pulling from service
     */

    @PostMapping("/createRentalContract")
    public String createRentalContract(@ModelAttribute RentalContract rentalContract) {
        service.addRentalContract(rentalContract);
        return "redirect:/dashboard";
    }

    /**
     * models all the rental contracts in thymeleaf and checks for login
     */

    @GetMapping("/viewRentalContracts")
    public String viewRentalContracts(Model model) {
        model.addAttribute("contractList", service.getAllRentalContracts());
        return loginCheck("contractOverview");

    }

    /**
     * deletes a rental contract by passing rental contract id
     */

    @GetMapping("/deleteRentalContract/{contractId}")
    public String deleteRentalContract(@PathVariable("contractId") int contractId) {
        service.deleteRentalContract(contractId);
        return "redirect:/viewRentalContracts";
    }

    /**
     * creates models for the damage reports for thymeleaf to show the objects
     *
     * @param model is a model of the passed object
     * @return returns the logincheck and createDamageReport HTML template
     */
    @GetMapping("/createDamageReport")
    public String createDamageReport(Model model) {
        model.addAttribute("contractList", service.getAllRentalContracts());
        model.addAttribute("damageTypeList", service.getAllDamageTypes());
        return loginCheck("createDamageReport");
    }

    /**
     *
     * @param damageReport damageReport object with passed attributes
     * @param rentalContractId the passed paramenter for the rental contract id
     * @return returns the logincheck and dashboard
     */
    @PostMapping("/createDamageReport")
    public String createDamageReport(@ModelAttribute DamageReport damageReport,
                                     @RequestParam(name = "rental_contract_id") int rentalContractId) {
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

    /**
     *
     * @param session variable name of the HttpSession
     * @return returns root page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * method for fetching all cars and modeling them in the view
     * @param model the model of the car list
     * @return retunrs logincheck method and carOverviewPage
     */
    @GetMapping("/carOverview")
    public String fetchAllCars(Model model) {
        model.addAttribute("carList", service.orderCarsByOngoing());
        return loginCheck("carOverviewPage");
    }

    /**
     * method for updating the status of a car
     * @param car the modeled car object
     * @return returns the carOverview HTML template
     */
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

    /**
     * method for deleting a car
     * @param car_id the requested car id parameter
     * @return returns the carOverview HTML template
     */
    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") int car_id) {
        service.deleteCar(car_id);
        return "redirect:/carOverview";
    }

    /**
     * Search method
     * @param searchFilter the requested search filter paramenter, what to search for in the database
     * @param keyword the passed keyword from the form e.g. "toyota prius"
     * @param model a model of the fetched object
     * @return returns the logincheck and searchResults HTML template
     */
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

    /**
     * Method for fetching the sum of all currently leased cars
     * @param model a model of the fetched car objects
     * @return returns the logincheck method and the dashboard
     */
    @GetMapping("/leasedCarSum")
    public String sumOfLeasedCars(Model model){
        model.addAttribute("carSum", service.fetchSumOfLeasedCars());
        return loginCheck("dashboard");
    }

    /**
     * method for creating an advance agreement
     * @param model the model fetches all cars and buyers from the database and makes the displayable with thymeleaf
     *             using the attribute names
     * @return returns the logincheck method with createAdvanceAgreement as the mapping for the HTML template
     */
    @GetMapping("/createAdvanceAgreement")
    public String createAdvanceAgreement(Model model){
        model.addAttribute("cars", service.getAllCars());
        model.addAttribute("buyers", service.getAllBuyers());
        return loginCheck("createAdvanceAgreement");
    }

    /**
     * method for adding the advance agreement to the database
     * @param advanceAgreement the model of the advanceAgreement object to be filled with attributes
     * @return returns you to the dashboard
     */
    @PostMapping("/createAdvanceAgreement")
    public String createAdvanceAgreement(@ModelAttribute AdvanceAgreement advanceAgreement){
        service.addAdvanceAgreement(advanceAgreement);
        return "redirect:/dashboard";
    }

    /**
     * method for viewing all advance agreements
     * @param model models the attributename advanceAgreements for thymeleaf to be able to access service.getAllAdvanceAgreements
     * @return returns the logincheck and viewAdvanceAgreements HTML template
     */
    @GetMapping("/viewAdvanceAgreements")
    public String viewAdvanceAgreements(Model model){
        model.addAttribute("advanceAgreements", service.getAllAdvanceAgreements());
        return loginCheck("viewAdvanceAgreements");
    }

    /**
     * method for deleting and advance agreement by ID
     * @param agreementId the variable passed for the advance agreement ID
     * @return returns the logincheck and viewAdvanceAgreement HTML template
     */
    @GetMapping("/deleteAdvanceAgreement/{agreementId}")
    public String deleteAdvanceAgreement(@PathVariable("agreementId") int agreementId){
        boolean deleted = service.deleteAdvanceAgreement(agreementId);
        return loginCheck("viewAdvanceAgreements");
    }
}

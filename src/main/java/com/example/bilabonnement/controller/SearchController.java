package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.repository.SearchRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.util.List;

@Controller
public class SearchController {

    SearchRepo searchRepo;

    @GetMapping("/search")
    public String searchForCar(@RequestParam("keyword") String keyword, Model model) {
        List<Car> carList = searchRepo.findCarByKeyword(keyword);
        model.addAttribute("cars", carList);
        return "dashboard";
    }
}

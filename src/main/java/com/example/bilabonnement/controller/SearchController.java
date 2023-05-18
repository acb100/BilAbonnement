package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        String sql = "SELECT * FROM brand WHERE brand_name LIKE ?";
        String keywordParam = "%" + keyword + "%";
        List<Car> searchResults = jdbcTemplate.query(sql, new Object[]{keywordParam}, new BeanPropertyRowMapper<>(Car.class));
        model.addAttribute("results", searchResults);
        return "dashboard";
    }
}

package com.example.bilabonnement.controller;

import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Service service;

    @GetMapping("/")
    public String index() {
        return "index";
    }
}

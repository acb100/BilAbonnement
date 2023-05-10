package com.example.bilabonnement.controller;

import com.example.bilabonnement.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Service service;
}

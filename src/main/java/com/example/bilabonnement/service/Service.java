package com.example.bilabonnement.service;


import com.example.bilabonnement.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    Repository repo;
}

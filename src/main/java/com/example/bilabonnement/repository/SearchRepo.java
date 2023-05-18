package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepo {

    @Autowired
    JdbcTemplate template;
    public List<Car> findCarByKeyword(String keyword) {
        String sql = "SELECT * FROM car WHERE";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }
}

package com.study.data_filter.controller;

import com.study.data_filter.model.User;
import com.study.data_filter.service.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @GetMapping("/userAll")
    public List<User> getUserList() {
        return sqlService.getUserList();
    }
}

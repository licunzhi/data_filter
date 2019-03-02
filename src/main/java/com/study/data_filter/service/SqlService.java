package com.study.data_filter.service;

import com.study.data_filter.dao.SqlDao;
import com.study.data_filter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlService {

    @Autowired
    private SqlDao sqlDao;

    public List<User> getUserList() {
        return sqlDao.getUserList();
    }
}

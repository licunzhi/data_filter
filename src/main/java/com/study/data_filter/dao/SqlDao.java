package com.study.data_filter.dao;

import com.study.data_filter.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SqlDao {

    @Select(value = "select * from user")
    List<User> getUserList();
}

package com.study.data_filter.dao;

import com.study.data_filter.model.User;
import com.study.data_filter.model.UserMovieScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SqlDao {

    @Select(value = "select * from user")
    List<User> getUserList();

    @Select(value = "select * from base_u1")
    List<UserMovieScore> getBaseList();

    @Select(value = "select * from test_u1")
    List<UserMovieScore> getTestList();
}

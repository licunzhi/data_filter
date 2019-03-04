package com.study.data_filter;

import com.study.data_filter.service.SqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataFilterApplicationTests {

    @Autowired
    private SqlService sqlService;

    @Test
    public void app() {
        sqlService.calculateSimMatrix();
    }

}

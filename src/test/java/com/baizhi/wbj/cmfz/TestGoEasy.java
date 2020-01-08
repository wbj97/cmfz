package com.baizhi.wbj.cmfz;

import com.baizhi.wbj.dao.UserDao;
import com.baizhi.wbj.entity.MapDto;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoEasy {
    @Autowired
    UserDao userDao;
    @Test
    public void test(){
        GoEasy goEasy = new GoEasy( "http://rest_hangzhou.goeasy.io", "BC-26723bbc45c047538396794c5d42c167");
        goEasy.publish("wbj", "Hello, GoEasy!");
    }

    @Test
    public void testMapDto(){
        List<MapDto> mapDtos = userDao.queryBySexGetLocation("0");
        System.out.println("mapDtos = " + mapDtos);
    }

}

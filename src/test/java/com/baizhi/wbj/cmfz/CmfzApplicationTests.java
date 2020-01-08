package com.baizhi.wbj.cmfz;

import com.baizhi.wbj.dao.AdminDao;
import com.baizhi.wbj.dao.BannerDao;
import com.baizhi.wbj.entity.Admin;
import com.baizhi.wbj.entity.Banner;
import com.baizhi.wbj.service.AdminService;
import com.baizhi.wbj.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    AdminDao adminDao;

    @Autowired
    BannerDao bannerDao;

    @Autowired
    BannerService bannerService;

    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        System.out.println("admins = " + admins);
    }

    @Test
    public void remove(){
        Banner banner = new Banner();
        banner.setId("1");
        Banner banner1 = bannerDao.selectByPrimaryKey(banner);

    }

}

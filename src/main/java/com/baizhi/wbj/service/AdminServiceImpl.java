package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.AdminDao;
import com.baizhi.wbj.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Autowired
    HttpSession session;
    @Override
    public Map login(String yzm,String username,String password) {
        String code = session.getAttribute("code").toString();
        System.out.println("yzm :"+yzm);
        System.out.println("code :"+code);
        HashMap hashMap = new HashMap();
        if(!yzm.equals(code)){
            hashMap.put("status",500);
            hashMap.put("msg","验证码错误");
        }else{
            Admin admin = new Admin();
            admin.setUsername(username);
            Admin admin1 = adminDao.selectOne(admin);
            if(admin1==null){
                hashMap.put("status",500);
                hashMap.put("msg","用户名不存在");
            }else if(!admin1.getPassword().equals(password)){
                hashMap.put("status",500);
                hashMap.put("msg","密码错误");
            }else{
                session.setAttribute("currentAdmin",admin1);
                hashMap.put("status",200);
            }
        }
        return hashMap;
    }
}

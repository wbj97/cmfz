package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface AdminService {
    public Map login(String yzm,String username,String password);
}

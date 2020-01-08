package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.MapDto;
import com.baizhi.wbj.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public void insertUser(User user);

    public void removeUser(String[] ids);

    public void modifyUser(User user);

    public Map queryByPage(int currentPage,int pageSize);

    public Integer queryBySexAndDay(String sex,Integer day);

    public List<MapDto> queryBySexGetLocation(String sex);

    public Map queryByPhone(String phone,String password);

    //查一个
    public User selectOne(User user);
    //随机差五个
    public List<User> selectFive();
}


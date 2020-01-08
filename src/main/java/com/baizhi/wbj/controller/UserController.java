package com.baizhi.wbj.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.wbj.util.*;
import com.baizhi.wbj.entity.User;
import com.baizhi.wbj.service.UserService;
import com.baizhi.wbj.util.HttpUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController{
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("queryByPage")
    public Map queryByPage(int page,int rows){
        Map map = userService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("editUser")
    public Map editUser(String[] id,User user,  HttpServletRequest request, String oper){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String userId = UUID.randomUUID().toString();
            user.setId(userId);
            hashMap.put("status",200);
            hashMap.put("userId",userId);
            userService.insertUser(user);
        }else if(oper.equals("edit")){
            user.setPhoto(null);
            hashMap.put("userId",user.getId());
            userService.modifyUser(user);
        }else{
            userService.removeUser(id);
        }
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-26723bbc45c047538396794c5d42c167");
        Map map = queryBySexAndDay();
        String s = JSONObject.toJSONString(map);
        goEasy.publish("cmfz", s);
        return hashMap;
    }

    @RequestMapping("/upload")
    public Map upload(MultipartFile photo, String userId, HttpServletRequest request){
//        调用工具类获取上传图片 并返回uri
        String uri = HttpUtil.getHttp(photo, request,"/upload/userImg/");
        User user = new User();
        user.setId(userId);
        user.setPhoto(uri);
        userService.modifyUser(user);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    @RequestMapping("queryBySexAndDay")
    public Map queryBySexAndDay(){
            HashMap hashMap = new HashMap();
            ArrayList manList = new ArrayList();
            manList.add(userService.queryBySexAndDay("0",1));
            manList.add(userService.queryBySexAndDay("0",7));
            manList.add(userService.queryBySexAndDay("0",30));
            manList.add(userService.queryBySexAndDay("0",365));
            ArrayList womenList = new ArrayList();
            womenList.add(userService.queryBySexAndDay("1",1));
            womenList.add(userService.queryBySexAndDay("1",7));
            womenList.add(userService.queryBySexAndDay("1",30));
            womenList.add(userService.queryBySexAndDay("1",365));
            hashMap.put("man",manList);
            hashMap.put("women",womenList);
            return hashMap;
    }
    @RequestMapping("queryBySexGetLocation")
    public Map queryBySexGetLocation(){
        HashMap hashMap = new HashMap();
        List manList = userService.queryBySexGetLocation("0");
        List womenList = userService.queryBySexGetLocation("1");
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }

    @GetMapping("login")
    public Map login(String phone,String password){
        Map map = userService.queryByPhone(phone,password);
        return map;
    }

    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        HashMap hashMap = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            String substring = s.substring(0, 5);
            // SmsUtil.send(phone,substring);
            // 将验证码保存至Redis  Key phone_186XXXX Value code 1分钟有效
            stringRedisTemplate.opsForValue().set("phone_"+phone,substring,60, TimeUnit.SECONDS);

            hashMap.put("status","200");
            hashMap.put("message","短信发送成功");
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","短信发送失败");
        }
        return hashMap;
    }
    //3.注册接口
    //3.注册接口
    @RequestMapping("/regist")
    public Map regist(String code,String phone){
        HashMap hashMap = new HashMap();
        //从redis中获取验证码
        String s = stringRedisTemplate.opsForValue().get("phone_" + phone);
        if(s==null){
            hashMap.put("status","-200");
            hashMap.put("message","验证码失效");
        }else if(code.equals(s)){
            hashMap.put("status","200");
            hashMap.put("message","注册成功");
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","注册失败");
        }

        return hashMap;
    }
    //4.补充个人信息接口
    @RequestMapping("/userMsg")
    public Map userMsg(User user){
        HashMap hashMap = new HashMap();
        try {
            userService.insertUser(user);
            hashMap.put("status","200");
            hashMap.put("user",user);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }
    //15.修改个人信息
    @RequestMapping("/updateUser")
    public Map updateUser(User user){
        userService.modifyUser(user);
        User user1 = new User();
        user1.setId(user.getId());
        User user2 = userService.selectOne(user1);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("user",user2);
        return hashMap;
    }
    //16.金刚道友
    @RequestMapping("/selectFive")
    public Map selectFive(String uid){
        List<User> users = userService.selectFive();
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("list",users);
        return hashMap;
    }
    //18.添加关注上师
    @RequestMapping("/focusGuru")
    public Map focusGuru(String uid,String id){
        stringRedisTemplate.opsForList().leftPush(uid,id);

        List<String> range = stringRedisTemplate.opsForList().range(uid, 0, -1);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","添加成功");
        hashMap.put("list",range);
        return hashMap;
    }

}

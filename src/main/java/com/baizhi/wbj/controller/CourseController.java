package com.baizhi.wbj.controller;


import com.baizhi.wbj.entity.Course;
import com.baizhi.wbj.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    //8.展示功课
    @RequestMapping("/queryByUid")
    public Map selectByUid(String uid){
        List<Course> courses = courseService.queryByUid(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //9.添加功课
    @RequestMapping("/insertCourse")
    public Map insertCourse(String uid,String title){
        courseService.insertCourse(uid, title);
        List<Course> courses = courseService.queryByUid(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //10.删除功课
    @RequestMapping("/deleteCourse")
    public Map deleteCourse(String uid,String id){
        courseService.deleteCourse(uid, id);
        List<Course> courses = courseService.queryByUid(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
}

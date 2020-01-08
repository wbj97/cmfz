package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> queryAll();

    public List<Course> queryByUid(String uid);

    public void insertCourse(String uid,String title);

    public void deleteCourse(String uid,String id);
}

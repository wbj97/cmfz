package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.CourseDao;
import com.baizhi.wbj.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Course> queryAll() {
        List<Course> courses = courseDao.selectAll();
        return courses;
    }

    @Override
    public List<Course> queryByUid(String uid) {
        Course course = new Course();
        course.setUserId(uid);
        List<Course> courses = courseDao.select(course);
        return courses;
    }

    @Override
    public void insertCourse(String uid,String title) {
        Course course = new Course();
        String id = UUID.randomUUID().toString();
        course.setId(id);
        course.setUserId(uid);
        course.setTitle(title);
        course.setCreateDate(new Date());
        course.setType("木鱼");
        courseDao.insert(course);
    }

    @Override
    public void deleteCourse(String uid, String id) {
        Course course = new Course();
        course.setId(id);
        course.setUserId(uid);
        courseDao.delete(course);
    }
}

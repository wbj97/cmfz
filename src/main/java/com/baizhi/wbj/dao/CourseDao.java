package com.baizhi.wbj.dao;

import com.baizhi.wbj.entity.Course;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends Mapper<Course>, DeleteByIdListMapper<Course,String>, InsertListMapper<Course> {
}

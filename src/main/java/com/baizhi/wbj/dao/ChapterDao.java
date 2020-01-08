package com.baizhi.wbj.dao;

import com.baizhi.wbj.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

public interface ChapterDao extends Mapper<Chapter>, DeleteByIdListMapper<Chapter,String>, InsertListMapper<Chapter> {

}

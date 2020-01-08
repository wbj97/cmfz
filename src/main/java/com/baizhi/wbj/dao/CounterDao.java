package com.baizhi.wbj.dao;

import com.baizhi.wbj.entity.Counter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter>, DeleteByIdListMapper<Counter,String>, InsertListMapper<Counter> {
}

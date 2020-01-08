package com.baizhi.wbj.dao;

import com.baizhi.wbj.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String>, InsertListMapper<Article> {
}

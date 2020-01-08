package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public void insertArticle(Article article);

    public void removeArticle(String[] id);

    public void modifyArticle(Article article);

    public Map queryByPage(int currentPage,int pageSize);

    public List<Article> queryAll();

    public Article queryOne(String id);
}

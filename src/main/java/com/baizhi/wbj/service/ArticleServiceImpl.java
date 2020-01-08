package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.ArticleDao;
import com.baizhi.wbj.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Override
    public void insertArticle(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void removeArticle(String[] ids) {
        articleDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    public void modifyArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(int currentPage, int pageSize) {
        HashMap hashMap = new HashMap();
        int count = articleDao.selectCount(null);
        hashMap.put("page",currentPage);
        hashMap.put("records",count);
        hashMap.put("total",count%pageSize==0?count/pageSize:count/pageSize+1);
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((currentPage - 1) * pageSize, pageSize));
        hashMap.put("rows",articles);
        return hashMap;
    }

    @Override
    public List<Article> queryAll() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }

    @Override
    public Article queryOne(String id) {
        Article article = new Article();
        article.setId(id);
        Article article1 = articleDao.selectOne(article);
        return article1;
    }
}

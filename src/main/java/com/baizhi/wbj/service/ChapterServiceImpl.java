package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.ChapterDao;
import com.baizhi.wbj.entity.Chapter;
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
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Override
    public void insertChapter(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void removeChapter(String[] ids) {
        chapterDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    public void modifyChapter(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(Integer CurrentPage, Integer pageSize) {
        HashMap hashMap = new HashMap();
        int count = chapterDao.selectCount(null);
        hashMap.put("page",CurrentPage);
        hashMap.put("records",count);
        int total = count%pageSize==0?count/pageSize:count/pageSize+1;
        hashMap.put("total",total);
        List<Chapter> rows = chapterDao.selectByRowBounds(null, new RowBounds((CurrentPage - 1) * pageSize, pageSize));
        hashMap.put("rows",rows);
        return hashMap;
    }

    @Override
    public List<Chapter> selectByAlbumId(String album_id) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(album_id);
        List<Chapter> chapters = chapterDao.select(chapter);
        return chapters;
    }
}

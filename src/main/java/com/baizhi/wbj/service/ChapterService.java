package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    public void insertChapter(Chapter chapter);

    public void removeChapter(String[] ids);

    public void modifyChapter(Chapter chapter);

    public Map queryByPage(Integer CurrentPage,Integer pageSize);

    public List<Chapter> selectByAlbumId(String album_id);
}

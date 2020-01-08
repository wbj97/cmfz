package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService  {
    public void insertAlbum(Album album);

    public void RemoveAlbum(String[] id);

    public void modifyAlbum(Album album);

    public Map queryByPage(int currentPage,int pageSize);

    public List<Album> queryAll();

    public List<Album> selectByRowBounds();

    public Album queryOne(String id);
}

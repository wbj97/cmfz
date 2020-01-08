package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.AlbumDao;
import com.baizhi.wbj.entity.Album;
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
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override
    public void insertAlbum(Album album) {
        albumDao.insertSelective(album);
    }

    @Override
    public void RemoveAlbum(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void modifyAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(int currentPage, int pageSize) {
        int records = albumDao.selectCount(null);
        int total = records%pageSize==0?records/pageSize:records/pageSize+1;
        List<Album> rows = albumDao.selectByRowBounds(null,new RowBounds((currentPage-1)*pageSize,pageSize));
        HashMap hashMap = new HashMap();
        hashMap.put("page",currentPage);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("rows",rows);
        return hashMap;
    }

    @Override
    public List<Album> queryAll() {
        List<Album> list = albumDao.selectAll();
        return list;
    }

    @Override
    public List<Album> selectByRowBounds() {
        List<Album> list = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
        return list;
    }

    @Override
    public Album queryOne(String id) {
        Album album = new Album();
        album.setId(id);
        Album album1 = albumDao.selectOne(album);
        return album1;
    }


}

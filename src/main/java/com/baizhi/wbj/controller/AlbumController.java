package com.baizhi.wbj.controller;

import com.baizhi.wbj.dao.AlbumDao;
import com.baizhi.wbj.entity.Album;
import com.baizhi.wbj.entity.Chapter;
import com.baizhi.wbj.service.AlbumService;
import com.baizhi.wbj.service.ChapterService;
import com.baizhi.wbj.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        Map map = albumService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("/editAlbum")
    public Map editAlbum(String oper, Album album,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String albumId = UUID.randomUUID().toString();
            hashMap.put("albumId",albumId);
            hashMap.put("status",200);
            album.setId(albumId);
            album.setCount(0);
            albumService.insertAlbum(album);
        }else if(oper.equals("edit")){
            hashMap.put("albumId",album.getId());
            hashMap.put("status",200);
            albumService.modifyAlbum(album);
        }else{
            albumService.RemoveAlbum(id);
        }
        return hashMap;
    }


    @RequestMapping("uploadAlbum")
    public Map upload(MultipartFile cover, HttpServletRequest request,String albumId){
        HashMap hashMap = new HashMap();
        String http = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        Album album = new Album();
        album.setId(albumId);
        album.setCover(http);
        albumService.modifyAlbum(album);
        hashMap.put("status",200);
        return hashMap;
    }
    //7.专辑详情接口
    @RequestMapping("/albumMsg")
    public Map albumMsg(String uid,String id){
        HashMap hashMap = new HashMap();
        Album album = albumService.queryOne(id);
        List<Chapter> chapters = chapterService.selectByAlbumId(album.getId());
        hashMap.put("status","200");
        hashMap.put("album",album);
        hashMap.put("list",chapters);
        return hashMap;
    }
}

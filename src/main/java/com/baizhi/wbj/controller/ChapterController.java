package com.baizhi.wbj.controller;

import com.baizhi.wbj.entity.Album;
import com.baizhi.wbj.entity.Chapter;
import com.baizhi.wbj.service.ChapterService;
import com.baizhi.wbj.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @GetMapping("/queryByPage")
    public Map queryByPage(int page,int rows){
        Map map = chapterService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("/editChapter")
    public Map edit(String oper, Chapter chapter, String[] id,String albumId){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String chapterId = UUID.randomUUID().toString();
            hashMap.put("chapterId",chapterId);
            hashMap.put("status",200);
            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapterService.insertChapter(chapter);
        }else if(oper.equals("edit")){
            hashMap.put("chapterId",chapter.getId());
            hashMap.put("status",200);
            chapterService.modifyChapter(chapter);
        }else{
            chapterService.removeChapter(id);
        }
        return hashMap;
    }


    @RequestMapping("uploadChapter")
    public Map upload(MultipartFile url, HttpServletRequest request, String chapterId) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap = new HashMap();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music/");
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length-1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        chapterService.modifyChapter(chapter);
        hashMap.put("status",200);
        return hashMap;
    }

    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        // FileUtils.copyFile();
    }
}

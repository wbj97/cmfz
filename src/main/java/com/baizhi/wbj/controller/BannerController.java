package com.baizhi.wbj.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.wbj.entity.Banner;
import com.baizhi.wbj.entity.BannerDataListener;
import com.baizhi.wbj.entity.BannerDto;
import com.baizhi.wbj.service.BannerService;
import com.baizhi.wbj.util.HttpU;
import com.baizhi.wbj.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

//增删改
    @RequestMapping("save")
    public Map save(String oper,Banner banner,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String bannerId = UUID.randomUUID().toString().replace("-","");
            hashMap.put("bannerId",bannerId);
            banner.setId(bannerId);
            bannerService.insertBanner(banner);
            return hashMap;
        }else if(oper.equals("del")){
            bannerService.removeBanner(id);
            return null;
        }else{
            banner.setUrl(null);
            bannerService.modifyBanner(banner);
            hashMap.put("bannerId",banner.getId());
            return hashMap;
        }
    }
//分也查
    @RequestMapping("/queryByPage")
    public BannerDto queryByPage(Integer page, Integer rows){
        BannerDto bannerDto = bannerService.queryByPage(page,rows);
        return bannerDto;
    }
//文件上传
    @RequestMapping("/upload")
    public Map upload(MultipartFile url, String bannerId, HttpServletRequest request){
//        调用工具类获取上传图片 并返回uri
        String uri = HttpU.getUrl(url, request,"/upload/img/");
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(uri);
        bannerService.modifyBanner(banner);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }

    //EasyExcel导出轮播图信息
    @RequestMapping("/exportBanner")
    @ResponseBody
    public Map exportBanner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.queryAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String url = split[split.length-1];
            banner.setUrl(url);
        }
//        String fileName = "D:\\Program Files\\feiq\\Recv Files\\example\\"+new Date().getTime()+".xls";
        response.setHeader("Content-Disposition", "attachment; filename="+new Date().getTime()+".xls");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream,Banner.class).sheet("banner").doWrite(banners);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        System.out.println("123");
        return hashMap;
    }

    //EasyExcel导入轮播图信息
    @RequestMapping("/importBanner")
    @ResponseBody
    public Map importBanner(MultipartFile inputBanner,HttpServletRequest request){
        String http = HttpUtil.getHttp(inputBanner, request, "/upload/importBanner/");
        String s = http.split("/")[http.split("/").length - 1];
        String realPath = request.getSession().getServletContext().getRealPath("/upload/importBanner/");
        String path = realPath+s;
        System.out.println("path = " + path);
        EasyExcel.read(path,Banner.class,new BannerDataListener()).sheet().doRead();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    @RequestMapping("downloadBanner")
    public void downloadBanner(HttpServletResponse response) throws IOException {
        Banner banner = new Banner();
//        String fileName = "D:\\Program Files\\feiq\\Recv Files\\example\\"+new Date().getTime()+".xls";
        response.setHeader("Content-Disposition", "attachment; filename="+new Date().getTime()+".xls");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream,Banner.class).sheet("banner").doWrite(Arrays.asList(banner));
    }
}

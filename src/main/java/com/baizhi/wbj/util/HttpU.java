package com.baizhi.wbj.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpU {
    public static String getUrl(MultipartFile file, HttpServletRequest request,String path){
//        获取真实路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
//        判断文件夹是否存在
        File file1 = new File(realPath);
        if(!file1.exists()){
            file1.mkdirs();
        }
//        为了防止重名,加入时间戳,得到新文件名
        String NewFileName =new Date().getTime()+"_"+file.getOriginalFilename();
//        获取网络路径
//        获取协议头
//        http
        String scheme = request.getScheme();
//        获取ip地址
//        Lenovo-PC/192.168.244.1
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        获取端口号
//        8989
        int serverPort = request.getServerPort();
//        获取项目名
//        /cmfz
        String contextPath = request.getContextPath();
//        获取项目的网络路径
//        http://Lenovo-PC/192.168.244.1:8989/cmfz/upload/img/1577432178277_1.jpg
//        http://192.168.244.1:8989/cmfz/upload/img/1577432534919_1.jpg
        String uri = scheme +"://"+localhost.split("/")[1]+":"+serverPort+contextPath+path+NewFileName;
//        文件上传
        try {
            file.transferTo(new File(realPath,NewFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}

package com.baizhi.wbj.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttp(MultipartFile file,HttpServletRequest request,String path){
//        获取真实路径
        String realpath = request.getSession().getServletContext().getRealPath(path);
//        判断文件夹是否存在
        File file1 = new File(realpath);
        if(!file1.exists()){
            file1.mkdirs();
        }
//        防止重名
        String originalFilename = file.getOriginalFilename();
        String filename = new Date().getTime()+"_"+originalFilename;
//        获取网络路径
//        获取协议头
        String scheme = request.getScheme();
//        获取ip地址
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        获取端口号
        int serverPort = request.getServerPort();
//        获取项目名
        String contextPath = request.getContextPath();
        String uri = scheme+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+path+filename;
        try {
//            文件上传
            file.transferTo(new File(realpath,filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}

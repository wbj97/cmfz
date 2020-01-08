package com.baizhi.wbj.controller;

import com.baizhi.wbj.entity.Guru;
import com.baizhi.wbj.service.GuruService;
import com.baizhi.wbj.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("queryAllGuru")
    public List<Guru> queryAllGuru(){
        List<Guru> gurus = guruService.queryAll();
        return gurus;
    }

    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        Map map = guruService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("editGuru")
    public Map editUser(Guru guru,String oper,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String guruId = UUID.randomUUID().toString();
            guru.setId(guruId);
            hashMap.put("guruId",guruId);
            hashMap.put("status",200);
            guruService.insertGuru(guru);
        }else if(oper.equals("edit")){
            hashMap.put("guruId",guru.getId());
            hashMap.put("status",200);
            guru.setPhoto(null);
            guruService.modifyGuru(guru);
        }else{
            guruService.removeGuru(id);
        }
        return hashMap;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile photo,HttpServletRequest request,String guruId){
        String http = HttpUtil.getHttp(photo, request, "/upload/guruImg/");
        Guru guru = new Guru();
        guru.setPhoto(http);
        guru.setId(guruId);
        System.out.println(guru);
        HashMap hashMap = new HashMap();
        guruService.modifyGuru(guru);
        hashMap.put("status",200);
        return hashMap;
    }
}

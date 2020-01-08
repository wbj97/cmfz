package com.baizhi.wbj.controller;


import com.baizhi.wbj.entity.Counter;
import com.baizhi.wbj.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    CounterService counterService;
    //11. 展示计数器
    @RequestMapping("/selectByUid")
    public Map selectByUid(String uid, String cid){
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //12.添加计数器
    @RequestMapping("/insertCounter")
    public Map insertCounter(String uid,String title,String cid){
        counterService.insertCounter(uid, title, cid);
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //13.删除计数器
    @RequestMapping("/deleteCounter")
    public Map deleteCounter(String id,String uid,String cid){
        counterService.removeCounter(id,uid,cid);
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //14. 表更计数器
    @RequestMapping("/updateCounter")
    public Map updateCounter(String uid,String id,long count,String cid){
        counterService.modifyCounter(uid, id, count, cid);

        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
}

package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Counter;
import org.apache.poi.ss.formula.functions.Count;

import java.util.List;
import java.util.Map;

public interface CounterService {
    public void insertCounter(String uid,String title,String cid);

    public void removeCounter(String id,String uid,String cid);

    public void modifyCounter(String id,String uid,long count,String cid);

    public List<Counter> queryByUid(String uid, String cid);
}

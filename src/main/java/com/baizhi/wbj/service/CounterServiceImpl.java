package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.CounterDao;
import com.baizhi.wbj.entity.Counter;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;
    @Override
    public void insertCounter(String uid, String title, String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setTitle(title);
        counter.setId(UUID.randomUUID().toString());
        counter.setCreateDate(new Date());
        counter.setCount(0);
        counter.setCourseId(cid);
        counterDao.insertSelective(counter);
    }

    @Override
    public void removeCounter(String id, String uid, String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setId(id);
        counter.setCourseId(cid);
        counterDao.delete(counter);
    }

    @Override
    public void modifyCounter(String id, String uid, long count, String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setCount(count);
        counter.setId(id);
        counter.setCourseId(cid);
        counterDao.updateByPrimaryKeySelective(counter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Counter> queryByUid(String uid, String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setCourseId(cid);
        List<Counter> select = counterDao.select(counter);
        return select;
    }
}

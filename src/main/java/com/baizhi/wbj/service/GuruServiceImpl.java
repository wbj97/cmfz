package com.baizhi.wbj.service;

import com.baizhi.wbj.dao.GuruDao;
import com.baizhi.wbj.entity.Guru;
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
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;
    @Override
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public void insertGuru(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    public void removeGuru(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void modifyGuru(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    public Map queryByPage(int currentPage, int pageSize) {
        HashMap hashMap = new HashMap();
        hashMap.put("page",currentPage);
        int count = guruDao.selectCount(null);
        hashMap.put("records",count);
        int total = count%pageSize==0?count/pageSize:count/pageSize+1;
        hashMap.put("total",total);
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds((currentPage - 1) * pageSize, pageSize));
        hashMap.put("rows",gurus);
        return hashMap;
    }
}

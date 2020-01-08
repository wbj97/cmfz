package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    public List<Guru> queryAll();

    public void insertGuru(Guru guru);

    public void removeGuru(String[] id);

    public void modifyGuru(Guru guru);

    public Map queryByPage(int currentPage,int pageSize);
}

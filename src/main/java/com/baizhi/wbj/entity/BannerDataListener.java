package com.baizhi.wbj.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.wbj.dao.BannerDao;
import com.baizhi.wbj.util.MyWebAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;

public class BannerDataListener extends AnalysisEventListener<Banner> {

    ArrayList arrayList = new ArrayList();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        arrayList.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        BannerDao bannerDao = (BannerDao) MyWebAware.getBeanByClass(BannerDao.class);
        bannerDao.insertList(arrayList);
    }
}

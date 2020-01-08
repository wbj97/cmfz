package com.baizhi.wbj.service;

import com.baizhi.wbj.annotation.LogAnnotation;
import com.baizhi.wbj.dao.BannerDao;
import com.baizhi.wbj.entity.Banner;
import com.baizhi.wbj.entity.BannerDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;
    @Override
    public void insertBanner(Banner banner) {
        bannerDao.insertSelective(banner);
    }

    @Override
    public void removeBanner(String[] ids) {
        bannerDao.deleteByIdList(Arrays.asList(ids));
    }

    @Override
    public void modifyBanner(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    @LogAnnotation(value = "分页轮播图信息")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public BannerDto queryByPage(int currentPage, int pageSize) {
        BannerDto dto = new BannerDto();
        dto.setPage(currentPage);
        int count = bannerDao.selectCount(null);
        dto.setRecords(count);
        dto.setTotal(count%pageSize==0?count/pageSize:count/pageSize+1);
//        计算下标
        int index = (currentPage-1)*pageSize;
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds(index, pageSize));
        dto.setRows(banners);
        return dto;
    }

    @Override
    public List<Banner> queryAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    @Override
    public List<Banner> queryBannersByTime() {
        List<Banner> banners = bannerDao.queryBannersByTime();
        return banners;
    }
}

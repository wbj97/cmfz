package com.baizhi.wbj.service;

import com.baizhi.wbj.entity.Banner;
import com.baizhi.wbj.entity.BannerDto;

import java.util.List;

public interface BannerService  {
    public void insertBanner(Banner banner);

    public void removeBanner(String[] ids);

    public void modifyBanner(Banner banner);

    public BannerDto queryByPage(int currentPage, int pageSize);

    public List<Banner> queryAll();

    public List<Banner> queryBannersByTime();
}

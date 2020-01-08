package com.baizhi.wbj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto implements Serializable {
//    当前页数
    private Integer page;
//    总页数
    private Integer total;
//    总行数
    private Integer records;
//    当前页的数据
    private List<Banner> rows;
}

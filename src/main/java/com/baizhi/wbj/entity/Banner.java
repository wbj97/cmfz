package com.baizhi.wbj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {

  @Id
  @ExcelProperty("ID")
  private String id;
  @ExcelProperty("标题")
  private String title;
  @ExcelProperty(value = "图片",converter = ImageConverter.class)
  private String url;
  @ExcelProperty("路径")
  private String href;
  @ExcelProperty("创建时间")
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createDate;
  @ExcelProperty("描述")
  private String description;
  @ExcelProperty("状态")
  private String status;

}

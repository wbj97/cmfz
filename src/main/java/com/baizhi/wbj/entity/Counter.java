package com.baizhi.wbj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counter {
  @Id
  private String id;
  private String title;
  private long count;
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createDate;
  private String userId;
  private String courseId;

}

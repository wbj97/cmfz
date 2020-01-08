package com.baizhi.wbj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable{
  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String cover;
  private String broadcast;
  private long count;
  @Column(name = "`desc`")
  private String desc;
  private String status;
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createDate;


}

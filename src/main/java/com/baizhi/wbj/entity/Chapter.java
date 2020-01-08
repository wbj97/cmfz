package com.baizhi.wbj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
  @Id
  private String id;
  private String title;
  private String url;
  private Double size;
  private String time;
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createTime;
  private String albumId;


}

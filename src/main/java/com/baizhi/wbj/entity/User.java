package com.baizhi.wbj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
  @Id
  private String id;
  private String phone;
  private String password;
  private String salt;
  private String status;
  private String photo;
  private String name;
  private String nickName;
  private String sex;
  private String sign;
  private String location;
  @JSONField(format = "yyyy-MM-dd")
  private Date registerDate;
  @JSONField(format = "yyyy-MM-dd")
  private Date lastLogin;


}

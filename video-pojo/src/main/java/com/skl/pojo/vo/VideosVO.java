package com.skl.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VideosVO {
  private String id;
  private String userId;
  private String audioId;
  private String videoDesc;
  private String videoPath;
  private Float videoSeconds;
  private Integer videoWidth;
  private Integer videoHeight;
  private String coverPath;
  private Long likeCounts;
  private Integer status;
  private Date createTime;
  // 关联的
  private String faceImage;
  private String nickname;
}
package com.skl.pojo.vo;


import lombok.Data;

import java.util.Date;

@Data
public class CommentsVO {
  private String id;

  /**
   * 视频id
   */
  private String videoId;

  /**
   * 留言者，评论的用户id
   */
  private String fromUserId;

  private Date createTime;

  /**
   * 评论内容
   */
  private String comment;

  private String faceImage;
  private String nickname;
  private String toNickname;
  private String timeAgoStr;
}

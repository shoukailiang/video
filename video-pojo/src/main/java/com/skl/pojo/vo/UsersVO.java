package com.skl.pojo.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="用户对象", description="这是用户对象")
@Data
public class UsersVO {

  @ApiModelProperty(hidden=true)
  private String id;

  @ApiModelProperty(hidden=true)
  private String userToken;

  /**
   * 用户名
   */
  @ApiModelProperty(value="用户名", name="username", example="user", required=true)
  private String username;

  /**
   * 密码
   */
  @ApiModelProperty(value="密码", name="password", example="123456", required=true)
  @JsonIgnore
  private String password;

  /**
   * 我的头像，如果没有默认给一张
   */
  @ApiModelProperty(hidden=true)
  private String faceImage;

  /**
   * 昵称
   */
  private String nickname;

  /**
   * 我的粉丝数量
   */
  @ApiModelProperty(hidden=true)
  private Integer fansCounts;

  /**
   * 我关注的人总数
   */
  @ApiModelProperty(hidden=true)
  private Integer followCounts;

  /**
   * 我接受到的赞美/收藏 的数量
   */
  @ApiModelProperty(hidden=true)
  private Integer receiveLikeCounts;
}
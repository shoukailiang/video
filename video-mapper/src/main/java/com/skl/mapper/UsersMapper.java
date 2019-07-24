package com.skl.mapper;

import com.skl.pojo.Users;
import com.skl.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {



  /**
   * 用户受喜欢数累加
   *
   * @param userId
   */
  void addReceiveLikeCount(String userId);

  /**
   * 用户受喜欢数累减
   *
   * @param userId
   */
  void reduceReceiveLikeCount(String userId);

  /**
   * 增加粉丝数
   * @param userId
   */
  void addFansCount(String userId);

  /**
   * 增加关注数
   * @param userId
   */
  void addFollersCount(String userId);

  /**
   * 减少粉丝数
   * @param userId
   */
  void reduceFansCount(String userId);

  /**
   * 减少关注数
   * @param userId
   */
  void reduceFollersCount(String userId);

}
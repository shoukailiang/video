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

}
package com.skl.service;


import com.skl.pojo.Users;
import com.skl.pojo.UsersReport;


public interface UserService {

  /**
   * 判断用户名是否存在
   *
   * @param username
   * @return
   */
  boolean queryUsernameIsExist(String username);

  /**
   * 保存用户(用户注册)
   *
   * @param user
   */
  void saveUser(Users user);

  /**
   * @param username
   * @param md5Str
   * @return
   */
  Users queryUserForLogin(String username, String md5Str);

  /**
   * 用户修改信息
   *
   * @param user
   */
  void updateUserInfo(Users user);

  /**
   * 查询用户信息
   *
   * @param userId
   * @return
   */
  Users queryUserInfo(String userId);

  /**
   * 查询用户是否喜欢点赞视频
   *
   * @param loginUserId
   * @param videoId
   * @return
   */
  boolean isUserLikeVideo(String loginUserId, String videoId);

  /**
   * 增加用户和粉丝的关系
   *
   * @param userId
   * @param fanId
   */
  void saveUserFanRelation(String userId, String fanId);

  /**
   * 删除用户和粉丝的关系
   *
   * @param userId
   * @param fanId
   */
  void deleteUserFanRelation(String userId, String fanId);

  /**
   * 查询用户是否关注
   *
   * @param userId
   * @param fanId
   * @return
   */
  boolean queryIfFollow(String userId, String fanId);

  void reportUser(UsersReport usersReport);
}

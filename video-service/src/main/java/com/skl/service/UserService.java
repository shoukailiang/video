package com.skl.service;


import com.skl.pojo.Users;


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
   *
   * @param username
   * @param md5Str
   * @return
   */
  Users queryUserForLogin(String username, String md5Str);

  /**
   * 用户修改信息
   * @param user
   */
  void updateUserInfo(Users user);

  /**
   * 查询用户信息
   * @param userId
   * @return
   */
  Users queryUserInfo(String userId);
}

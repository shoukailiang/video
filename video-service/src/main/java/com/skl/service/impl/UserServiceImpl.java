package com.skl.service.impl;

import com.skl.mapper.UsersMapper;
import com.skl.pojo.Users;
import com.skl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UsersMapper usersMapper;

  @Override
  public boolean queryUsernameIsExist(String username) {

    Users user = new Users();
    user.setUsername(username);
    Users selectOne = usersMapper.selectOne(user);

    return selectOne == null ? false : true;
  }

  @Override
  public void saveUser(Users user) {

  }
}

package com.skl.service.impl;

import com.skl.mapper.UsersMapper;
import com.skl.pojo.Users;
import com.skl.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UsersMapper usersMapper;

  @Autowired
  private Sid sid;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public boolean queryUsernameIsExist(String username) {

    Users user = new Users();
    user.setUsername(username);
    Users selectOne = usersMapper.selectOne(user);

    return selectOne == null ? false : true;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void saveUser(Users user) {
    String userId = sid.nextShort();
    user.setId(userId);
    usersMapper.insert(user);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Users queryUserForLogin(String username, String password) {
    Example example = new Example(Users.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("username", username);
    criteria.andEqualTo("password", password);
    Users user = usersMapper.selectOneByExample(example);
    return user;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateUserInfo(Users user) {
    Example userExample = new Example(Users.class);
    Example.Criteria criteria = userExample.createCriteria();
    criteria.andEqualTo("id", user.getId());
    usersMapper.updateByExampleSelective(user, userExample);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Users queryUserInfo(String userId) {
    Example example = new Example(Users.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id",userId);
    Users user = usersMapper.selectOneByExample(example);
    return user;
  }
}

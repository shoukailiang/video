package com.skl.service.impl;

import com.skl.mapper.UsersFansMapper;
import com.skl.mapper.UsersLikeVideosMapper;
import com.skl.mapper.UsersMapper;
import com.skl.mapper.UsersReportMapper;
import com.skl.pojo.Users;
import com.skl.pojo.UsersFans;
import com.skl.pojo.UsersLikeVideos;
import com.skl.pojo.UsersReport;
import com.skl.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UsersMapper usersMapper;

  @Autowired
  private UsersReportMapper usersReportMapper;

  @Autowired
  private UsersLikeVideosMapper usersLikeVideosMapper;

  @Autowired
  private UsersFansMapper usersFansMapper;

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

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public boolean isUserLikeVideo(String userId, String videoId) {
    if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
      return false;
    }

    Example example = new Example(UsersLikeVideos.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("userId", userId);
    criteria.andEqualTo("videoId", videoId);

    List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);

    if (list != null && list.size() >0) {
      return true;
    }

    return false;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void saveUserFanRelation(String userId, String fanId) {
    String relId = sid.nextShort();

    UsersFans userFan = new UsersFans();
    userFan.setId(relId);
    userFan.setUserId(userId);
    userFan.setFanId(fanId);

    usersFansMapper.insert(userFan);


    usersMapper.addFansCount(userId);
    usersMapper.addFollersCount(fanId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void deleteUserFanRelation(String userId, String fanId) {
    Example example = new Example(UsersFans.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("userId", userId);
    criteria.andEqualTo("fanId", fanId);

    usersFansMapper.deleteByExample(example);

    usersMapper.reduceFansCount(userId);
    usersMapper.reduceFollersCount(fanId);
  }

  @Override
  public boolean queryIfFollow(String userId, String fanId) {
    Example example = new Example(UsersFans.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("userId", userId);
    criteria.andEqualTo("fanId", fanId);

    List<UsersFans> list = usersFansMapper.selectByExample(example);

    if (list != null && !list.isEmpty() && list.size() > 0) {
      return true;
    }
    return false;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void reportUser(UsersReport usersReport) {
    String urId = sid.nextShort();
    usersReport.setId(urId);
    usersReport.setCreateDate(new Date());

    usersReportMapper.insert(usersReport);
  }
}

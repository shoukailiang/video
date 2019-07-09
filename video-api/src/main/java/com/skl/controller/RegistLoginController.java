package com.skl.controller;

import com.skl.pojo.Users;
import com.skl.pojo.vo.UsersVO;
import com.skl.service.UserService;
import com.skl.utils.MD5Utils;
import com.skl.utils.SklJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户登录注册的接口", tags = {"注册登录的controller"})
public class RegistLoginController extends BasicController {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "用户注册", notes = "用户注册的接口")
  @PostMapping("/regist")
  public SklJSONResult regist(@RequestBody() Users user) throws Exception {
    // 1. 判断用户名和密码必须不为空
    if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
      return SklJSONResult.errorMsg("用户名和密码不能为空");
    }
    // 2. 判断用户名是否存在
    boolean queryUsernameIsExist = userService.queryUsernameIsExist(user.getUsername());
    // 3. 保存用户，注册信息
    if (!queryUsernameIsExist) {
      user.setNickname(user.getUsername());
      user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
      user.setFansCounts(0);
      user.setReceiveLikeCounts(0);
      user.setFollowCounts(0);
      userService.saveUser(user);
    } else {
      return SklJSONResult.errorMsg("用户名已经存在，请换一个再试");
    }

    user.setPassword("");

    UsersVO usersVO = setUserRedisSessionToken(user);
    return SklJSONResult.ok(usersVO);
  }

  public UsersVO setUserRedisSessionToken(Users user){
    String uniqueToken = UUID.randomUUID().toString();
    redisOperator.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 * 30);// 30分钟
    UsersVO usersVO = new UsersVO();
    BeanUtils.copyProperties(user,usersVO);
    usersVO.setUserToken(uniqueToken);
    return usersVO;
  }

  @ApiOperation(value = "用户登录", notes = "用户登录的接口")
  @PostMapping("/login")
  public SklJSONResult login(@RequestBody Users user) throws Exception {
    String username = user.getUsername();
    String password = user.getPassword();
    // 1. 判断用户名和密码必须不为空
    if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
      return SklJSONResult.ok("用户名或密码不能为空...");
    }

    // 2. 判断用户是否存在
    Users userResult = userService.queryUserForLogin(username,
        MD5Utils.getMD5Str(user.getPassword()));

    // 3. 返回
    if (userResult != null) {
      userResult.setPassword("");
      UsersVO usersVO = setUserRedisSessionToken(userResult);
      return SklJSONResult.ok(usersVO);
    } else {
      return SklJSONResult.errorMsg("用户名或密码不正确, 请重试...");
    }
  }

  @ApiOperation(value = "用户注销", notes = "用户注销的接口")
  @ApiImplicitParam(name = "userId",value = "用户id",required = true,dataType = "String",paramType = "query")// paramType = "query" 以?在后面拼接
  @PostMapping("/logout")
  public SklJSONResult logout(String userId) throws Exception {
    redisOperator.del(USER_REDIS_SESSION+":"+userId);
    return SklJSONResult.ok();
  }
}

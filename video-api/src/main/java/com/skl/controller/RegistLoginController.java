package com.skl.controller;

import com.skl.pojo.Users;
import com.skl.service.UserService;
import com.skl.utils.MD5Utils;
import com.skl.utils.SklJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "用户登录注册的接口",tags = {"注册登录的controller"})
public class RegistLoginController {

  @Autowired
  private UserService userService;

  @ApiOperation(value="用户注册", notes="用户注册的接口")
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

    return SklJSONResult.ok();
  }
}

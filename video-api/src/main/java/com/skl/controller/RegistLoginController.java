package com.skl.controller;

import com.skl.pojo.Users;
import com.skl.utils.SklJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistLoginController {

  @PostMapping("/regist")
  public SklJSONResult regist(@RequestBody() Users user){
    // 1. 判断用户名和密码必须不为空
    if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
      return SklJSONResult.errorMsg("用户名和密码不能为空");
    }

    return SklJSONResult.ok();
  }
}

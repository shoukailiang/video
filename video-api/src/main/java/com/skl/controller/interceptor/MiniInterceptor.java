package com.skl.controller.interceptor;

import com.skl.utils.JsonUtils;
import com.skl.utils.RedisOperator;
import com.skl.utils.SklJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class MiniInterceptor implements HandlerInterceptor {

  @Autowired
  private RedisOperator redis;

  public static final String USER_REDIS_SESSION = "USER-REDIS-SESSION";

  /**
   * 拦截请求，在controller调用之前
   * @param httpServletRequest
   * @param httpServletResponse
   * @param o
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

    String userId = httpServletRequest.getHeader("headerUserId");
    String userToken = httpServletRequest.getHeader("headerUserToken");

    if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
      String uniqueToken = redis.get(USER_REDIS_SESSION + ":" + userId);
      System.out.println(userId);
      // 有可能已经过期了
      if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
        System.out.println("请登录1...");
        returnErrorResponse(httpServletResponse, new SklJSONResult().errorTokenMsg("请登录..."));
        return false;
      } else {
        //fixme 如果redis和用户发过来的不一样，则说明这个账号在别的地方登陆了,新登陆的账号的key不一样
        if (!uniqueToken.equals(userToken)) {
          System.out.println("账号被挤出...");
          returnErrorResponse(httpServletResponse, new SklJSONResult().errorTokenMsg("账号被挤出..."));
          return false;
        }
      }
    } else {
      System.out.println("请登录2...");
      returnErrorResponse(httpServletResponse, new SklJSONResult().errorTokenMsg("请登录..."));
      return false;
    }

    return true;
    /**
     * 返回 false：请求被拦截，返回
     * 返回 true ：请求OK，可以继续执行，放行
     */
  }

  public void returnErrorResponse(HttpServletResponse response, SklJSONResult result)
      throws IOException, UnsupportedEncodingException {
    OutputStream out=null;
    try{
      response.setCharacterEncoding("utf-8");
      response.setContentType("text/json");
      out = response.getOutputStream();
      out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
      out.flush();
    } finally{
      if(out!=null){
        out.close();
      }
    }
  }
  /**
   * 请求controller之后，渲染视图之前
   */
  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  /**
   * 请求controller之后，视图渲染之后
   */
  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}

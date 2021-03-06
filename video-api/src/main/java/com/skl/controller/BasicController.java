package com.skl.controller;


import com.skl.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

  @Autowired
  public RedisOperator redisOperator;

  public static final String USER_REDIS_SESSION = "USER-REDIS-SESSION";


  // 文件保存的命名空间
  public static final String FILE_SPACE = "C:/video";

  // ffmpeg所在目录
  public static final String FFMPEG_EXE = "C:\\ffmpeg\\bin\\ffmpeg.exe";

  // 每页分页的记录数
  public static final Integer PAGE_SIZE = 5;
}

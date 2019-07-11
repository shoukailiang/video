package com.skl.service;


import com.skl.pojo.Bgm;

import java.util.List;

public interface BgmService {
  /**
   * 查询背景音乐列表
   * @return
   */
  List<Bgm> queryBgmList();
}

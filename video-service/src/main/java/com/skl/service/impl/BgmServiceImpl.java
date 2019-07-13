package com.skl.service.impl;

import com.skl.mapper.BgmMapper;
import com.skl.pojo.Bgm;
import com.skl.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

  @Autowired
  private BgmMapper bgmMapper;

  @Override
  public List<Bgm> queryBgmList() {
    List<Bgm> bgms = bgmMapper.selectAll();
    return bgms;
  }

  @Override
  public Bgm queryBgmById(String bgmId) {
    return bgmMapper.selectByPrimaryKey(bgmId);
  }
}

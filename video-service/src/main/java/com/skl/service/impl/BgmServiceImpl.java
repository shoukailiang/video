package com.skl.service.impl;

import com.skl.mapper.BgmMapper;
import com.skl.pojo.Bgm;
import com.skl.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

  @Autowired
  private BgmMapper bgmMapper;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<Bgm> queryBgmList() {
    List<Bgm> bgms = bgmMapper.selectAll();
    return bgms;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Bgm queryBgmById(String bgmId) {
    return bgmMapper.selectByPrimaryKey(bgmId);
  }
}

package com.skl.service.impl;

import com.skl.mapper.VideosMapper;
import com.skl.pojo.Videos;
import com.skl.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {
  @Autowired
  private Sid sid;
  @Autowired
  private VideosMapper videosMapper;

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public String saveVideo(Videos video) {

    String id = sid.nextShort();
    video.setId(id);
    videosMapper.insertSelective(video);
    return id;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void updateVideo(String videoId, String uploadPathDB) {
    Videos video = new Videos();
    video.setId(videoId);
    video.setCoverPath(uploadPathDB);
    videosMapper.updateByPrimaryKeySelective(video);
  }
}

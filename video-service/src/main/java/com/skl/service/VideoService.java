package com.skl.service;

import com.skl.pojo.Videos;
import com.skl.utils.PagedResult;

import java.util.List;

public interface VideoService {

  String saveVideo(Videos video);

  void updateVideo(String videoId, String uploadPathDB);

  /**
   * 分页查询视频列表
   * @param video
   * @param isSaveRecord
   * @param page
   * @param pageSize
   * @return
   */
  PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

  /**
   * 获取热搜词列表
   * @return
   */
  List<String> getHotwords();
}

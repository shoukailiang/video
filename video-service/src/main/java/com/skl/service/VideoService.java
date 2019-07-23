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

  /**
   * 用户喜欢/点赞视频
   * @param userId
   * @param videoId
   * @param videoCreaterId  视频创建者的id
   */
  public void userLikeVideo(String userId, String videoId, String videoCreaterId);

  /**
   * 用户不喜欢/取消点赞视频
   * @param userId
   * @param videoId
   * @param videoCreaterId
   */
  public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
}

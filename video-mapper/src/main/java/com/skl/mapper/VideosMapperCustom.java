package com.skl.mapper;

import com.skl.pojo.Videos;
import com.skl.pojo.vo.VideosVO;
import com.skl.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {

  List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc, @Param("userId") String userId);


  void addVideoLikeCount(String videoId);


  void reduceVideoLikeCount(String videoId);

  /**
   * 查询点赞视频
   *
   * @param userId
   * @return
   */
  List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);

  /**
   * 查询关注的视频
   *
   * @param userId
   * @return
   */
  List<VideosVO> queryMyFollowVideos(String userId);


}
package com.skl.mapper;

import com.skl.pojo.Videos;
import com.skl.pojo.vo.VideosVO;
import com.skl.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {

  List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);


  void addVideoLikeCount(String videoId);


  void reduceVideoLikeCount(String videoId);
}
package com.skl.service;

import com.skl.pojo.Videos;

public interface VideoService {

  String saveVideo(Videos video);

  void updateVideo(String videoId, String uploadPathDB);
}

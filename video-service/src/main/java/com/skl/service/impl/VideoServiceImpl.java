package com.skl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skl.mapper.SearchRecordsMapper;
import com.skl.mapper.VideosMapper;
import com.skl.mapper.VideosMapperCustom;
import com.skl.pojo.SearchRecords;
import com.skl.pojo.Videos;
import com.skl.pojo.vo.VideosVO;
import com.skl.service.VideoService;
import com.skl.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
  @Autowired
  private Sid sid;
  @Autowired
  private VideosMapper videosMapper;

  @Autowired
  private VideosMapperCustom videosMapperCustom;

  @Autowired
  private SearchRecordsMapper searchRecordsMapper;

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

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {

    // 保存热搜词
    String desc = video.getVideoDesc();
    String userId = video.getUserId();
    if (isSaveRecord != null && isSaveRecord == 1) {
      SearchRecords record = new SearchRecords();
      String recordId = sid.nextShort();
      record.setId(recordId);
      record.setContent(desc);
      searchRecordsMapper.insert(record);
    }

    PageHelper.startPage(page, pageSize);

    List<VideosVO> list = videosMapperCustom.queryAllVideos(desc);
    PageInfo<VideosVO> pageList = new PageInfo<VideosVO>(list);

    PagedResult pagedResult = new PagedResult();
    pagedResult.setPage(page);
    pagedResult.setTotal(pageList.getPages());
    pagedResult.setRows(list);
    pagedResult.setRecords(pageList.getTotal());

    return pagedResult;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public List<String> getHotwords() {
    return searchRecordsMapper.getHotwords();
  }
}

package com.skl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skl.mapper.*;
import com.skl.pojo.Comments;
import com.skl.pojo.SearchRecords;
import com.skl.pojo.UsersLikeVideos;
import com.skl.pojo.Videos;
import com.skl.pojo.vo.CommentsVO;
import com.skl.pojo.vo.VideosVO;
import com.skl.service.VideoService;
import com.skl.utils.PagedResult;
import com.skl.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
  @Autowired
  private Sid sid;
  @Autowired
  private VideosMapper videosMapper;

  @Autowired
  private UsersMapper usersMapper;

  @Autowired
  private VideosMapperCustom videosMapperCustom;

  @Autowired
  private SearchRecordsMapper searchRecordsMapper;


  @Autowired
  private UsersLikeVideosMapper usersLikeVideosMapper;

  @Autowired
  private CommentsMapper commentsMapper;


  @Autowired
  private CommentsMapperCustom commentsMapperCustom;


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

    List<VideosVO> list = videosMapperCustom.queryAllVideos(desc, userId);
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


  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
    // 1. 保存用户和视频的喜欢点赞关联关系表
    String likeId = sid.nextShort();
    UsersLikeVideos ulv = new UsersLikeVideos();
    ulv.setId(likeId);
    ulv.setUserId(userId);
    ulv.setVideoId(videoId);
    usersLikeVideosMapper.insert(ulv);

    // 2. 视频喜欢数量累加
    videosMapperCustom.addVideoLikeCount(videoId);

    // 3. 用户受喜欢数量的累加
    usersMapper.addReceiveLikeCount(videoCreaterId);
  }


  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
    // 1. 删除用户和视频的喜欢点赞关联关系表

    Example example = new Example(UsersLikeVideos.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("userId", userId);
    criteria.andEqualTo("videoId", videoId);

    usersLikeVideosMapper.deleteByExample(example);

    // 2. 视频喜欢数量累减
    videosMapperCustom.reduceVideoLikeCount(videoId);

    // 3. 用户受喜欢数量的累减
    usersMapper.reduceReceiveLikeCount(videoCreaterId);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
    PageHelper.startPage(page, pageSize);
    List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);

    PageInfo<VideosVO> pageList = new PageInfo<>(list);

    PagedResult pagedResult = new PagedResult();
    pagedResult.setTotal(pageList.getPages());
    pagedResult.setRows(list);
    pagedResult.setPage(page);
    pagedResult.setRecords(pageList.getTotal());

    return pagedResult;
  }

  @Override
  public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
    PageHelper.startPage(page, pageSize);
    List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);

    PageInfo<VideosVO> pageList = new PageInfo<>(list);

    PagedResult pagedResult = new PagedResult();
    pagedResult.setTotal(pageList.getPages());
    pagedResult.setRows(list);
    pagedResult.setPage(page);
    pagedResult.setRecords(pageList.getTotal());

    return pagedResult;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public void saveComment(Comments comment) {
    String id = sid.nextShort();
    comment.setId(id);
    comment.setCreateTime(new Date());
    commentsMapper.insert(comment);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
    PageHelper.startPage(page, pageSize);

    List<CommentsVO> list = commentsMapperCustom.queryComments(videoId);

    for (CommentsVO c : list) {
      String timeAgo = TimeAgoUtils.format(c.getCreateTime());
      c.setTimeAgoStr(timeAgo);
    }

    PageInfo<CommentsVO> pageList = new PageInfo<>(list);

    PagedResult grid = new PagedResult();
    grid.setTotal(pageList.getPages());
    grid.setRows(list);
    grid.setPage(page);
    grid.setRecords(pageList.getTotal());

    return grid;
  }
}

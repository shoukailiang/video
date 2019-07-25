package com.skl.mapper;

import com.skl.pojo.Comments;
import com.skl.pojo.vo.CommentsVO;
import com.skl.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
  List<CommentsVO> queryComments(String videoId);
}

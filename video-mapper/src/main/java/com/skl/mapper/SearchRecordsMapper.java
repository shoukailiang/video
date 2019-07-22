package com.skl.mapper;

import com.skl.pojo.SearchRecords;
import com.skl.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
  List<String> getHotwords();
}
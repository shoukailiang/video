package com.skl.controller;


import com.skl.emums.VideoStatusEnum;
import com.skl.pojo.Bgm;
import com.skl.pojo.Videos;
import com.skl.service.BgmService;
import com.skl.service.VideoService;
import com.skl.utils.FetchVideoCover;
import com.skl.utils.MergeVideoMp3;
import com.skl.utils.PagedResult;
import com.skl.utils.SklJSONResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;


@RestController
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {

  @Autowired
  private BgmService bgmService;

  @Autowired
  private VideoService videoService;


  @ApiOperation(value = "上传视频", notes = "上传视频的接口")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userId", value = "用户id", required = true,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
          dataType = "String", paramType = "form")
  })
  @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
  public SklJSONResult upload(String userId,
                              String bgmId, double videoSeconds,
                              int videoWidth, int videoHeight,
                              String desc,
                              @ApiParam(value = "短视频", required = true)
//                              @RequestParam("file")
                                  MultipartFile file) throws Exception {


    if (StringUtils.isBlank(userId)) {
      return SklJSONResult.errorMsg("用户id不能为空...");
    }

    // 文件保存的命名空间
//    String fileSpace = "c:/video";

    // 保存到数据库中的相对路径
    String uploadPathDB = "/" + userId + "/video";
    String coverPathDB = "/" + userId + "/video";


    String finalVideoPath = "";

    FileOutputStream fileOutputStream = null;
    InputStream inputStream = null;
    try {
      if (file != null) {

        String fileName = file.getOriginalFilename();

        // abc.mp4
        String arrayFilenameItem[] = fileName.split("\\.");
        String fileNamePrefix = "";
        for (int i = 0; i < arrayFilenameItem.length - 1; i++) {
          fileNamePrefix += arrayFilenameItem[i];
        }

        if (StringUtils.isNotBlank(fileName)) {
          // 文件上传的最终保存路径
          finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
          // 设置数据库保存的路径
          uploadPathDB += ("/" + fileName);
          coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

          File outFile = new File(finalVideoPath);
          // path是目录返回true
          if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
            // 创建父文件夹
            // C:\video\190707H4HH45HXS8\video
            outFile.getParentFile().mkdirs();
          }

          fileOutputStream = new FileOutputStream(outFile);
          inputStream = file.getInputStream();
          IOUtils.copy(inputStream, fileOutputStream);
        }

      } else {
        return SklJSONResult.errorMsg("上传出错...");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return SklJSONResult.errorMsg("上传出错...");
    } finally {
      if (fileOutputStream != null) {
        fileOutputStream.flush();
        fileOutputStream.close();
      }
    }

    // 判断bgmId是否为空，如果不为空，
    // 那就查询bgm的信息，并且合并视频，生产新的视频

    if (StringUtils.isNotBlank(bgmId)) {
      Bgm bgm = bgmService.queryBgmById(bgmId);
      // bgm 路径
      String mp3InputPath = FILE_SPACE + bgm.getPath();
      MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
      String videoInputPath = finalVideoPath;
      // 生成新视频的名字
      String videoOutputName = UUID.randomUUID().toString() + ".mp4";
      // 视频的路径
      uploadPathDB = "/" + userId + "/video" + "/" + videoOutputName;
      // 最终新视频路径
      finalVideoPath = FILE_SPACE + uploadPathDB;
      tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
    }
    System.out.println("uploadPathDB=" + uploadPathDB);
    System.out.println("finalVideoPath=" + finalVideoPath);

    // 对视频进行截图
    FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
    videoInfo.getCover(finalVideoPath, FILE_SPACE + coverPathDB);

    // 保存视频信息到数据库
    Videos video = new Videos();
    video.setAudioId(bgmId);
    video.setUserId(userId);
    video.setVideoSeconds((float) videoSeconds);
    video.setVideoHeight(videoHeight);
    video.setVideoWidth(videoWidth);
    video.setVideoDesc(desc);
    video.setVideoPath(uploadPathDB);
    video.setCoverPath(coverPathDB);
    video.setStatus(VideoStatusEnum.SUCCESS.value);
    video.setCreateTime(new Date());

    // 这里的id是为了上传封面所使用的
    String videoId = videoService.saveVideo(video);

    return SklJSONResult.ok(videoId);
  }


  @ApiOperation(value = "上传封面", notes = "上传封面的接口")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userId", value = "用户id", required = true,
          dataType = "String", paramType = "form"),
      @ApiImplicitParam(name = "videoId", value = "视频主键id", required = true,
          dataType = "String", paramType = "form")
  })
  @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
  public SklJSONResult uploadCover(String userId, String videoId,
                                   @ApiParam(value = "视频封面", required = true)
                                       MultipartFile file) throws Exception {

    if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
      return SklJSONResult.errorMsg("视频主键id或用户id不能为空...");
    }

    // 文件保存的命名空间
//		String fileSpace = "C:/video";
    // 保存到数据库中的相对路径
    String uploadPathDB = "/" + userId + "/video";

    FileOutputStream fileOutputStream = null;
    InputStream inputStream = null;
    // 文件上传的最终保存路径
    String finalCoverPath = "";
    try {
      if (file != null) {

        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {

          finalCoverPath = FILE_SPACE + uploadPathDB + "/" + fileName;
          // 设置数据库保存的路径
          uploadPathDB += ("/" + fileName);

          File outFile = new File(finalCoverPath);
          if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
            // 创建父文件夹
            outFile.getParentFile().mkdirs();
          }

          fileOutputStream = new FileOutputStream(outFile);
          inputStream = file.getInputStream();
          IOUtils.copy(inputStream, fileOutputStream);
        }

      } else {
        return SklJSONResult.errorMsg("上传出错...");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return SklJSONResult.errorMsg("上传出错...");
    } finally {
      if (fileOutputStream != null) {
        fileOutputStream.flush();
        fileOutputStream.close();
      }
    }

    videoService.updateVideo(videoId, uploadPathDB);

    return SklJSONResult.ok();
  }
  /**
   * 分页和搜索查询视频列表
   * @param video
   * @param isSaveRecord  1 - 需要保存  0 - 不需要保存 ，或者为空的时候
   * @param page
   * @param pageSize
   * @return
   * @throws Exception
   */
  @PostMapping(value = "/showAll")
  public SklJSONResult showAll(@RequestBody Videos video, Integer isSaveRecord, Integer page, Integer pageSize) throws Exception {

    if (page == null) {
      page = 1;
    }

    if (pageSize == null) {
      pageSize = PAGE_SIZE;
    }

    PagedResult result = videoService.getAllVideos(video, isSaveRecord, page, pageSize);
    return SklJSONResult.ok(result);
  }


  @PostMapping(value="/hot")
  public SklJSONResult hot() throws Exception {
    return SklJSONResult.ok(videoService.getHotwords());
  }


  @PostMapping(value="/userLike")
  public SklJSONResult userLike(String userId, String videoId, String videoCreaterId)
      throws Exception {
    videoService.userLikeVideo(userId, videoId, videoCreaterId);
    return SklJSONResult.ok();
  }

  @PostMapping(value="/userUnLike")
  public SklJSONResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
    videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
    return SklJSONResult.ok();
  }


  /**
   * 我关注的人发的视频
   * @param userId
   * @param page
   * @return
   * @throws Exception
   */
  @PostMapping("/showMyFollow")
  public SklJSONResult showMyFollow(String userId, Integer page) throws Exception {

    if (StringUtils.isBlank(userId)) {
      return SklJSONResult.ok();
    }

    if (page == null) {
      page = 1;
    }

    int pageSize = 6;

    PagedResult videosList = videoService.queryMyFollowVideos(userId, page, pageSize);

    return SklJSONResult.ok(videosList);
  }

  /**
   * 我收藏(点赞)过的视频列表
   * @param userId
   * @param page
   * @param pageSize
   * @return
   * @throws Exception
   */
  @PostMapping("/showMyLike")
  public SklJSONResult showMyLike(String userId, Integer page, Integer pageSize) throws Exception {

    if (StringUtils.isBlank(userId)) {
      return SklJSONResult.ok();
    }

    if (page == null) {
      page = 1;
    }

    if (pageSize == null) {
      pageSize = 6;
    }

    PagedResult videosList = videoService.queryMyLikeVideos(userId, page, pageSize);

    return SklJSONResult.ok(videosList);
  }
}

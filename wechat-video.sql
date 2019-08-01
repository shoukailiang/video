/*
 Navicat MySQL Data Transfer

 Source Server         : skl
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : wechat-video

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 01/08/2019 18:32:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('1', '很美味', '东西', '/bgm/很美味 - 东西.mp3');
INSERT INTO `bgm` VALUES ('2', '桀一Jelly', '你好吗（cover）', '/bgm/桀一Jelly - 你好吗.mp3');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `father_comment_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `video_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频id',
  `from_user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言者，评论的用户id',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('1907257Z23R1M8ZC', 'undefined', 'undefined', '190724B5KS6P7WPH', '190724AYBKT0DYFW', '12122', '2019-07-25 11:09:47');
INSERT INTO `comments` VALUES ('1907257Z30AKMGF8', 'undefined', 'undefined', '190724B5KS6P7WPH', '190724AYBKT0DYFW', '1212121212', '2019-07-25 11:09:53');
INSERT INTO `comments` VALUES ('1907257ZXA1YCC28', '1907257Z30AKMGF8', '190724AYBKT0DYFW', '190724B5KS6P7WPH', '190724AYBKT0DYFW', 'sklskl', '2019-07-25 11:12:17');

-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '搜索的内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频搜索的记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of search_records
-- ----------------------------
INSERT INTO `search_records` VALUES ('1', '抖音');
INSERT INTO `search_records` VALUES ('190722BP32ZNTSCH', 'java');
INSERT INTO `search_records` VALUES ('190722BXRF7SP65P', 'java');
INSERT INTO `search_records` VALUES ('190722BY32760754', 'java');
INSERT INTO `search_records` VALUES ('190722BYKP5ZF2RP', 'js');
INSERT INTO `search_records` VALUES ('190722C6D0PKASNC', 'java');
INSERT INTO `search_records` VALUES ('2', '美女');
INSERT INTO `search_records` VALUES ('3', ' 帅哥');
INSERT INTO `search_records` VALUES ('4', '大哥');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `face_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `fans_counts` int(11) NULL DEFAULT 0 COMMENT '我的粉丝数量',
  `follow_counts` int(11) NULL DEFAULT 0 COMMENT '我关注的人总数',
  `receive_like_counts` int(11) NULL DEFAULT 0 COMMENT '我接受到的赞美/收藏 的数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('190724AYBKT0DYFW', 'skl', 'q2Tzn0rsWxBAZAhOjvToLQ==', '/190724AYBKT0DYFW/face/wx801df9ef2d4a0eee.o6zAJs4ZAwvf_9bKEBrwvNb7QLEc.uMXuXLfhz6yx1660da1bea578a4d8da13364edcdd6ea.png', 'skl', 0, 1, 0);
INSERT INTO `users` VALUES ('190724B1WM37X1P0', 'skl2', 'LLOD0f3+nv8TmM2Mzieb+g==', '/190724B1WM37X1P0/face/wx801df9ef2d4a0eee.o6zAJs4ZAwvf_9bKEBrwvNb7QLEc.2rCE6SftCT26d936357963cc2e461991b83c3698b5d1.png', 'skl2', 1, 0, 1);
INSERT INTO `users` VALUES ('190724B5A0CRZGHH', 'shou', 'oKAM7kocFcjiBQ15PP+oLQ==', '/190724B5A0CRZGHH/face/tmp_7f282dab55f4eaa05d4e7102855a69ef83e83b3aa037c16f.jpg', 'shou', 0, 0, 0);

-- ----------------------------
-- Table structure for users_fans
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `fan_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `fan_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户粉丝关联关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('190724B4CDNPC0ZC', '190724B1WM37X1P0', '190724AYBKT0DYFW');

-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_video_rel`(`user_id`, `video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户喜欢的/赞过的视频' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------
INSERT INTO `users_like_videos` VALUES ('190724B4ADG93B0H', '190724AYBKT0DYFW', '190724B2WD65AW00');

-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `deal_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型标题，让用户选择，详情见 枚举',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报人的id',
  `create_date` datetime(0) NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '举报用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者id',
  `audio_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6, 2) NULL DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) NULL DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) NULL DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT 0 COMMENT '喜欢/赞美的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：\r\n1、发布成功\r\n2、禁止播放，管理员操作',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('190722HHZD3GN540', '190724AYBKT0DYFW', '1', '121212', '/190707H4HH45HXS8/video/4213efbe-d1d9-4364-9108-46235b856211.mp4', 10.03, 1080, 1920, '/190707H4HH45HXS8/video/wx801df9ef2d4a0eeeo6zAJs4ZAwvf_9bKEBrwvNb7QLEcOb1em5Ke8IkU917c54ca5c555820a54287e77ff78675.jpg', 0, 1, '2019-07-22 23:16:06');
INSERT INTO `videos` VALUES ('190724B2WD65AW00', '190724B1WM37X1P0', '1', 'java', '/190724B1WM37X1P0/video/d03359db-7189-4399-b6b9-f508c6b9a376.mp4', 9.28, 1080, 1920, '/190724B1WM37X1P0/video/wx801df9ef2d4a0eeeo6zAJs4ZAwvf_9bKEBrwvNb7QLEcq0FJLy2g0sBg1b912fba797d4e4304851d817db277b8.jpg', 1, 1, '2019-07-24 15:33:24');
INSERT INTO `videos` VALUES ('190724B427407TC0', '190724AYBKT0DYFW', '2', 'js', '/190724AYBKT0DYFW/video/4a7b4ae3-f23b-4a17-8295-a07b2d0c64b4.mp4', 10.08, 464, 960, '/190724AYBKT0DYFW/video/wx801df9ef2d4a0eeeo6zAJs4ZAwvf_9bKEBrwvNb7QLEcRn96JVJupPmT6aab49b4b62d48f5a8554ec606f2d0bd.jpg', 0, 1, '2019-07-24 15:37:01');
INSERT INTO `videos` VALUES ('190724B5KS6P7WPH', '190724B5A0CRZGHH', '2', 'shou', '/190724B5A0CRZGHH/video/80a84af6-deda-4096-aad1-cd5fcec70ddf.mp4', 10.00, 464, 960, '/190724B5A0CRZGHH/video/tmp_229272ff3d6b8e57f14c66126894d532d60180d1e63b45d8.jpg', 0, 1, '2019-07-24 15:41:41');

SET FOREIGN_KEY_CHECKS = 1;

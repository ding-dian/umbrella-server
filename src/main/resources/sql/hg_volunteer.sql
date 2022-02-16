/*
 Navicat Premium Data Transfer

 Source Server         : docker_47.101.223.63_13457
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 47.101.223.63:13457
 Source Schema         : hg_volunteer

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 15/02/2022 10:59:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `roles` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份',
  `deleted` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '【0】未删除 【1】已删除',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of admin_info
-- ----------------------------
INSERT INTO `admin_info` VALUES (1, 'admin', 'w+NE4VVl702a6mE4n57ZsA==', '', '', '', '0', '');
INSERT INTO `admin_info` VALUES (2, 'admininfo', 'DYIQrni1XyB6cnlyR49vzw==', '123456', '管理员', '管理员', '0', '这个一个管理员账号');

-- ----------------------------
-- Table structure for sign_up_record
-- ----------------------------
DROP TABLE IF EXISTS `sign_up_record`;
CREATE TABLE `sign_up_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报名记录ID',
  `volunteer_id` int(11) NOT NULL COMMENT '志愿者ID',
  `volunteer_activity_id` int(11) NOT NULL COMMENT '活动ID',
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除【0:未删除，1:已删除】',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_sign_in` tinyint(4) NULL DEFAULT NULL COMMENT '是否签到【0:未签到，1:已签到】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sign_up_record
-- ----------------------------
INSERT INTO `sign_up_record` VALUES (1, 1, 7, 0, NULL, NULL);
INSERT INTO `sign_up_record` VALUES (7, 97, 18, 0, '2021-12-25 17:15:28', NULL);
INSERT INTO `sign_up_record` VALUES (12, 102, 18, 0, '2021-12-25 19:30:20', NULL);
INSERT INTO `sign_up_record` VALUES (13, 107, 18, 0, '2021-12-25 20:49:13', NULL);
INSERT INTO `sign_up_record` VALUES (14, 108, 18, 0, '2021-12-25 21:04:23', NULL);
INSERT INTO `sign_up_record` VALUES (15, 111, 18, 0, '2021-12-25 22:03:58', NULL);
INSERT INTO `sign_up_record` VALUES (16, 111, 15, 0, '2022-01-08 22:15:27', NULL);
INSERT INTO `sign_up_record` VALUES (17, 112, 18, 0, '2022-01-19 23:45:14', NULL);
INSERT INTO `sign_up_record` VALUES (18, 115, 18, 0, '2022-01-19 23:57:43', NULL);
INSERT INTO `sign_up_record` VALUES (19, 115, 17, 0, '2022-01-25 18:09:00', NULL);
INSERT INTO `sign_up_record` VALUES (20, 115, 11, 0, '2022-02-04 23:04:28', NULL);
INSERT INTO `sign_up_record` VALUES (21, 115, 10, 0, '2022-02-04 23:04:59', NULL);

-- ----------------------------
-- Table structure for umbrella
-- ----------------------------
DROP TABLE IF EXISTS `umbrella`;
CREATE TABLE `umbrella`  (
  `id` int(11) NOT NULL COMMENT '雨伞编号',
  `openID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前借取人的微信小程序openID',
  `borrowDate` datetime NULL DEFAULT NULL COMMENT '用户借取雨伞的时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '雨伞在库状态，0表示在库，1表示借出',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对雨伞的描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of umbrella
-- ----------------------------

-- ----------------------------
-- Table structure for umbrella_history_borrow
-- ----------------------------
DROP TABLE IF EXISTS `umbrella_history_borrow`;
CREATE TABLE `umbrella_history_borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信提供给用户的唯一凭证',
  `borrow_date` datetime NULL DEFAULT NULL COMMENT '用户借取雨伞的时间',
  `return_date` datetime NULL DEFAULT NULL COMMENT '用户归还雨伞的时间',
  `borrow_durations` decimal(6, 1) NULL DEFAULT NULL COMMENT '用户借伞时间间隔',
  `borrow_status` int(1) NULL DEFAULT NULL COMMENT '借伞归还状态 0表示已归还/1表示未归还',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of umbrella_history_borrow
-- ----------------------------
INSERT INTO `umbrella_history_borrow` VALUES (1, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:09:48', '2022-02-09 00:10:09', 0.0, 0);
INSERT INTO `umbrella_history_borrow` VALUES (2, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:09:48', '2022-02-09 00:10:09', 0.0, 0);
INSERT INTO `umbrella_history_borrow` VALUES (5, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:09:48', '2022-02-09 00:10:09', 0.0, 0);
INSERT INTO `umbrella_history_borrow` VALUES (6, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:09:48', '2022-02-09 00:10:09', 0.0, 0);
INSERT INTO `umbrella_history_borrow` VALUES (7, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:25:58', '2022-02-09 00:30:56', 4965.5, 0);
INSERT INTO `umbrella_history_borrow` VALUES (8, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:36:08', '2022-02-09 00:36:42', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (9, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-09 00:41:02', '2022-02-10 00:29:33', 24.0, 1);
INSERT INTO `umbrella_history_borrow` VALUES (10, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-10 00:29:36', '2022-02-11 18:38:43', 42.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (11, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-11 18:39:30', '2022-02-12 14:56:32', 20.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (12, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-12 14:56:43', '2022-02-13 19:55:13', 29.0, 1);
INSERT INTO `umbrella_history_borrow` VALUES (13, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 10:19:19', '2022-02-14 10:46:52', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (14, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 10:55:39', '2022-02-14 11:00:08', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (15, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 22:00:42', '2022-02-14 22:01:52', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (16, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 22:01:54', '2022-02-14 22:01:57', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (17, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 22:01:59', '2022-02-14 22:02:02', 0.5, 1);
INSERT INTO `umbrella_history_borrow` VALUES (18, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '2022-02-14 22:02:32', '2022-02-14 22:02:37', 0.5, 1);

-- ----------------------------
-- Table structure for volunteer
-- ----------------------------
DROP TABLE IF EXISTS `volunteer`;
CREATE TABLE `volunteer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '志愿者ID',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序端唯一ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone_number` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `student_id` bigint(11) NULL DEFAULT NULL COMMENT '学号',
  `qq_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qq号码',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `institude` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院',
  `grade` int(11) NULL DEFAULT NULL COMMENT '年级',
  `major` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业',
  `points` int(11) NULL DEFAULT NULL COMMENT '积分',
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除【0:未删除，1:已删除】',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` int(11) NULL DEFAULT NULL COMMENT '性别【0:未知,1:男,2:女】',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `contact_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_openId`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of volunteer
-- ----------------------------
INSERT INTO `volunteer` VALUES (1, NULL, '测试12', '11111111111', '1111@qq.com', 3333, NULL, 'CQkzHeHdLlbzC34AnpqHmw==', '计信学院', 18, '网工', 110, 0, '2021-11-07 21:03:46', '111', '2021-11-07 21:03:54', '111', NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (5, NULL, '708', NULL, NULL, NULL, NULL, 'CQkzHeHdLlbzC34AnpqHmw==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (6, NULL, '427', NULL, NULL, NULL, NULL, 'Z7sTjYel4dW7Uwhb+fdNZw==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (7, NULL, '333', NULL, NULL, NULL, NULL, 'OiVNK0BgWWobaP52XPBFiQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (8, NULL, '467', NULL, NULL, NULL, NULL, 'l4JsoS3eQbpZ6Z2mu3LA2Q==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (9, NULL, '605', NULL, NULL, NULL, NULL, 'V8xWtfzhnUfBjeAqjTB+zg==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (10, NULL, '3', NULL, NULL, NULL, NULL, 'JXc2Y+vDUBCAW/Ix7G3HhQ==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (11, NULL, '972', NULL, NULL, NULL, NULL, 'YQd9LNPqiYpBZsZdXlFqDg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (12, NULL, '750', NULL, NULL, NULL, NULL, 'yWXh9SKHJbEJ/wW1e2v3Fw==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (13, NULL, '105', NULL, NULL, NULL, NULL, 'jdIJ3fFL8wnbWQ6fu5tYcw==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (14, NULL, '831', NULL, NULL, NULL, NULL, 'fnUgQKLR4zAytH+98mmmoQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (15, NULL, '723', NULL, NULL, NULL, NULL, 'U+8nGgQEP53xaFBwNDXq3Q==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (16, NULL, '616', NULL, NULL, NULL, NULL, '6W8BXFR42LOjg97ysN2LXQ==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (17, NULL, '300', NULL, NULL, NULL, NULL, 'VwuybRpgYLMP1NMcUdVyTg==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (18, NULL, '32', NULL, NULL, NULL, NULL, 'dtKisLlZ/Au2eQoSfUHWew==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (19, NULL, '732', NULL, NULL, NULL, NULL, 'uU4gShR+ZUlb5FL3FEMbdQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (21, NULL, '71', NULL, NULL, NULL, NULL, 'JOEl4qA+AhA/1e8lG/Ah/w==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (22, NULL, '726', NULL, NULL, NULL, NULL, 'OCGqhPaNlzy74vnaEMqW2Q==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (23, NULL, '560', NULL, NULL, NULL, NULL, '1Xs79lhxScDdCMVsmff++w==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (24, NULL, '436', NULL, NULL, NULL, NULL, 'GSmittpqlof/vr6BVLdE7Q==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (25, NULL, '845', NULL, NULL, NULL, NULL, 'RQU9rDzkVD6Pmdr8FI3GJQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (26, NULL, '813', NULL, NULL, NULL, NULL, 'DGZdcahsaDMTnMIzWbibtg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (27, NULL, '124', NULL, NULL, NULL, NULL, '9rYIN35wPEGbNeLit8sZGg==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:31', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (28, NULL, '璃妹妹', NULL, NULL, NULL, NULL, '1z7qAzRQbblk1v+pGa8Hxg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (29, NULL, '48', NULL, NULL, NULL, NULL, 'TBOhCiBfurjtzHOX5BbDeQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (30, NULL, '588', NULL, NULL, NULL, NULL, 'w/fQCRRQzCtzAuzV23WM9g==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (32, NULL, '252', NULL, NULL, NULL, NULL, '9GYNjyXMQgDaLhGV4KyBYw==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (33, NULL, '168', NULL, NULL, NULL, NULL, 'Twku+bQgHV5b6XvxKFJ7sA==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (34, NULL, '744', NULL, NULL, NULL, NULL, 'T0+Qt5GY+4TAzgNy75Xr6A==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (35, NULL, '168', NULL, NULL, NULL, NULL, 'lyfBPppT87WRCQUrDCMNng==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (36, NULL, '858', NULL, NULL, NULL, NULL, 'AG+lT01Bks9LnwOdllpKSA==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (37, NULL, '346', NULL, NULL, NULL, NULL, 'mKO4nZPoSpVfUC8ThZZjtg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (38, NULL, '271', NULL, NULL, NULL, NULL, 'Na6SZp4X0DIDEhiwEw0t0w==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (39, NULL, '435', NULL, NULL, NULL, NULL, 'XA6a7uStIZrXWEVwICBWWw==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (40, NULL, '555', NULL, NULL, NULL, NULL, 'YyhyXwNGLqmpfMkseP7neg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (41, NULL, '333', NULL, NULL, NULL, NULL, '39J8eeblaD5LOmDvNmXL5g==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (42, NULL, '511', NULL, NULL, NULL, NULL, 'IzbYEMeEnh3BUpWKFM5/Rw==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (43, NULL, '314', NULL, NULL, NULL, NULL, '/ts/+BBDJ6tn+0arcrTVqg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (44, NULL, '204', NULL, NULL, NULL, NULL, 'P9phs55cnw5GhmiMf2hlAg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (45, NULL, '166', NULL, NULL, NULL, NULL, 'lFq956qjqYzbJFaZAfYcmQ==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (46, NULL, '415', NULL, NULL, NULL, NULL, '8iyq0gSBglWW4d58c2rXVg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (47, NULL, '605', NULL, NULL, NULL, NULL, '4JxqoJ0EJQ/7fc98zjfSxA==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (48, NULL, '409', NULL, NULL, NULL, NULL, '645P9keNvQu7bP3qx8ce4Q==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (49, NULL, '560', NULL, NULL, NULL, NULL, 'CShMbQ/dk8ADnr9/AqW+YA==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (50, NULL, '0', NULL, NULL, NULL, NULL, 'GuJ6AFyInTN6Bu6hBaCeFw==', NULL, NULL, NULL, NULL, 0, '2021-11-17 12:32:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (51, NULL, '974', NULL, NULL, NULL, NULL, 'yfC7B1YfsCKaFK4QHT/K6A==', NULL, NULL, NULL, NULL, 1, '2021-11-17 12:32:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (52, NULL, '唐三藏', NULL, NULL, NULL, NULL, 'xR8wXpKxcJNyt90qHwJi9Q==', NULL, NULL, NULL, NULL, 1, '2021-11-17 18:40:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (53, NULL, '孙悟空', NULL, NULL, NULL, NULL, 'Y1uW/5lL0k4Q0H4nNHyodg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 18:43:47', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (54, NULL, '沙悟净', NULL, NULL, NULL, NULL, '6e4qSNhiiVfQEG5XeIJ5Eg==', NULL, NULL, NULL, NULL, 0, '2021-11-17 18:44:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (55, NULL, '刘姥姥', NULL, NULL, NULL, NULL, '10XNwOQm+E8HfiIwG9Uj7w==', NULL, NULL, NULL, NULL, 0, '2021-11-17 18:47:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (56, NULL, '小亚索', NULL, NULL, NULL, NULL, '10XNwOQm+E8HfiIwG9Uj7w==', NULL, NULL, NULL, NULL, 0, '2021-11-17 19:47:20', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (57, NULL, '阿木木', NULL, NULL, NULL, NULL, '10XNwOQm+E8HfiIwG9Uj7w==', NULL, NULL, NULL, NULL, 0, '2021-11-18 16:20:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (61, NULL, '123', NULL, NULL, NULL, NULL, '�`�ҳ��HR�Icc�&', NULL, NULL, NULL, NULL, 0, '2021-11-18 17:48:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (62, NULL, '白骨精', NULL, NULL, NULL, NULL, 'TuxJr/uPHEWmAUIVbtXAUw==', NULL, NULL, NULL, NULL, 0, '2021-11-18 20:13:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (63, NULL, '喜羊羊', NULL, NULL, NULL, NULL, 'G61C6Xq7aYC+FAMwdSjHHQ==', NULL, NULL, NULL, NULL, 0, '2021-11-18 20:31:28', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (64, NULL, '牛魔王', NULL, NULL, NULL, NULL, 'G61C6Xq7aYC+FAMwdSjHHQ==', NULL, NULL, NULL, NULL, 0, '2021-11-20 12:57:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (65, NULL, '测新增', '13999444444', '测新增', 13333333333, NULL, 'xR8wXpKxcJNyt90qHwJi9Q==', '测新增', NULL, '测新增', NULL, 1, '2021-11-24 11:11:22', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (66, NULL, '新增的', '13999999999', NULL, 13333333333, NULL, 'VLLyrw3SN38tmhlBNUhd4Q==', NULL, NULL, NULL, NULL, 1, '2021-11-24 12:09:23', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (67, NULL, '哪吒', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:17:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (68, NULL, '迪丽热巴', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:24:39', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (69, NULL, '言灵', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:31:34', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (70, NULL, '大哥大', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:37:49', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (71, NULL, '大哥大1', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:48:19', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (74, NULL, '大哥66', NULL, NULL, NULL, NULL, 'vJoXRBdljDPLh8PKiBJ9ZA==', NULL, NULL, NULL, NULL, 0, '2021-11-25 14:58:53', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (75, NULL, '佩奇', '123456', '232323', 456456, NULL, 'xR8wXpKxcJNyt90qHwJi9Q==', '计信', 2, '网络', NULL, 0, '2021-12-13 13:54:50', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `volunteer` VALUES (78, 'ozLwX5aDHI2DtkhZZWo7djNnEtls1', '何福任', '15367985268', 'vernhe@foxmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '生命中有太多不确定', 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132', NULL);
INSERT INTO `volunteer` VALUES (86, 'ozLwX5aDHI2DtkhZZWo7djNnEtls2', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-12-22 00:47:32', NULL, NULL, NULL, '生命中有太多不确定', 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132', NULL);
INSERT INTO `volunteer` VALUES (87, 'ozLwX5aDHI2DtkhZZWo7djNnEtls3', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-12-23 12:22:24', NULL, NULL, NULL, '生命中有太多不确定', 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132', NULL);
INSERT INTO `volunteer` VALUES (97, 'ozLwX5aDHI2DtkhZZWo7djNnEtls', NULL, 'Hh80ExCSFSKuLGzKKZ2Ld4l+ya9+JZD+pCRgOwvjtEM=', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-12-23 21:25:03', NULL, NULL, NULL, '生命中有太多不确定', 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eplWzPJuvFyWeY5KjG5mOv6a7YNHNDvyxCibSCv58iaxicjyjPD08FoicQDBibMoCF64urOjYFEicM5KTeQ/132', NULL);
INSERT INTO `volunteer` VALUES (115, 'oR83j4kkq2CyvVmuxl6znKbrWi2A', '梁峰源', 'oVBfcx4efaRhA4iQo+7BfWy6IijmcZFO4Ac8fjmAvS8=', NULL, 19020340131, '2062728920', 'fail', '计算机学院', NULL, '网络工程', NULL, 0, '2022-01-19 23:56:50', NULL, NULL, NULL, '奉先大大鸭', 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/hkGC2Cr2ZZdOaNrDbGKUuD3nrWYwp5nJSiaBQW70DUXibgqpvb9Hiar5euSPg0E8iaSk4ozwJAqu7KAd4DCLr9Cxpg/132', NULL);

-- ----------------------------
-- Table structure for volunteer_activity
-- ----------------------------
DROP TABLE IF EXISTS `volunteer_activity`;
CREATE TABLE `volunteer_activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '志愿活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动名称',
  `activity_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动图片',
  `activity_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动地点',
  `number_of_need` int(11) NOT NULL DEFAULT 0 COMMENT '活动所需人数',
  `number_of_attendees` int(11) NULL DEFAULT 0 COMMENT '活动参加人数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '活动描述',
  `reward_points` int(11) NOT NULL DEFAULT 5 COMMENT '奖励积分',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态【00:进行中，01:未开始，02:已结束】',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除【0:未删除，1:已删除】',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `is_audited` tinyint(4) NOT NULL DEFAULT 0 COMMENT '【0}:未审核 【1]:已审核',
  `predict_duration` double NOT NULL DEFAULT 0 COMMENT '预计志愿活动时长',
  `actual_duration` double NULL DEFAULT 0 COMMENT '实际志愿时长',
  `contact_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of volunteer_activity
-- ----------------------------
INSERT INTO `volunteer_activity` VALUES (1, '111', 'https://lfy-hg-volunteer.oss-cn-beijing.aliyuncs.com/volunteer/ab1ebb8e209e4c098488f3ba1beaf9a0.png', '111', 111, 10, '这是一次很有意思的活动', 10, '2021-11-07 00:00:00', '2021-11-17 00:00:00', '02', 1, '2021-11-07 00:00:00', '', '2022-02-06 19:41:50', NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (3, '第一次活动', 'https://img2.baidu.com/it/u=1814268193,3619863984&fm=253&fmt=auto&app=138&f=JPEG?w=632&h=500', '衡阳市', 10, 0, '', 5, '2021-11-21 00:00:00', '2021-12-21 00:00:00', '01', 1, '2021-11-10 14:35:14', '神仙佩奇', NULL, NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (4, '第二次活动', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳市', 10, 0, '', 5, '2021-11-21 00:00:00', '2021-12-21 00:00:00', '01', 1, '2021-11-29 14:35:18', '神仙佩奇', NULL, NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (5, '第二次活动', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳市', 10, 0, '', 5, '2021-11-21 00:00:00', '2021-12-21 00:00:00', '01', 1, '2021-11-02 14:35:23', '神仙佩奇', NULL, NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (6, '第二次活动', 'https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto', '衡阳市', 10, 0, '', 5, '2021-11-21 00:00:00', '2021-12-21 00:00:00', '02', 0, '2021-11-05 14:35:27', '神仙佩奇', NULL, NULL, 0, 0, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (7, '衡阳志愿者活动', 'https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto', '珠晖区湖南工学院', 15, 0, '这是一次很有意义的志愿者活动', 5, '2021-11-24 12:00:00', '2021-11-25 12:00:00', '02', 0, '2021-11-25 15:54:21', '神仙佩奇', NULL, NULL, 1, 3, 4, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (8, '衡阳志愿者活动', 'https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto', '珠晖区湖南工学院', 15, 0, '这是一次很有意义的志愿者活动', 5, '2021-11-27 12:00:00', '2021-12-27 12:00:00', '02', 0, '2021-11-25 15:56:01', '神仙佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (9, '衡阳志愿者活动', 'https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto', '珠晖区湖南工学院', 15, 0, '这是一次很有意义的志愿者活动', 5, '2021-11-27 12:00:00', '2021-12-27 12:00:00', '02', 0, '2021-11-25 15:57:51', '神仙佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (10, '衡阳志愿者活动', 'https://img1.baidu.com/it/u=3427821080,1838386749&fm=26&fmt=auto', '珠晖区湖南工学院', 15, 2, '这是一次很有意义的志愿者活动', 5, '2021-11-27 12:00:00', '2021-12-27 12:00:00', '02', 0, '2021-11-25 15:59:28', '神仙佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (11, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 1, '用于志愿者测试', 5, '2021-12-21 12:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:03', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (12, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 1, '用于志愿者测试', 5, '2021-12-22 12:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:11', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (13, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 0, '用于志愿者测试', 5, '2021-12-23 12:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:19', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (14, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 0, '用于志愿者测试', 5, '2021-12-22 13:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:30', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (15, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 1, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:36', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (16, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 1, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:38', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (17, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 1, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:38', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (18, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 8, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:39', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (19, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 0, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 0, '2021-12-16 20:46:39', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);
INSERT INTO `volunteer_activity` VALUES (20, '志愿者活动测试', 'https://img0.baidu.com/it/u=3671520250,4077278021&fm=26&fmt=auto', '衡阳', 20, 0, '用于志愿者测试', 5, '2021-12-22 11:00:00', '2022-12-29 12:00:00', '00', 1, '2021-12-16 20:46:39', '佩奇', NULL, NULL, 0, 3, 0, NULL, NULL);

-- ----------------------------
-- Table structure for volunteer_activity_comment
-- ----------------------------
DROP TABLE IF EXISTS `volunteer_activity_comment`;
CREATE TABLE `volunteer_activity_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `volunteer_id` int(11) NOT NULL COMMENT '志愿者id',
  `volunteer_activity_id` int(11) NOT NULL COMMENT '志愿者活动id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '【0】未删除 【1】删除',
  `comment_time` datetime NOT NULL COMMENT '评论时间',
  `thumb_up_number` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of volunteer_activity_comment
-- ----------------------------
INSERT INTO `volunteer_activity_comment` VALUES (1, 1, 1, '这次志愿活动真有意思', 1, '2021-12-11 20:45:48', 0);
INSERT INTO `volunteer_activity_comment` VALUES (2, 1, 1, '这次志愿活动真有意思', 0, '2021-12-11 20:55:05', 0);

-- ----------------------------
-- Table structure for volunteer_statistical_information
-- ----------------------------
DROP TABLE IF EXISTS `volunteer_statistical_information`;
CREATE TABLE `volunteer_statistical_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `volunteer_id` int(11) NOT NULL COMMENT '志愿者id',
  `volunteer_durations` decimal(10, 1) NOT NULL DEFAULT 0.0 COMMENT '志愿者总时长',
  `activity_numbers` int(11) NOT NULL DEFAULT 0 COMMENT '志愿者参加活动总次数',
  `umbrella_borrow_durations` decimal(11, 1) NULL DEFAULT NULL COMMENT '志愿者借伞总时长',
  `volunteer_points` int(11) NOT NULL DEFAULT 0 COMMENT '志愿者总积分',
  `create_at` datetime NOT NULL,
  `deleted` tinyint(20) NOT NULL DEFAULT 0 COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of volunteer_statistical_information
-- ----------------------------
INSERT INTO `volunteer_statistical_information` VALUES (1, 1, 4.5, 1, NULL, 5, '2021-11-25 14:02:04', 0);
INSERT INTO `volunteer_statistical_information` VALUES (2, 69, 0.0, 0, NULL, 0, '2021-11-25 14:31:34', 0);
INSERT INTO `volunteer_statistical_information` VALUES (3, 70, 0.0, 0, NULL, 0, '2021-11-25 14:37:49', 0);
INSERT INTO `volunteer_statistical_information` VALUES (4, 71, 0.0, 0, NULL, 0, '2021-11-25 14:48:19', 0);
INSERT INTO `volunteer_statistical_information` VALUES (7, 74, 0.0, 0, NULL, 0, '2021-11-25 14:58:53', 0);
INSERT INTO `volunteer_statistical_information` VALUES (8, 75, 0.0, 0, NULL, 0, '2021-12-13 13:54:50', 0);
INSERT INTO `volunteer_statistical_information` VALUES (9, 115, 4.5, 2, 5.0, 0, '2022-02-08 05:31:18', 0);

SET FOREIGN_KEY_CHECKS = 1;

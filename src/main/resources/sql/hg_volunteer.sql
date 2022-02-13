/*
 Navicat Premium Data Transfer

 Source Server         : VirtualBox志愿者数据库
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.56.102:3306
 Source Schema         : hg_volunteer

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 13/02/2022 18:44:31
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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

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
  `activity_number` int(11) NULL DEFAULT NULL COMMENT '参加志愿者活动次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_openId`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for volunteer_activity
-- ----------------------------
DROP TABLE IF EXISTS `volunteer_activity`;
CREATE TABLE `volunteer_activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '志愿活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `activity_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动图片',
  `activity_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动地点',
  `number_of_need` int(11) NULL DEFAULT 0 COMMENT '活动所需人数',
  `number_of_attendees` int(11) NULL DEFAULT 0 COMMENT '活动参加人数',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动描述',
  `reward_points` int(11) NULL DEFAULT 5 COMMENT '奖励积分',
  `start_time` datetime NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '活动结束时间',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态【00:进行中，01:未开始，02:已结束】',
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '逻辑删除【0:未删除，1:已删除】',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `is_audited` tinyint(4) NULL DEFAULT 0 COMMENT '【0}:未审核 【1]:已审核',
  `predict_duration` double NULL DEFAULT 0 COMMENT '预计志愿活动时长',
  `actual_duration` double NULL DEFAULT 0 COMMENT '实际志愿时长',
  `contact_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

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
-- Table structure for volunteer_statistical_information
-- ----------------------------
DROP TABLE IF EXISTS `volunteer_statistical_information`;
CREATE TABLE `volunteer_statistical_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `volunteer_id` int(11) NOT NULL COMMENT '志愿者id',
  `volunteer_durations` decimal(10, 0) NOT NULL DEFAULT 0 COMMENT '志愿者总时长',
  `volunteer_points` int(11) NOT NULL DEFAULT 0 COMMENT '志愿者总积分',
  `activity_numbers` int(11) NOT NULL DEFAULT 0 COMMENT '累计参加的活动数',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(20) NOT NULL DEFAULT 0 COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;

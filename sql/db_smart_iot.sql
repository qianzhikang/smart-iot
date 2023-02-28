/*
 Navicat Premium Data Transfer

 Source Server         : 毕设
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : 47.113.216.179:3306
 Source Schema         : db_smart_iot

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 28/02/2023 09:41:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `type_id` int NOT NULL COMMENT '设备类型',
  `device_num` int NOT NULL COMMENT '设备编号',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备端id（MQTT）',
  `device_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一用户名（MQTT）',
  `device_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一密码（MQTT）',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备主题',
  `device_status` int NOT NULL DEFAULT (1) COMMENT '设备状态：0:禁用，1:可用',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_device
-- ----------------------------
BEGIN;
INSERT INTO `t_device` (`id`, `type_id`, `device_num`, `device_name`, `client_id`, `device_username`, `device_password`, `topic`, `device_status`, `create_time`, `update_time`) VALUES (3, 2, 1, '筠竹311空调', 'AirCondition', 'ACManage', '666666', 'ac_topic', 1, '2023-02-27 15:24:24', '2023-02-27 15:24:24');
COMMIT;

-- ----------------------------
-- Table structure for t_device_room
-- ----------------------------
DROP TABLE IF EXISTS `t_device_room`;
CREATE TABLE `t_device_room` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '设备-房间关联id',
  `device_id` int NOT NULL COMMENT '设备编号',
  `room_id` int NOT NULL COMMENT '房间id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_device_room
-- ----------------------------
BEGIN;
INSERT INTO `t_device_room` (`id`, `device_id`, `room_id`, `create_time`, `update_time`) VALUES (3, 3, 1, '2023-02-27 15:24:24', '2023-02-27 15:24:24');
COMMIT;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组名',
  `owner_id` int NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_group
-- ----------------------------
BEGIN;
INSERT INTO `t_group` (`id`, `group_name`, `owner_id`, `create_time`, `update_time`) VALUES (2, 'qzk', 2, '2023-02-24 05:31:54', '2023-02-24 05:32:12');
INSERT INTO `t_group` (`id`, `group_name`, `owner_id`, `create_time`, `update_time`) VALUES (3, '筠竹311', 2, '2023-02-24 06:36:04', '2023-02-24 06:36:04');
INSERT INTO `t_group` (`id`, `group_name`, `owner_id`, `create_time`, `update_time`) VALUES (4, 'hello', 2, '2023-02-24 06:56:26', '2023-02-24 06:56:26');
INSERT INTO `t_group` (`id`, `group_name`, `owner_id`, `create_time`, `update_time`) VALUES (9, 'One', 2, '2023-02-24 09:50:37', '2023-02-24 09:50:37');
INSERT INTO `t_group` (`id`, `group_name`, `owner_id`, `create_time`, `update_time`) VALUES (10, '王东', 4, '2023-02-25 06:48:25', '2023-02-25 06:48:25');
COMMIT;

-- ----------------------------
-- Table structure for t_room
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '房间id',
  `group_id` int NOT NULL COMMENT '所属用户组id',
  `room_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房间名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_room
-- ----------------------------
BEGIN;
INSERT INTO `t_room` (`id`, `group_id`, `room_name`, `create_time`, `update_time`) VALUES (1, 2, 'qzk的房间测试', '2023-02-24 05:34:44', '2023-02-24 05:36:28');
INSERT INTO `t_room` (`id`, `group_id`, `room_name`, `create_time`, `update_time`) VALUES (2, 2, 'xx的房间', '2023-02-24 05:35:09', '2023-02-24 05:35:09');
INSERT INTO `t_room` (`id`, `group_id`, `room_name`, `create_time`, `update_time`) VALUES (3, 2, 'xxx的房间', '2023-02-24 05:35:14', '2023-02-24 05:35:14');
COMMIT;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  `create_time` datetime NOT NULL COMMENT '创建时间\n',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐',
  `gender` int NOT NULL DEFAULT '0' COMMENT '性别 0:男 1:女',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `birth` datetime DEFAULT NULL COMMENT '出生年月',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `name`, `phone`, `password`, `salt`, `gender`, `avatar`, `birth`, `create_time`, `update_time`) VALUES (2, 'qzk', '15962540975', 'N6J2PIauJvgweCYDq0NxTA==', 'z9UbxDYMcq96naIH', 0, 'http://dummyimage.com/100x100', '1999-10-07 00:00:00', '2023-02-20 16:01:44', '2023-02-20 16:01:44');
INSERT INTO `t_user` (`id`, `name`, `phone`, `password`, `salt`, `gender`, `avatar`, `birth`, `create_time`, `update_time`) VALUES (3, 'qzk', '17368359293', 'it2vcQP/NgR9KmEoRQLX5A==', 'L3jL7kiN9D7c88R5', 0, 'http://dummyimage.com/100x100', '2022-11-10 16:00:00', '2023-02-24 03:41:14', '2023-02-24 03:41:14');
INSERT INTO `t_user` (`id`, `name`, `phone`, `password`, `salt`, `gender`, `avatar`, `birth`, `create_time`, `update_time`) VALUES (4, '17314432233', '17314432233', 'XANiJEul/DSBoml7PuTPJA==', 'yfn?4gz<X.gLx5Ay', 0, 'http://dummyimage.com/100x100', '2022-11-10 16:00:00', '2023-02-24 03:43:40', '2023-02-24 03:43:40');
INSERT INTO `t_user` (`id`, `name`, `phone`, `password`, `salt`, `gender`, `avatar`, `birth`, `create_time`, `update_time`) VALUES (5, '小丽', '13776147804', 'J4JrFjhczXU6oDbqu9qWwA==', 'lA,YRI1ppI<P,aRC', 0, 'http://dummyimage.com/100x100', '1999-10-07 00:00:00', '2023-02-27 09:43:25', '2023-02-27 09:43:25');
COMMIT;

-- ----------------------------
-- Table structure for t_user_group
-- ----------------------------
DROP TABLE IF EXISTS `t_user_group`;
CREATE TABLE `t_user_group` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户-组关联id',
  `group_id` int NOT NULL COMMENT '组id',
  `user_id` int NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_user_group
-- ----------------------------
BEGIN;
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (2, 2, 2, '2023-02-24 05:31:54', '2023-02-24 05:31:54');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (4, 3, 2, '2023-02-24 06:36:05', '2023-02-24 06:36:05');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (5, 4, 2, '2023-02-24 06:56:26', '2023-02-24 06:56:26');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (12, 2, 3, '2023-02-24 08:45:05', '2023-02-24 08:45:05');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (14, 9, 2, '2023-02-24 09:50:37', '2023-02-24 09:50:37');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (15, 2, 4, '2023-02-25 06:40:45', '2023-02-25 06:40:45');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (16, 10, 4, '2023-02-25 06:48:25', '2023-02-25 06:48:25');
INSERT INTO `t_user_group` (`id`, `group_id`, `user_id`, `create_time`, `update_time`) VALUES (17, 10, 2, '2023-02-25 06:50:16', '2023-02-25 06:50:16');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

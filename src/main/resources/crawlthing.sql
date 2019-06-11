/*
 Navicat Premium Data Transfer

 Source Server         : crawlthing
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : crawlthing

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 11/06/2019 14:05:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comicinfo
-- ----------------------------
DROP TABLE IF EXISTS `comicinfo`;
CREATE TABLE `comicinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comicname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '漫画名称',
  `comicchapter` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '漫画第几章',
  `comicpage` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '漫画第几页',
  `comicimg` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '漫画图片URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;

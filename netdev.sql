/*
 Navicat Premium Data Transfer

 Source Server         : AWSRoot
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 127.0.0.1:3306
 Source Schema         : netdev

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 18/10/2020 03:25:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `product_id` int(11) NOT NULL,
  `user_id` int(255) NOT NULL,
  `product_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`, `product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (4, 1, 11);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名',
  `descrption` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (7, 'IMAGE_UPLOAD', '上传图片');
INSERT INTO `permission` VALUES (87, 'ADD_PRODUCT', '增加商品的权限');
INSERT INTO `permission` VALUES (88, 'DELETE_PRODUCT', '删除商品的权限');
INSERT INTO `permission` VALUES (89, 'PRODUCT_UPDATE', '修改商品的权限');
INSERT INTO `permission` VALUES (90, 'GET_TRACE', '拿用户的浏览轨迹');
INSERT INTO `permission` VALUES (91, 'GET_ALL_USERS_MONEY', '取得所有用户的余额');
INSERT INTO `permission` VALUES (92, 'UPDATE_USER_MONEY', '更新用户的余额数量');

-- ----------------------------
-- Table structure for porder
-- ----------------------------
DROP TABLE IF EXISTS `porder`;
CREATE TABLE `porder`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `order_status` int(255) DEFAULT NULL,
  `product_num` int(11) DEFAULT NULL,
  `product_money` int(255) DEFAULT NULL,
  `total_money` int(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of porder
-- ----------------------------
INSERT INTO `porder` VALUES (11, 270, 5, 1, 3, 666, 1998);
INSERT INTO `porder` VALUES (12, 270, 4, 1, 1, 999, 999);
INSERT INTO `porder` VALUES (13, 270, 6, 1, 1, 999, 999);
INSERT INTO `porder` VALUES (14, 270, 4, 0, 1, 999, 999);
INSERT INTO `porder` VALUES (15, 270, 6, 0, 2, 999, 1998);
INSERT INTO `porder` VALUES (16, 270, 4, 0, 1, 999, 999);
INSERT INTO `porder` VALUES (17, 270, 4, 1, 1, 999, 999);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `product_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type_name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `product_num` int(11) DEFAULT NULL,
  `product_money` int(255) DEFAULT NULL,
  `product_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleted` int(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '小米手机6d', '这是用于测试的伪造数据', '手机', 50, 666, '', 1);
INSERT INTO `product` VALUES (2, '魅族6d', '魅族手机测试', '手机', 300, 3000, '', 1);
INSERT INTO `product` VALUES (3, '一加6', '一加手机测试', '手机', 123, 777, '', 1);
INSERT INTO `product` VALUES (4, 'VIVO IQOO', 'vivo手机商品测试', '手机', 770, 999, '36cd7c77-6d5f-49ab-9a5c-4bf23ff7c5e7.jpg', 0);
INSERT INTO `product` VALUES (5, '小米电视prod', '小米电视', '电视', 765, 666, 'a3b369fc-749f-4d14-8dd9-93f9c178376d.jpg', 0);
INSERT INTO `product` VALUES (6, '华为电视53', '华为的电视', '电视', 774, 999, 'ea1325bb-549d-438d-8aed-a13e7cbbe6ca.jpg', 0);

-- ----------------------------
-- Table structure for ptype
-- ----------------------------
DROP TABLE IF EXISTS `ptype`;
CREATE TABLE `ptype`  (
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'STUDENT', '学生');
INSERT INTO `role` VALUES (2, 'TEACHER', '教师');
INSERT INTO `role` VALUES (3, 'ADMIN', '管理员');
INSERT INTO `role` VALUES (4, 'SCHOOL', '学校');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL COMMENT '自增主键（此处为多对多）',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (0, 3, 14);
INSERT INTO `role_permission` VALUES (1, 1, 7);
INSERT INTO `role_permission` VALUES (2, 1, 12);
INSERT INTO `role_permission` VALUES (3, 1, 13);
INSERT INTO `role_permission` VALUES (4, 2, 1);
INSERT INTO `role_permission` VALUES (5, 2, 3);
INSERT INTO `role_permission` VALUES (6, 2, 7);
INSERT INTO `role_permission` VALUES (7, 2, 9);
INSERT INTO `role_permission` VALUES (8, 2, 12);
INSERT INTO `role_permission` VALUES (9, 3, 1);
INSERT INTO `role_permission` VALUES (10, 3, 2);
INSERT INTO `role_permission` VALUES (11, 3, 3);
INSERT INTO `role_permission` VALUES (12, 3, 4);
INSERT INTO `role_permission` VALUES (13, 3, 5);
INSERT INTO `role_permission` VALUES (14, 3, 6);
INSERT INTO `role_permission` VALUES (15, 3, 7);
INSERT INTO `role_permission` VALUES (16, 3, 8);
INSERT INTO `role_permission` VALUES (17, 3, 9);
INSERT INTO `role_permission` VALUES (18, 3, 10);
INSERT INTO `role_permission` VALUES (19, 3, 11);
INSERT INTO `role_permission` VALUES (21, 3, 24);
INSERT INTO `role_permission` VALUES (23, 3, 25);
INSERT INTO `role_permission` VALUES (24, 1, 26);
INSERT INTO `role_permission` VALUES (25, 2, 26);
INSERT INTO `role_permission` VALUES (26, 3, 26);
INSERT INTO `role_permission` VALUES (28, 3, 27);
INSERT INTO `role_permission` VALUES (29, 1, 28);
INSERT INTO `role_permission` VALUES (30, 2, 28);
INSERT INTO `role_permission` VALUES (31, 3, 28);
INSERT INTO `role_permission` VALUES (33, 3, 29);
INSERT INTO `role_permission` VALUES (34, 1, 30);
INSERT INTO `role_permission` VALUES (35, 3, 31);
INSERT INTO `role_permission` VALUES (36, 1, 32);
INSERT INTO `role_permission` VALUES (37, 2, 32);
INSERT INTO `role_permission` VALUES (38, 3, 32);
INSERT INTO `role_permission` VALUES (39, 1, 33);
INSERT INTO `role_permission` VALUES (40, 2, 33);
INSERT INTO `role_permission` VALUES (41, 3, 33);
INSERT INTO `role_permission` VALUES (42, 2, 34);
INSERT INTO `role_permission` VALUES (43, 3, 34);
INSERT INTO `role_permission` VALUES (44, 2, 35);
INSERT INTO `role_permission` VALUES (45, 3, 35);
INSERT INTO `role_permission` VALUES (46, 1, 36);
INSERT INTO `role_permission` VALUES (47, 2, 36);
INSERT INTO `role_permission` VALUES (48, 3, 36);
INSERT INTO `role_permission` VALUES (49, 1, 37);
INSERT INTO `role_permission` VALUES (50, 3, 37);
INSERT INTO `role_permission` VALUES (51, 2, 38);
INSERT INTO `role_permission` VALUES (52, 3, 38);
INSERT INTO `role_permission` VALUES (53, 1, 39);
INSERT INTO `role_permission` VALUES (54, 3, 39);
INSERT INTO `role_permission` VALUES (55, 1, 40);
INSERT INTO `role_permission` VALUES (56, 3, 40);
INSERT INTO `role_permission` VALUES (57, 1, 41);
INSERT INTO `role_permission` VALUES (58, 3, 41);
INSERT INTO `role_permission` VALUES (59, 2, 42);
INSERT INTO `role_permission` VALUES (60, 3, 42);
INSERT INTO `role_permission` VALUES (61, 2, 25);
INSERT INTO `role_permission` VALUES (62, 2, 29);
INSERT INTO `role_permission` VALUES (63, 1, 23);
INSERT INTO `role_permission` VALUES (64, 2, 23);
INSERT INTO `role_permission` VALUES (65, 3, 23);
INSERT INTO `role_permission` VALUES (66, 3, 16);
INSERT INTO `role_permission` VALUES (67, 1, 15);
INSERT INTO `role_permission` VALUES (68, 1, 43);
INSERT INTO `role_permission` VALUES (69, 1, 44);
INSERT INTO `role_permission` VALUES (70, 2, 14);
INSERT INTO `role_permission` VALUES (71, 2, 17);
INSERT INTO `role_permission` VALUES (72, 3, 17);
INSERT INTO `role_permission` VALUES (73, 2, 18);
INSERT INTO `role_permission` VALUES (74, 3, 18);
INSERT INTO `role_permission` VALUES (75, 1, 19);
INSERT INTO `role_permission` VALUES (76, 2, 20);
INSERT INTO `role_permission` VALUES (77, 2, 21);
INSERT INTO `role_permission` VALUES (78, 3, 21);
INSERT INTO `role_permission` VALUES (79, 2, 22);
INSERT INTO `role_permission` VALUES (80, 2, 46);
INSERT INTO `role_permission` VALUES (81, 3, 46);
INSERT INTO `role_permission` VALUES (82, 1, 21);
INSERT INTO `role_permission` VALUES (83, 3, 22);
INSERT INTO `role_permission` VALUES (84, 1, 23);
INSERT INTO `role_permission` VALUES (85, 2, 23);
INSERT INTO `role_permission` VALUES (86, 3, 23);
INSERT INTO `role_permission` VALUES (87, 3, 47);
INSERT INTO `role_permission` VALUES (88, 1, 48);
INSERT INTO `role_permission` VALUES (89, 2, 48);
INSERT INTO `role_permission` VALUES (90, 3, 48);
INSERT INTO `role_permission` VALUES (91, 1, 49);
INSERT INTO `role_permission` VALUES (92, 2, 49);
INSERT INTO `role_permission` VALUES (93, 3, 49);
INSERT INTO `role_permission` VALUES (94, 1, 50);
INSERT INTO `role_permission` VALUES (95, 2, 50);
INSERT INTO `role_permission` VALUES (96, 3, 50);
INSERT INTO `role_permission` VALUES (97, 2, 51);
INSERT INTO `role_permission` VALUES (98, 3, 51);
INSERT INTO `role_permission` VALUES (99, 1, 52);
INSERT INTO `role_permission` VALUES (101, 2, 53);
INSERT INTO `role_permission` VALUES (102, 2, 54);
INSERT INTO `role_permission` VALUES (103, 3, 54);
INSERT INTO `role_permission` VALUES (104, 2, 15);
INSERT INTO `role_permission` VALUES (105, 3, 15);
INSERT INTO `role_permission` VALUES (106, 2, 55);
INSERT INTO `role_permission` VALUES (107, 3, 55);
INSERT INTO `role_permission` VALUES (108, 2, 24);
INSERT INTO `role_permission` VALUES (109, 2, 27);
INSERT INTO `role_permission` VALUES (110, 3, 53);
INSERT INTO `role_permission` VALUES (111, 3, 20);
INSERT INTO `role_permission` VALUES (112, 3, 57);
INSERT INTO `role_permission` VALUES (113, 2, 57);
INSERT INTO `role_permission` VALUES (114, 1, 57);
INSERT INTO `role_permission` VALUES (115, 2, 58);
INSERT INTO `role_permission` VALUES (116, 3, 58);
INSERT INTO `role_permission` VALUES (117, 2, 59);
INSERT INTO `role_permission` VALUES (118, 3, 59);
INSERT INTO `role_permission` VALUES (119, 1, 59);
INSERT INTO `role_permission` VALUES (120, 2, 60);
INSERT INTO `role_permission` VALUES (121, 3, 60);
INSERT INTO `role_permission` VALUES (122, 1, 56);
INSERT INTO `role_permission` VALUES (123, 2, 56);
INSERT INTO `role_permission` VALUES (124, 3, 56);
INSERT INTO `role_permission` VALUES (125, 1, 61);
INSERT INTO `role_permission` VALUES (126, 2, 61);
INSERT INTO `role_permission` VALUES (127, 3, 61);
INSERT INTO `role_permission` VALUES (128, 2, 62);
INSERT INTO `role_permission` VALUES (129, 3, 62);
INSERT INTO `role_permission` VALUES (130, 2, 63);
INSERT INTO `role_permission` VALUES (131, 3, 63);
INSERT INTO `role_permission` VALUES (132, 1, 64);
INSERT INTO `role_permission` VALUES (133, 2, 64);
INSERT INTO `role_permission` VALUES (134, 3, 64);
INSERT INTO `role_permission` VALUES (135, 1, 65);
INSERT INTO `role_permission` VALUES (136, 2, 65);
INSERT INTO `role_permission` VALUES (137, 3, 65);
INSERT INTO `role_permission` VALUES (138, 2, 66);
INSERT INTO `role_permission` VALUES (139, 3, 66);
INSERT INTO `role_permission` VALUES (140, 2, 5);
INSERT INTO `role_permission` VALUES (150, 2, 67);
INSERT INTO `role_permission` VALUES (151, 3, 67);
INSERT INTO `role_permission` VALUES (152, 2, 68);
INSERT INTO `role_permission` VALUES (153, 3, 68);
INSERT INTO `role_permission` VALUES (154, 2, 69);
INSERT INTO `role_permission` VALUES (155, 3, 69);
INSERT INTO `role_permission` VALUES (156, 2, 70);
INSERT INTO `role_permission` VALUES (157, 3, 70);
INSERT INTO `role_permission` VALUES (158, 2, 71);
INSERT INTO `role_permission` VALUES (159, 3, 71);
INSERT INTO `role_permission` VALUES (160, 2, 72);
INSERT INTO `role_permission` VALUES (161, 3, 72);
INSERT INTO `role_permission` VALUES (162, 2, 73);
INSERT INTO `role_permission` VALUES (163, 3, 73);
INSERT INTO `role_permission` VALUES (164, 2, 74);
INSERT INTO `role_permission` VALUES (165, 3, 74);
INSERT INTO `role_permission` VALUES (166, 1, 75);
INSERT INTO `role_permission` VALUES (167, 1, 76);
INSERT INTO `role_permission` VALUES (168, 2, 77);
INSERT INTO `role_permission` VALUES (169, 3, 77);
INSERT INTO `role_permission` VALUES (170, 1, 78);
INSERT INTO `role_permission` VALUES (171, 1, 79);
INSERT INTO `role_permission` VALUES (172, 2, 79);
INSERT INTO `role_permission` VALUES (173, 3, 79);
INSERT INTO `role_permission` VALUES (174, 2, 80);
INSERT INTO `role_permission` VALUES (175, 3, 80);
INSERT INTO `role_permission` VALUES (176, 2, 13);
INSERT INTO `role_permission` VALUES (177, 3, 13);
INSERT INTO `role_permission` VALUES (178, 3, 80);
INSERT INTO `role_permission` VALUES (179, 3, 14);
INSERT INTO `role_permission` VALUES (188, 1, 86);
INSERT INTO `role_permission` VALUES (189, 2, 86);
INSERT INTO `role_permission` VALUES (190, 3, 86);
INSERT INTO `role_permission` VALUES (191, 4, 86);
INSERT INTO `role_permission` VALUES (192, 1, 81);
INSERT INTO `role_permission` VALUES (193, 2, 81);
INSERT INTO `role_permission` VALUES (194, 3, 81);
INSERT INTO `role_permission` VALUES (195, 4, 81);
INSERT INTO `role_permission` VALUES (196, 2, 82);
INSERT INTO `role_permission` VALUES (197, 3, 82);
INSERT INTO `role_permission` VALUES (198, 4, 82);
INSERT INTO `role_permission` VALUES (199, 2, 84);
INSERT INTO `role_permission` VALUES (200, 3, 84);
INSERT INTO `role_permission` VALUES (201, 4, 84);
INSERT INTO `role_permission` VALUES (202, 2, 83);
INSERT INTO `role_permission` VALUES (203, 3, 83);
INSERT INTO `role_permission` VALUES (204, 4, 83);
INSERT INTO `role_permission` VALUES (205, 2, 85);
INSERT INTO `role_permission` VALUES (206, 3, 85);
INSERT INTO `role_permission` VALUES (207, 4, 85);
INSERT INTO `role_permission` VALUES (208, 3, 87);
INSERT INTO `role_permission` VALUES (209, 3, 88);
INSERT INTO `role_permission` VALUES (210, 3, 89);
INSERT INTO `role_permission` VALUES (211, 3, 90);
INSERT INTO `role_permission` VALUES (212, 3, 91);
INSERT INTO `role_permission` VALUES (213, 3, 92);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '111' COMMENT '头像文件名（不要带域名）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `sex` int(1) DEFAULT 0 COMMENT '性别（0-未知、1-男、2-女）',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号（学号、工号）',
  `profess` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学院',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 272 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '111', '管理员', 0, '0000', '学校管理员', '');
INSERT INTO `user` VALUES (270, '111', NULL, 0, 'huyunhaode', NULL, '');
INSERT INTO `user` VALUES (271, '111', NULL, 0, 'huyunhaoge', NULL, '');

-- ----------------------------
-- Table structure for user_email
-- ----------------------------
DROP TABLE IF EXISTS `user_email`;
CREATE TABLE `user_email`  (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `activate_status` int(11) DEFAULT 0,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_email
-- ----------------------------
INSERT INTO `user_email` VALUES (1, 'huyunhao124624@2980.com', 1);
INSERT INTO `user_email` VALUES (270, '2640558211@qq.com', 1);
INSERT INTO `user_email` VALUES (271, '592593531@qq.com', 0);

-- ----------------------------
-- Table structure for user_money
-- ----------------------------
DROP TABLE IF EXISTS `user_money`;
CREATE TABLE `user_money`  (
  `money` int(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_money
-- ----------------------------
INSERT INTO `user_money` VALUES (66667, 1);
INSERT INTO `user_money` VALUES (7735, 270);
INSERT INTO `user_money` VALUES (93455, 271);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID（此处为一对多）',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 272 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 3, 'admin');
INSERT INTO `user_role` VALUES (270, 1, 'zx124624');
INSERT INTO `user_role` VALUES (271, 1, 'zx124624');

-- ----------------------------
-- Table structure for user_trace
-- ----------------------------
DROP TABLE IF EXISTS `user_trace`;
CREATE TABLE `user_trace`  (
  `user_id` int(11) NOT NULL,
  `trace_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`trace_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_trace
-- ----------------------------
INSERT INTO `user_trace` VALUES (1, 1, 4);
INSERT INTO `user_trace` VALUES (1, 2, 4);
INSERT INTO `user_trace` VALUES (1, 3, 4);
INSERT INTO `user_trace` VALUES (1, 4, 5);
INSERT INTO `user_trace` VALUES (1, 5, 4);
INSERT INTO `user_trace` VALUES (270, 6, 4);
INSERT INTO `user_trace` VALUES (270, 7, 5);
INSERT INTO `user_trace` VALUES (270, 8, 4);
INSERT INTO `user_trace` VALUES (270, 9, 5);
INSERT INTO `user_trace` VALUES (270, 10, 4);
INSERT INTO `user_trace` VALUES (270, 11, 6);
INSERT INTO `user_trace` VALUES (270, 12, 6);
INSERT INTO `user_trace` VALUES (270, 13, 4);

SET FOREIGN_KEY_CHECKS = 1;

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

 Date: 16/10/2020 02:12:42
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
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (7, 'IMAGE_UPLOAD', '上传图片');
INSERT INTO `permission` VALUES (87, 'ADD_PRODUCT', '增加商品的权限');
INSERT INTO `permission` VALUES (88, 'DELETE_PRODUCT', '删除商品的权限');
INSERT INTO `permission` VALUES (89, 'PRODUCT_UPDATE', '修改商品的权限');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of porder
-- ----------------------------
INSERT INTO `porder` VALUES (3, 1, 4, 0, 2, 666, 1332);
INSERT INTO `porder` VALUES (4, 1, 5, 0, 2, 666, 1332);

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
INSERT INTO `product` VALUES (4, 'VIVO IQOO', 'vivo手机商品测试', '手机', 777, 999, '36cd7c77-6d5f-49ab-9a5c-4bf23ff7c5e7.jpg', 0);
INSERT INTO `product` VALUES (5, '小米电视prod', '小米电视', '电视', 777, 666, 'a3b369fc-749f-4d14-8dd9-93f9c178376d.jpg', 0);
INSERT INTO `product` VALUES (6, '华为电视53', '华为的电视', '电视', 777, 999, 'ea1325bb-549d-438d-8aed-a13e7cbbe6ca.jpg', 0);

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

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '111' COMMENT '头像文件名（不要带域名）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` int(1) NOT NULL DEFAULT 0 COMMENT '性别（0-未知、1-男、2-女）',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号（学号、工号）',
  `profess` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学院',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 263 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '111', '管理员', 0, '0000', '学校管理员', '');
INSERT INTO `user` VALUES (100, '111', '体验二', 0, '0009', '计算机学院', '');
INSERT INTO `user` VALUES (106, '4e103500-4375-489f-bdb3-6082306ead6c.jpg', '教师测试账号1', 0, 'teacher1', '计算机学院', '');
INSERT INTO `user` VALUES (110, '111', '教师测试账号', 0, '123', '计算机学院', '');
INSERT INTO `user` VALUES (112, 'b0ebd2ef-e182-4a0e-ab84-4f5e596a7e7d.jpg', '林智能', 0, 'ai2020', NULL, '');
INSERT INTO `user` VALUES (113, '111', 'test1', 0, 'test1', NULL, '');
INSERT INTO `user` VALUES (114, '111', 'test2', 0, 'test2', NULL, '');
INSERT INTO `user` VALUES (115, '111', 'test3', 0, 'test3', NULL, '');
INSERT INTO `user` VALUES (116, '111', 'test4', 0, 'test4', NULL, '');
INSERT INTO `user` VALUES (117, '111', 'test5', 0, 'test5', NULL, '');
INSERT INTO `user` VALUES (118, '111', 'test6', 0, 'test6', NULL, '');
INSERT INTO `user` VALUES (119, '111', 'test7', 0, 'test7', NULL, '');
INSERT INTO `user` VALUES (120, '111', 'test8', 0, 'test8', NULL, '');
INSERT INTO `user` VALUES (121, '111', 'test9', 0, 'test9', NULL, '');
INSERT INTO `user` VALUES (122, '111', 'test10', 0, 'test10', NULL, '');
INSERT INTO `user` VALUES (123, '111', 'test11', 0, 'test11', NULL, '');
INSERT INTO `user` VALUES (124, '111', 'test12', 0, 'test12', NULL, '');
INSERT INTO `user` VALUES (125, '111', 'test13', 0, 'test13', NULL, '');
INSERT INTO `user` VALUES (126, '111', 'test14', 0, 'test14', NULL, '');
INSERT INTO `user` VALUES (127, '111', 'test15', 0, 'test15', NULL, '');
INSERT INTO `user` VALUES (128, '111', 'test16', 0, 'test16', NULL, '');
INSERT INTO `user` VALUES (129, '111', 'test17', 0, 'test17', NULL, '');
INSERT INTO `user` VALUES (130, '111', 'test18', 0, 'test18', NULL, '');
INSERT INTO `user` VALUES (131, '111', 'AI酱', 0, 'ai20202', NULL, '');
INSERT INTO `user` VALUES (132, '111', 'student01', 0, 'student01', NULL, '');
INSERT INTO `user` VALUES (133, '111', 'student02', 0, 'student02', NULL, '');
INSERT INTO `user` VALUES (134, '111', 'student03', 0, 'student03', NULL, '');
INSERT INTO `user` VALUES (135, '111', 'student', 0, 'student04', NULL, '');
INSERT INTO `user` VALUES (136, '111', 'student', 0, 'student05', NULL, '');
INSERT INTO `user` VALUES (137, '111', 'student', 0, 'student06', NULL, '');
INSERT INTO `user` VALUES (139, '111', 'teacher01', 0, 'teacher01', '人工智能学院', '');
INSERT INTO `user` VALUES (143, '111', '何毅城', 0, '3119000973', NULL, '');
INSERT INTO `user` VALUES (144, '32aa01b6-fd95-4246-8c84-7f196c841361.jpg', '梁梓熙', 0, '3119001152', NULL, '');
INSERT INTO `user` VALUES (145, '111', '陈嘉俊', 0, '3118000920', NULL, '');
INSERT INTO `user` VALUES (146, '111', '周明悦', 0, '3119001090', NULL, '');
INSERT INTO `user` VALUES (147, '111', '陈建汛', 0, '3118000447', NULL, '');
INSERT INTO `user` VALUES (148, '111', '王宇智', 0, '3119005115', NULL, '');
INSERT INTO `user` VALUES (149, '111', '陈梦飞', 0, '3219002213', NULL, '');
INSERT INTO `user` VALUES (150, '111', '施嘉辉', 0, '3119002524', NULL, '');
INSERT INTO `user` VALUES (151, '90753ae4-9e83-4310-b47e-1c5e6861d8d7.jpg', '陈锐', 0, '3118000921', NULL, '');
INSERT INTO `user` VALUES (152, '111', '陈汉杰', 0, '3119000837', NULL, '');
INSERT INTO `user` VALUES (153, '111', '林尔特', 0, '3119000389', NULL, '');
INSERT INTO `user` VALUES (154, '111', '黄欣茵', 0, '3219005356', NULL, '');
INSERT INTO `user` VALUES (155, 'e0d3f9be-1ef9-4032-98c0-7c57e561d18d.jpg', '黄志滨', 0, 'bene', NULL, '');
INSERT INTO `user` VALUES (156, '111', '黄宜泓', 0, '3119000380', NULL, '');
INSERT INTO `user` VALUES (157, '111', '巫润东', 0, '3119000345', NULL, '');
INSERT INTO `user` VALUES (158, '111', 'EAIDK', 0, 'EAIDK-t1', 'EAIDK', '');
INSERT INTO `user` VALUES (166, '111', '华为严选', 0, 'huawei01', NULL, '');
INSERT INTO `user` VALUES (167, '111', '华为严选', 0, 'huawei-t', '华为严选', '');
INSERT INTO `user` VALUES (172, '111', '助理老师', 0, 'tringpa', '人工智能学院', '');
INSERT INTO `user` VALUES (173, '111', '郑冰冰', 0, '3219004770', NULL, '');
INSERT INTO `user` VALUES (174, '111', '文', 0, 'teacherwen', '小A', '');
INSERT INTO `user` VALUES (175, '111', '朱文辉', 0, '3117001478', NULL, '');
INSERT INTO `user` VALUES (205, '111', '广轻工', 0, '2020gqg', '人工智能学院', '');
INSERT INTO `user` VALUES (206, '111', '广轻工', 0, '2020gqg0001', '人工智能学院', '');
INSERT INTO `user` VALUES (209, '111', 'tring', 0, 'tringedu', NULL, '');
INSERT INTO `user` VALUES (248, '111', '嘉颖', 0, 'colin', '人工智能学院', '');
INSERT INTO `user` VALUES (249, '111', '嘉颖', 0, 'colin01', NULL, '');
INSERT INTO `user` VALUES (252, '8b9bb8ef-1fa1-48ae-907d-338fb0abea16.jpg', 'BENE', 0, '1562619830401', NULL, '');
INSERT INTO `user` VALUES (254, '111', '王雪飞', 0, '13073181799', NULL, '');
INSERT INTO `user` VALUES (255, '111', '李丽英', 0, '13803505527', NULL, '');
INSERT INTO `user` VALUES (256, '111', '王彤', 0, '18913191077', NULL, '');
INSERT INTO `user` VALUES (257, '111', '蔡基锋', 0, '15889961255', NULL, '');
INSERT INTO `user` VALUES (259, '111', '哈雯', 0, '15918746623', NULL, '');
INSERT INTO `user` VALUES (260, '111', '胡建华', 0, '15875602058', NULL, '');
INSERT INTO `user` VALUES (261, '111', '黄杰晟', 0, '13570342554', NULL, '');
INSERT INTO `user` VALUES (262, '111', '常熟理工', 0, 'changshuAI01', NULL, '');

-- ----------------------------
-- Table structure for user_exam
-- ----------------------------
DROP TABLE IF EXISTS `user_exam`;
CREATE TABLE `user_exam`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户考试id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `exam_id` int(11) NOT NULL COMMENT '考试id',
  `exam_status` tinyint(10) NOT NULL DEFAULT 0 COMMENT '考试状态（0-未考试，1-已提交，2-已批改）',
  `exam_score` double(20, 2) NOT NULL DEFAULT 0.00 COMMENT '考试分数',
  `time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 602 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_exam
-- ----------------------------
INSERT INTO `user_exam` VALUES (325, 50, 17, 2, 3.50, '2020-05-25 10:52:48');
INSERT INTO `user_exam` VALUES (326, 51, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (327, 52, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (328, 53, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (329, 54, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (330, 55, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (331, 56, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (332, 57, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (333, 58, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (334, 143, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (335, 144, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (336, 145, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (337, 146, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (338, 147, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (339, 148, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (340, 149, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (341, 150, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (342, 151, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (343, 152, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (344, 153, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (345, 154, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (346, 155, 17, 2, 4.00, '2020-05-28 01:37:57');
INSERT INTO `user_exam` VALUES (347, 156, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (348, 157, 17, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (412, 143, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (413, 144, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (414, 145, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (415, 146, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (416, 147, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (417, 148, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (418, 149, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (419, 150, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (420, 151, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (421, 152, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (422, 153, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (423, 154, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (424, 155, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (425, 156, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (426, 157, 21, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (451, 176, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (452, 177, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (453, 178, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (454, 179, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (455, 180, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (456, 181, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (457, 182, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (458, 183, 25, 2, 0.00, '2020-08-16 08:44:11');
INSERT INTO `user_exam` VALUES (459, 184, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (460, 185, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (461, 186, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (462, 187, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (463, 188, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (464, 189, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (465, 190, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (466, 191, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (467, 192, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (468, 193, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (469, 194, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (470, 195, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (471, 196, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (472, 197, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (473, 198, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (474, 199, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (475, 200, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (476, 201, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (477, 202, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (478, 203, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (479, 204, 25, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (551, 143, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (552, 144, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (553, 145, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (554, 146, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (555, 147, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (556, 148, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (557, 149, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (558, 150, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (559, 151, 37, 2, 1.50, '2020-08-30 02:15:01');
INSERT INTO `user_exam` VALUES (560, 152, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (561, 153, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (562, 154, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (563, 155, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (564, 156, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (565, 157, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (566, 173, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (567, 175, 37, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (568, 143, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (569, 144, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (570, 145, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (571, 146, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (572, 147, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (573, 148, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (574, 149, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (575, 150, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (576, 151, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (577, 152, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (578, 153, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (579, 154, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (580, 155, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (581, 156, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (582, 157, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (583, 173, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (584, 175, 38, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (585, 143, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (586, 144, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (587, 145, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (588, 146, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (589, 147, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (590, 148, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (591, 149, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (592, 150, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (593, 151, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (594, 152, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (595, 153, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (596, 154, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (597, 155, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (598, 156, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (599, 157, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (600, 173, 39, 0, 0.00, '');
INSERT INTO `user_exam` VALUES (601, 175, 39, 0, 0.00, '');

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
INSERT INTO `user_money` VALUES (66666, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID（此处为一对多）',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 263 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 3, 'admin');
INSERT INTO `user_role` VALUES (100, 2, '123456');
INSERT INTO `user_role` VALUES (106, 2, 'tringai');
INSERT INTO `user_role` VALUES (110, 2, '123456');
INSERT INTO `user_role` VALUES (112, 1, 'tringai');
INSERT INTO `user_role` VALUES (113, 1, 'tringai');
INSERT INTO `user_role` VALUES (114, 1, '123456');
INSERT INTO `user_role` VALUES (115, 1, '123456');
INSERT INTO `user_role` VALUES (116, 1, '123456');
INSERT INTO `user_role` VALUES (117, 1, '123456');
INSERT INTO `user_role` VALUES (118, 1, '123456');
INSERT INTO `user_role` VALUES (119, 1, '123456');
INSERT INTO `user_role` VALUES (120, 1, '123456');
INSERT INTO `user_role` VALUES (121, 1, '123456');
INSERT INTO `user_role` VALUES (122, 1, '123456');
INSERT INTO `user_role` VALUES (123, 1, '123456');
INSERT INTO `user_role` VALUES (124, 1, '123456');
INSERT INTO `user_role` VALUES (125, 1, '123456');
INSERT INTO `user_role` VALUES (126, 1, '123456');
INSERT INTO `user_role` VALUES (127, 1, '123456');
INSERT INTO `user_role` VALUES (128, 1, '123456');
INSERT INTO `user_role` VALUES (129, 1, '123456');
INSERT INTO `user_role` VALUES (130, 1, '123456');
INSERT INTO `user_role` VALUES (131, 1, 'tringai');
INSERT INTO `user_role` VALUES (132, 1, 'student');
INSERT INTO `user_role` VALUES (133, 1, 'student');
INSERT INTO `user_role` VALUES (134, 1, 'student');
INSERT INTO `user_role` VALUES (135, 1, 'student');
INSERT INTO `user_role` VALUES (136, 1, 'student');
INSERT INTO `user_role` VALUES (137, 1, 'student');
INSERT INTO `user_role` VALUES (139, 2, 'tringai01');
INSERT INTO `user_role` VALUES (143, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (144, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (145, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (146, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (147, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (148, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (149, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (150, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (151, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (152, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (153, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (154, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (155, 1, 'tringai2580');
INSERT INTO `user_role` VALUES (156, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (157, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (158, 2, '123456921058');
INSERT INTO `user_role` VALUES (166, 1, 'huawei01');
INSERT INTO `user_role` VALUES (167, 2, 'huawei-t');
INSERT INTO `user_role` VALUES (172, 2, 'tringpa');
INSERT INTO `user_role` VALUES (173, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (174, 2, 'teacherwen');
INSERT INTO `user_role` VALUES (175, 1, 'alwaysbea');
INSERT INTO `user_role` VALUES (205, 2, '2020gqg');
INSERT INTO `user_role` VALUES (206, 2, '2020gqg0001');
INSERT INTO `user_role` VALUES (209, 1, 'tringedu2580');
INSERT INTO `user_role` VALUES (248, 2, 'colin');
INSERT INTO `user_role` VALUES (249, 1, 'colin01');
INSERT INTO `user_role` VALUES (252, 1, '15626198304');
INSERT INTO `user_role` VALUES (254, 1, '13073181799');
INSERT INTO `user_role` VALUES (255, 1, '13803505527');
INSERT INTO `user_role` VALUES (256, 1, '18913191077');
INSERT INTO `user_role` VALUES (257, 1, '15889961255');
INSERT INTO `user_role` VALUES (259, 1, '15918746623');
INSERT INTO `user_role` VALUES (260, 1, '15875602058');
INSERT INTO `user_role` VALUES (261, 1, '13570342554');
INSERT INTO `user_role` VALUES (262, 1, 'changshuAI01');

-- ----------------------------
-- Table structure for user_trace
-- ----------------------------
DROP TABLE IF EXISTS `user_trace`;
CREATE TABLE `user_trace`  (
  `user_id` int(11) NOT NULL,
  `trace_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`trace_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_trace
-- ----------------------------
INSERT INTO `user_trace` VALUES (1, 1, 4);
INSERT INTO `user_trace` VALUES (1, 2, 4);
INSERT INTO `user_trace` VALUES (1, 3, 4);
INSERT INTO `user_trace` VALUES (1, 4, 5);

SET FOREIGN_KEY_CHECKS = 1;

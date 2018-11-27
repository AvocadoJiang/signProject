/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : sign

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-11-27 23:16:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_academy`
-- ----------------------------
DROP TABLE IF EXISTS `tb_academy`;
CREATE TABLE `tb_academy` (
  `academyID` varchar(32) NOT NULL COMMENT '学院ID',
  `schoolID` varchar(32) NOT NULL COMMENT '学校ID',
  `academyName` varchar(32) NOT NULL COMMENT '学院名称',
  `academyStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0正常使用 1删除',
  PRIMARY KEY (`academyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_academy
-- ----------------------------
INSERT INTO `tb_academy` VALUES ('1', '1', '电信学院', '0');
INSERT INTO `tb_academy` VALUES ('2', '1', '经管学院', '0');
INSERT INTO `tb_academy` VALUES ('3', '2', '材料学院', '0');
INSERT INTO `tb_academy` VALUES ('4', '2', '化工学院', '0');
INSERT INTO `tb_academy` VALUES ('5', '2', '交通学院', '0');

-- ----------------------------
-- Table structure for `tb_class`
-- ----------------------------
DROP TABLE IF EXISTS `tb_class`;
CREATE TABLE `tb_class` (
  `classID` varchar(32) NOT NULL COMMENT '班级ID',
  `majorID` varchar(32) DEFAULT NULL COMMENT '专业ID',
  `grade` int(10) DEFAULT NULL COMMENT '年级ID',
  `className` varchar(128) DEFAULT NULL COMMENT '班级名',
  `classStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0正常使用 1删除',
  PRIMARY KEY (`classID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_class
-- ----------------------------
INSERT INTO `tb_class` VALUES ('1', '1', '2015', '网络151班', '0');
INSERT INTO `tb_class` VALUES ('10', '2', '2015', '计科153班', '0');
INSERT INTO `tb_class` VALUES ('11', '2', '2015', '计科154班', '0');
INSERT INTO `tb_class` VALUES ('2', '1', '2015', '网络152班', '0');
INSERT INTO `tb_class` VALUES ('2a20825293ff449a982a32fd7034f507', '1', '2015', '网络191', '0');
INSERT INTO `tb_class` VALUES ('3', '1', '2016', '网络161班', '0');
INSERT INTO `tb_class` VALUES ('4', '1', '2016', '网路162班', '0');
INSERT INTO `tb_class` VALUES ('5', '1', '2016', '网络163班', '0');
INSERT INTO `tb_class` VALUES ('6', '1', '2017', '网络171班', '0');
INSERT INTO `tb_class` VALUES ('8', '2', '2015', '计科151班', '0');
INSERT INTO `tb_class` VALUES ('9', '2', '2015', '计科152班', '0');
INSERT INTO `tb_class` VALUES ('b45218c383df4edfbaf55e0d62ce111a', '1', '2015', '网络191', '0');

-- ----------------------------
-- Table structure for `tb_course`
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
  `courseID` varchar(32) NOT NULL COMMENT '课程ID',
  `academyID` varchar(32) NOT NULL COMMENT '学院的ID',
  `teacherID` varchar(32) NOT NULL COMMENT '教师ID',
  `courseName` varchar(32) DEFAULT NULL COMMENT '课程名',
  `createTime` date DEFAULT '0000-00-00' COMMENT '创建时间',
  `courseStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0使用中  1删除',
  PRIMARY KEY (`courseID`),
  KEY `teacher` (`teacherID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('1addtyuif54a46689a34798c6ea0f196', '1', 'teacher1', '计算机组成', '2018-06-26', '0');
INSERT INTO `tb_course` VALUES ('2f46c3b57bd645f5b59f6b6061f715e3', '1', 'teacher1', '网络安全', '2018-06-26', '0');
INSERT INTO `tb_course` VALUES ('af5bcdc7f54a46689a34798c6ea0f196', '1', 'teacher1', '数据结构（网络151）', '2018-06-26', '0');
INSERT INTO `tb_course` VALUES ('bd33c1117bd645f5b59f6b6061f715e3', '1', 'teacher1', '操作系统', '2018-06-26', '0');

-- ----------------------------
-- Table structure for `tb_lesson`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lesson`;
CREATE TABLE `tb_lesson` (
  `lessonID` varchar(32) NOT NULL COMMENT '签到ID',
  `courseID` varchar(32) DEFAULT NULL COMMENT '课程ID',
  `lessonStartTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '课程开始时间',
  `lessonEndTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '课程结束时间',
  `lessonSignStatus` varchar(128) DEFAULT '0' COMMENT '课时签到状态  0未进行过签到  1正常签到中  2迟到签到中  3签到完成 4签到取消',
  PRIMARY KEY (`lessonID`),
  KEY `course` (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lesson
-- ----------------------------
INSERT INTO `tb_lesson` VALUES ('076ea4be1b5e469daf7897f04d8fef7c', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-27 10:00:00', '2018-03-27 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('07b6d701b8b44b99b6edea4685dc48ec', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-13 10:55:00', '2018-03-13 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('12e0d8c3f1104dd2a11562a2ae2c0713', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-06 10:00:00', '2018-03-06 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('14545f2cdc9b4fad8a8ae11e9e4f17c3', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-13 11:40:00', '2018-03-13 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('51ef4d4b07bf40c185fae69212ecd04c', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-20 11:40:00', '2018-03-20 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('539340b68b654a28b19bfb38142b4ff0', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-20 10:45:00', '2018-03-20 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('5bd4083f7d3140d68a8838d4476ed266', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-20 10:00:00', '2018-03-20 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('5ec4ca09b4784d3f87785f6712d68c12', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-27 10:55:00', '2018-03-27 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('70b8211866ab4a2f90a5fd72c8281846', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-06 10:55:00', '2018-03-06 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('8a68652dad2e4203a408a021741a6dee', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-13 10:45:00', '2018-03-13 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('a76371fe1ecf4ae192df58854935eb44', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-27 10:45:00', '2018-03-27 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('cfbcab0939024e55b35bd74d15f24468', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-27 11:40:00', '2018-03-27 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('d19d040695314797b9fe911f74547e99', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-13 10:00:00', '2018-03-13 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('d833ce4c78c14adcbb62790d4942f7dc', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-06 10:45:00', '2018-03-06 10:45:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('f0fcc3d8fe5e42c58e84a9151cba8ba4', 'af5bcdc7f54a46689a34798c6ea0f196', '2018-03-20 10:55:00', '2018-03-20 11:40:00', 'SIGN_UNSTART');
INSERT INTO `tb_lesson` VALUES ('fef9f4e067f14f7699f49e62b6f98d24', '2f46c3b57bd645f5b59f6b6061f715e3', '2018-03-06 11:40:00', '2018-03-06 11:40:00', 'SIGN_UNSTART');

-- ----------------------------
-- Table structure for `tb_major`
-- ----------------------------
DROP TABLE IF EXISTS `tb_major`;
CREATE TABLE `tb_major` (
  `majorID` varchar(32) NOT NULL COMMENT '专业ID',
  `academyID` varchar(32) DEFAULT NULL COMMENT '学院ID',
  `majorName` varchar(128) DEFAULT NULL COMMENT '专业名',
  `majorStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0正常使用 1删除',
  PRIMARY KEY (`majorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_major
-- ----------------------------
INSERT INTO `tb_major` VALUES ('1', '1', '网络工程', '0');
INSERT INTO `tb_major` VALUES ('2', '1', '计算机科学', '0');
INSERT INTO `tb_major` VALUES ('3', '1', '电信', '0');
INSERT INTO `tb_major` VALUES ('4', '1', '电科', '1');
INSERT INTO `tb_major` VALUES ('5', '1', '机器人', '0');

-- ----------------------------
-- Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `orderID` varchar(32) NOT NULL COMMENT '学生选课ID',
  `courseID` varchar(32) NOT NULL COMMENT '课程ID',
  `studentID` varchar(32) NOT NULL COMMENT '学生ID',
  `orderStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0正常使用 1删除',
  PRIMARY KEY (`orderID`),
  KEY `course3` (`courseID`),
  KEY `student2` (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES ('02bd7d41762946d2a6d3220be9349e96', '1', '14403010136', '0');
INSERT INTO `tb_order` VALUES ('1', '1addtyuif54a46689a34798c6ea0f196', '15401190123', '0');
INSERT INTO `tb_order` VALUES ('10', '2f46c3b57bd645f5b59f6b6061f715e3', '14401190128', '0');
INSERT INTO `tb_order` VALUES ('11', 'af5bcdc7f54a46689a34798c6ea0f196', '14401190128', '0');
INSERT INTO `tb_order` VALUES ('12', 'bd33c1117bd645f5b59f6b6061f715e3', '14401190128', '0');
INSERT INTO `tb_order` VALUES ('18b9b1cc09e04d629fdb8c03b0116829', '1', '15401190207', '0');
INSERT INTO `tb_order` VALUES ('2', '2f46c3b57bd645f5b59f6b6061f715e3', '14401010307', '0');
INSERT INTO `tb_order` VALUES ('2dc52d9893da440fb7b449c11c3643ce', '1', '15401190209', '0');
INSERT INTO `tb_order` VALUES ('3', 'af5bcdc7f54a46689a34798c6ea0f196', '14401010307', '0');
INSERT INTO `tb_order` VALUES ('3fa5be75c7c44a3b94d274314ee7ac82', '1', '16401010137', '0');
INSERT INTO `tb_order` VALUES ('4', 'bd33c1117bd645f5b59f6b6061f715e3', '14401010307', '0');
INSERT INTO `tb_order` VALUES ('5', '1addtyuif54a46689a34798c6ea0f196', '14401010410', '0');
INSERT INTO `tb_order` VALUES ('6', '2f46c3b57bd645f5b59f6b6061f715e3', '14401010410', '0');
INSERT INTO `tb_order` VALUES ('64faf43d207e4b379985a2a486dc3cea', '1', '14401010307', '0');
INSERT INTO `tb_order` VALUES ('7', 'af5bcdc7f54a46689a34798c6ea0f196', '14401010410', '0');
INSERT INTO `tb_order` VALUES ('8', 'bd33c1117bd645f5b59f6b6061f715e3', '14401010410', '0');
INSERT INTO `tb_order` VALUES ('9', '1addtyuif54a46689a34798c6ea0f196', '14401190128', '0');
INSERT INTO `tb_order` VALUES ('9604db881c6a430383293df1ab806dc0', '1', '14401190128', '0');
INSERT INTO `tb_order` VALUES ('9c1dfbcf7a084758aabef55a0dadeda7', '1', '14401010410', '0');
INSERT INTO `tb_order` VALUES ('c1d8af442909400c985e574011c9d656', '1', '15401190219', '0');
INSERT INTO `tb_order` VALUES ('fbda63faa5ac43d39a96b258582ee236', '1', '16401200135', '0');

-- ----------------------------
-- Table structure for `tb_school`
-- ----------------------------
DROP TABLE IF EXISTS `tb_school`;
CREATE TABLE `tb_school` (
  `schoolID` varchar(32) NOT NULL COMMENT '学校ID',
  `schoolName` varchar(128) DEFAULT NULL COMMENT '学校名称',
  `schoolStatus` int(10) NOT NULL DEFAULT '0' COMMENT '0正常使用  1删除',
  PRIMARY KEY (`schoolID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_school
-- ----------------------------
INSERT INTO `tb_school` VALUES ('1', '宁波工程学院', '0');
INSERT INTO `tb_school` VALUES ('2', '宁波大学', '0');

-- ----------------------------
-- Table structure for `tb_stusign`
-- ----------------------------
DROP TABLE IF EXISTS `tb_stusign`;
CREATE TABLE `tb_stusign` (
  `stuSignID` varchar(32) NOT NULL COMMENT '学生签到ID',
  `studentID` varchar(32) NOT NULL COMMENT '学生ID',
  `lessonID` varchar(32) NOT NULL COMMENT '班级ID',
  `stuSignStatus` varchar(128) NOT NULL COMMENT '0未签到   1已签到  2迟到  3请假',
  PRIMARY KEY (`stuSignID`),
  KEY `student3` (`studentID`),
  KEY `classID` (`lessonID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stusign
-- ----------------------------
INSERT INTO `tb_stusign` VALUES ('1', 'student1', '12e0d8c3f1104dd2a11562a2ae2c0713', 'NORMAL_SIGN');
INSERT INTO `tb_stusign` VALUES ('2', 'student2', '1', 'NORMAL_SIGN');
INSERT INTO `tb_stusign` VALUES ('3', 'student1', '2', 'LATE_SIGN');
INSERT INTO `tb_stusign` VALUES ('36baba26d23641fc881a92507e65d0fc', '1', '1', 'NORMAL_SIGN');
INSERT INTO `tb_stusign` VALUES ('4', 'student2', '2', 'LEAVE_SIGN');
INSERT INTO `tb_stusign` VALUES ('5', '15401190209', '12e0d8c3f1104dd2a11562a2ae2c0713', 'LATE_SIGN');
INSERT INTO `tb_stusign` VALUES ('6', '14401010410', '12e0d8c3f1104dd2a11562a2ae2c0713', 'LEAVE_SIGN');
INSERT INTO `tb_stusign` VALUES ('7', 'student2', '12e0d8c3f1104dd2a11562a2ae2c0713', 'NOT_SIGN');
INSERT INTO `tb_stusign` VALUES ('9d637d36f0c545f0a8e2e2cc50b3e74a', '1', '1', 'NORMAL_SIGN');
INSERT INTO `tb_stusign` VALUES ('bbfef39a840f4f4988aefca11a92608f', '1', '1', 'NORMAL_SIGN');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `userID` varchar(32) NOT NULL COMMENT '用户的学工号',
  `schoolID` varchar(32) DEFAULT '' COMMENT '所属学校ID',
  `academyID` varchar(32) DEFAULT '' COMMENT '所属学院ID',
  `majorID` varchar(32) DEFAULT '' COMMENT '所属专业ID',
  `classID` varchar(32) DEFAULT '' COMMENT '班级ID',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(16) DEFAULT NULL,
  `identity` varchar(128) DEFAULT NULL COMMENT '用户身份 \r\n 0学生 \r\n 1教师  \r\n2管理员\r\n  3最高管理员 ',
  `openID` varchar(32) DEFAULT NULL COMMENT '用户微信openID',
  `createTime` date NOT NULL DEFAULT '0000-00-00' COMMENT '账号创建时间',
  `locationX` decimal(10,0) DEFAULT NULL COMMENT '位置X纬度',
  `locationY` decimal(10,0) DEFAULT NULL COMMENT '位置Y经度',
  `userStatus` int(10) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1删除',
  PRIMARY KEY (`userID`),
  KEY `school` (`schoolID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('15401190123', '1', '1', '', '', '15401190126', '蒋周杰', null, 'ADMIN', null, '2018-04-13', '0', '0', '0');
INSERT INTO `tb_user` VALUES ('16401200135', '1', '1', '', '', '16401200135', '蔡孟泳', '15258394445', 'ADMIN', null, '2018-04-13', '0', '0', '0');
INSERT INTO `tb_user` VALUES ('student1', '1', '1', '1', '1', 'student1', '学生1', null, 'STUDENT', null, '2018-04-13', '0', '0', '0');
INSERT INTO `tb_user` VALUES ('student2', '1', '1', '1', '1', 'student2', '学生2', null, 'STUDENT', null, '2018-04-13', '0', '0', '0');
INSERT INTO `tb_user` VALUES ('student3', '1', '1', '1', '1', 'student3', '学生3', null, 'STUDENT', null, '2018-04-13', '0', '0', '0');
INSERT INTO `tb_user` VALUES ('teacher1', '1', '1', '1', '', 'teacher1', '鲍淑娣', null, 'TEACHER', null, '2018-04-13', '0', '0', '0');

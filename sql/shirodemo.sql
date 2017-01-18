-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.62-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema `shirodemo`
--

CREATE DATABASE IF NOT EXISTS `mobile`;
USE `mobile`;

--
-- Definition of table `cp_permission`
--

DROP TABLE IF EXISTS `cp_permission`;
CREATE TABLE `cp_permission` (
  `id`          VARCHAR(36)  NOT NULL
  COMMENT '权限Id',
  `name`        VARCHAR(255) NOT NULL
  COMMENT '权限名称',
  `description` VARCHAR(255) COMMENT '权限描述',
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `id` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_permission`
--

/*!40000 ALTER TABLE `cp_permission` DISABLE KEYS */;
INSERT INTO `cp_permission` (`id`, `name`, `description`) VALUES
  ('b6d3ceb9-eae9-43a6-9064-424db8c53208', 'user:manage', '管理用户'),
  ('b6d3ceb9-eae9-43a6-9064-424db8c53209', 'user:edit', '编辑用户'),
  ('29a366b9-bbbf-47e4-a0c2-26eec58b1822', 'user:delete', '删除用户');
/*!40000 ALTER TABLE `cp_permission` ENABLE KEYS */;


--
-- Definition of table `cp_role`
--

DROP TABLE IF EXISTS `cp_role`;
CREATE TABLE `cp_role` (
  `id`          VARCHAR(36)  NOT NULL
  COMMENT '角色Id',
  `name`        VARCHAR(255) NOT NULL
  COMMENT '角色名称',
  `description` VARCHAR(255) COMMENT '角色描述',
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `id` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_role`
--

/*!40000 ALTER TABLE `cp_role` DISABLE KEYS */;
INSERT INTO `cp_role` (`id`, `name`, `description`) VALUES
  ('b432d31d-ebd2-4e91-9184-1b3769e6686b', 'admin', '管理员'),
  ('b432d31d-ebd2-4e91-9184-1b3769e6686c', 'normal', '普通用户'),
  ('b432d31d-ebd2-4e91-9184-1b3769e6686d', 'guest', '访客');
/*!40000 ALTER TABLE `cp_role` ENABLE KEYS */;


--
-- Definition of table `cp_role_permission`
--

DROP TABLE IF EXISTS `cp_role_permission`;
CREATE TABLE `cp_role_permission` (
  `role_id`       VARCHAR(36) NOT NULL
  COMMENT '角色id',
  `permission_id` VARCHAR(36) NOT NULL
  COMMENT '权限id',
  PRIMARY KEY (`role_id`, `permission_id`),
  KEY `role_id` (`role_id`),
  KEY `authority_id` (`permission_id`),
  CONSTRAINT `cp_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `cp_role` (`id`),
  CONSTRAINT `cp_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `cp_permission` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_role_permission`
--

/*!40000 ALTER TABLE `cp_role_permission` DISABLE KEYS */;
INSERT INTO `cp_role_permission` (`role_id`, `permission_id`) VALUES
  ('b432d31d-ebd2-4e91-9184-1b3769e6686b', 'b6d3ceb9-eae9-43a6-9064-424db8c53208'),
  ('b432d31d-ebd2-4e91-9184-1b3769e6686b', 'b6d3ceb9-eae9-43a6-9064-424db8c53209'),
  ('b432d31d-ebd2-4e91-9184-1b3769e6686b', '29a366b9-bbbf-47e4-a0c2-26eec58b1822'),
  ('b432d31d-ebd2-4e91-9184-1b3769e6686c', 'b6d3ceb9-eae9-43a6-9064-424db8c53208');
/*!40000 ALTER TABLE `cp_role_permission` ENABLE KEYS */;


--
-- Definition of table `cp_user`
--

DROP TABLE IF EXISTS `cp_user`;
CREATE TABLE `cp_user` (
  `id`            VARCHAR(36)  NOT NULL
  COMMENT '用户ID',
  `username`      VARCHAR(255) NOT NULL
  COMMENT '用户名',
  `password`      VARCHAR(255) NOT NULL
  COMMENT '密码',
  `email`         VARCHAR(255) NOT NULL
  COMMENT '邮箱',
  `register_time` DATETIME DEFAULT NULL
  COMMENT '注册时间',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  KEY `id` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_user`
--

/*!40000 ALTER TABLE `cp_user` DISABLE KEYS */;
INSERT INTO `cp_user` (`id`, `username`, `password`, `email`, `register_time`) VALUES
  ('6e5afb1d-50e1-45fe-b6fe-b9e399415387', 'admin', 'admin', 'admin@chinasws.com', '2015-02-06 17:06:36'),
  ('14ff5253-5912-4a3f-b51b-f50d9da0271d', 'guest', 'guest', 'zty@qq.com', '2015-02-06 17:13:55');
/*!40000 ALTER TABLE `cp_user` ENABLE KEYS */;


--
-- Definition of table `cp_user_role`
--

DROP TABLE IF EXISTS `cp_user_role`;
CREATE TABLE `cp_user_role` (
  `user_id` VARCHAR(36) NOT NULL
  COMMENT '用户Id',
  `role_id` VARCHAR(36) NOT NULL
  COMMENT '角色Id',
  PRIMARY KEY (`user_id`, `role_id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cp_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `cp_user` (`id`),
  CONSTRAINT `cp_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `cp_role` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_user_role`
--

/*!40000 ALTER TABLE `cp_user_role` DISABLE KEYS */;
INSERT INTO `cp_user_role` (`user_id`, `role_id`) VALUES
  ('6e5afb1d-50e1-45fe-b6fe-b9e399415387', 'b432d31d-ebd2-4e91-9184-1b3769e6686b'),
  ('6e5afb1d-50e1-45fe-b6fe-b9e399415387', 'b432d31d-ebd2-4e91-9184-1b3769e6686c'),
  ('6e5afb1d-50e1-45fe-b6fe-b9e399415387', 'b432d31d-ebd2-4e91-9184-1b3769e6686d'),
  ('14ff5253-5912-4a3f-b51b-f50d9da0271d', 'b432d31d-ebd2-4e91-9184-1b3769e6686d');
/*!40000 ALTER TABLE `cp_user_role` ENABLE KEYS */;


--
-- Definition of table `cp_menu`
--

DROP TABLE IF EXISTS `cp_menu`;
CREATE TABLE `cp_menu` (
  `id`        VARCHAR(36)  NOT NULL
  COMMENT '菜单ID',
  `label`     VARCHAR(255) NOT NULL
  COMMENT '菜单描述',
  `link`      VARCHAR(255) COMMENT '链接',
  `parent_id` VARCHAR(255) COMMENT '上一级菜单ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `id` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_menu`
--

/*!40000 ALTER TABLE `cp_menu` DISABLE KEYS */;
INSERT INTO `cp_menu` (`id`, `label`, `link`, `parent_id`) VALUES
  ('6e5afb1d-50e1-45fe-b6fe-000000000000', '我的服务器', '/servers', NULL),
  ('6e5afb1d-50e1-45fe-b6fe-000000000001', '添加服务器', '/servers/add', '6e5afb1d-50e1-45fe-b6fe-000000000000'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000002', '我的客户', '/customers', NULL),
  ('6e5afb1d-50e1-45fe-b6fe-000000000003', '用户管理', '/usermanage', NULL),
  ('6e5afb1d-50e1-45fe-b6fe-000000000004', 'Demo', NULL, NULL),
  ('6e5afb1d-50e1-45fe-b6fe-000000000005', '火车', '/trainsview', '6e5afb1d-50e1-45fe-b6fe-000000000004'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000006', '常用链接', NULL, NULL),
  ('6e5afb1d-50e1-45fe-b6fe-000000000007', 'Google镜像', 'https://clonegoogle.com',
   '6e5afb1d-50e1-45fe-b6fe-000000000006'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000008', '百度', 'https://www.baidu.com', '6e5afb1d-50e1-45fe-b6fe-000000000006'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000009', '开源中国', 'http://www.oschina.net', '6e5afb1d-50e1-45fe-b6fe-000000000006');
/*!40000 ALTER TABLE `cp_user` ENABLE KEYS */;


--
-- Definition of table `cp_menu_role`
--

DROP TABLE IF EXISTS `cp_menu_role`;
CREATE TABLE `cp_menu_role` (
  `menu_id` VARCHAR(36) NOT NULL
  COMMENT '菜单Id',
  `role_id` VARCHAR(36) NOT NULL
  COMMENT '角色Id',
  PRIMARY KEY (`menu_id`, `role_id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `cp_menu_role_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `cp_menu` (`id`),
  CONSTRAINT `cp_menu_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `cp_role` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `cp_menu_role`
--

/*!40000 ALTER TABLE `cp_menu_role` DISABLE KEYS */;
INSERT INTO `cp_menu_role` (`menu_id`, `role_id`) VALUES
  ('6e5afb1d-50e1-45fe-b6fe-000000000000', 'b432d31d-ebd2-4e91-9184-1b3769e6686b'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000001', 'b432d31d-ebd2-4e91-9184-1b3769e6686b'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000002', 'b432d31d-ebd2-4e91-9184-1b3769e6686b'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000003', 'b432d31d-ebd2-4e91-9184-1b3769e6686b'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000004', 'b432d31d-ebd2-4e91-9184-1b3769e6686c'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000005', 'b432d31d-ebd2-4e91-9184-1b3769e6686c'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000006', 'b432d31d-ebd2-4e91-9184-1b3769e6686d'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000007', 'b432d31d-ebd2-4e91-9184-1b3769e6686d'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000008', 'b432d31d-ebd2-4e91-9184-1b3769e6686d'),
  ('6e5afb1d-50e1-45fe-b6fe-000000000009', 'b432d31d-ebd2-4e91-9184-1b3769e6686d');
/*!40000 ALTER TABLE `cp_user_role` ENABLE KEYS */;


/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户名',
  `passWord` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_sex` varchar(32) DEFAULT NULL,
  `nick_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `init_point` INT NOT NULL,
  `active_flg` INT NOT NULL,
  `update_id` INT,
  `update_date` date,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `match`
-- ----------------------------
DROP TABLE IF EXISTS `match`;
CREATE TABLE `match`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `MATCH_DATE` VARCHAR(8) NOT NULL, 
  WINNER1 INT,
  WINNER2 INT,
  LOSSER1 INT, 
  LOSSER2 INT, 
  `WIN_SCORE` INT, 
  `LOSS_SCORE` INT,
  POINTS INT,
  UPDATE_ID INT,
  UPDATE_DATE DATE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `point`
-- ----------------------------
DROP TABLE IF EXISTS `point`;
CREATE TABLE `point`(
  `id` INT NOT NULL ,
  `match_date` VARCHAR(8) NOT NULL,
  `win_match` INT NOT NULL,
  `loss_match` INT NOT NULL,
  `get_points` INT NOT NULL,
  `total_points` INT NOT NULL,
  `rank` INT NOT NULL,
  `update_id` INT,
  `update_date` date,
  primary key (id, match_date)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `syspara`
-- ----------------------------
DROP TABLE IF EXISTS `syspara`;
CREATE TABLE `syspara`(
  `parameter` varchar(32) NOT NULL,
  `value` varchar(100) NOT NULL,
  primary key (parameter)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
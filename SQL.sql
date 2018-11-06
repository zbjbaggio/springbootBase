/*用户表*/;
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `name` varchar(30) NOT NULL COMMENT '真实姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `salt` varchar(50) DEFAULT NULL COMMENT '加密密码的盐',
  `status` tinyint(4) NOT NULL COMMENT '用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定',
  `dr` tinyint(1) NOT NULL COMMENT '逻辑删除',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `operator_id` bigint(20) NOT NULL COMMENT '操作员id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j3bb02ycn6rf98pp2kx08fnu1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


/*权限表*/;
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL COMMENT '是否可用',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) NOT NULL COMMENT '父编号',
  `parent_ids` varchar(30) NOT NULL COMMENT '父编号列表',
  `permission` varchar(30) NOT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:remove,role:view',
  `resource_type` enum('menu','button') NOT NULL COMMENT '资源类型，[menu|button]',
  `url` varchar(30) DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*角色表*/
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL COMMENT '是否可用',
  `description` text COMMENT '角色描述,UI界面显示使用',
  `name` varchar(30) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*角色权限关系表*/
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*用户角色关系表*/
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
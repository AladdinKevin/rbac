CREATE TABLE `sys_acl` (
  `id` int(200) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限名称',
  `aclModule_id` int(100) NOT NULL COMMENT '权限模块moduleid',
  `url` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `type` tinyint(10) DEFAULT NULL COMMENT '权限类型 菜单 按钮 其它',
  `status` tinyint(10) NOT NULL COMMENT '正常 冻结',
  `seq` int(100) NOT NULL COMMENT '当前模块下的排序',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '',
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_aclmodule` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` int(100) NOT NULL COMMENT '上级权限模块id',
  `level` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `status` tinyint(10) NOT NULL,
  `seq` tinyint(10) NOT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '',
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '执行者',
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(200) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_dept` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `level` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '部门级别',
  `seq` tinyint(10) NOT NULL COMMENT '部门在当前层级下的顺序,由小到大',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '注释',
  `parent_id` int(10) DEFAULT NULL COMMENT '上级部门',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作人',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '执行时间',
  `operate_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '执行人ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_log` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `type` tinyint(10) DEFAULT NULL COMMENT '记录是rbac哪个部分的操作日志',
  `target_id` int(100) NOT NULL,
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `operator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(200) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_role` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` tinyint(10) NOT NULL COMMENT '类型:备用 拓展',
  `status` tinyint(10) NOT NULL COMMENT '1 正常 0 冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '',
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_role_acl` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `role_id` int(100) NOT NULL,
  `acl_id` int(100) NOT NULL,
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_role_user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `role_id` int(100) NOT NULL,
  `user_id` int(100) NOT NULL,
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `operate_time` datetime DEFAULT NULL,
  `operate_ip` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `sys_user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `telephone` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `mail` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '注释',
  `dept_id` int(100) NOT NULL,
  `status` tinyint(10) DEFAULT NULL COMMENT '状态 0 冻结 1 正常',
  `operator` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作者',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_ip` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作人ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
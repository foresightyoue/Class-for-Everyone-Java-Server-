--20190802 �û��� local
CREATE TABLE `userbean` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `nickname` varchar(255) DEFAULT NULL COMMENT '�ǳ�',
  `name` varchar(255) DEFAULT NULL COMMENT '�û���',
  `password` varchar(255) DEFAULT NULL COMMENT '����',
  `email` varchar(255) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT = '�û���';
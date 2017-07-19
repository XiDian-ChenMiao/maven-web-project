-- 创建数据库
CREATE DATABASE SecKill;
-- 使用数据库
USE SecKill;
-- 创建秒杀库存表
CREATE TABLE seckill (
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMIT '商品库存ID',
  `name` VARCHAR (120) NOT NULL COMMIT '商品名称',
  `number` INT NOT NULL COMMIT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMIT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMIT '秒杀介绍时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMIT '秒杀创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
) ENGINE = InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET = utf8 COMMIT = '秒杀库存表';

-- 初始化数据
INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀ipone6',100,'2016-05-26 00:00:00','2016-05-27 00:00:00'),
  ('500元秒杀ipad3',200,'2016-05-26 00:00:00','2016-05-27 00:00:00'),
  ('300元秒杀小米4',300,'2016-05-26 00:00:00','2016-05-27 00:00:00'),
  ('200元秒杀红米NOTE',400,'2016-05-26 00:00:00','2016-05-27 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed (
  `seckill_id` BIGINT NOT NULL COMMIT '秒杀商品ID',
  `user_phone` BIGINT NOT NULL COMMIT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示：-1：无效 0：成功 1：已成功 2：已发货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idex_create_time(create_time)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMIT = '秒杀成功明细表';



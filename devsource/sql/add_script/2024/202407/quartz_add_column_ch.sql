ALTER TABLE base_quartz_task
ADD COLUMN `scope` varchar(50) NULL COMMENT '所属系统' AFTER `concurrent`;

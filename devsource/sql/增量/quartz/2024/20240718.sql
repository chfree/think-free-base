ALTER TABLE base_quartz_task_log
ADD COLUMN `log_type` varchar(50) NULL COMMENT '日志类型' AFTER `error_message`,
ADD COLUMN `exec_message` varchar(2000) NULL COMMENT '执行消息' AFTER `log_type`;

ALTER TABLE base_quartz_task_log
    ADD COLUMN `exec_result` varchar(50) NULL COMMENT '执行结果' AFTER `exec_message`;

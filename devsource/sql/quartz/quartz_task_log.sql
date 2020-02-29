create table base_quartz_task_log (
    id varchar(50) NOT NULL COMMENT '主键',
    record_time datetime  NULL COMMENT '记录时间',
    task_name varchar(100) NULL COMMENT '任务名称',
    bean_name varchar(100) NULL COMMENT '对象名称',
    method_name varchar(100) NULL COMMENT '方法名称',
    parameter varchar(1000) NULL COMMENT '参数',
    exec_phase varchar(300) NULL COMMENT '执行阶段',
    result varchar(50) NULL COMMENT '结果',
    error_message varchar(3000) NULL COMMENT '异常消息',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
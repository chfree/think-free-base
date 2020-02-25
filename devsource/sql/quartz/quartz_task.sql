create table base_quartz_task (
    id varchar(50) NOT NULL COMMENT '主键',
    name varchar(100) NULL COMMENT '任务名称',
    method_name varchar(100) NULL COMMENT '方法名称',
    bean_name varchar(50) NULL COMMENT '对象名称',
    cron varchar(100) NULL COMMENT '时间表达式',
    parameter varchar(500) NULL COMMENT '参数',
    description varchar(1000) NULL COMMENT '描述',
    status varchar(50) NULL COMMENT '状态',
    concurrent varchar(50) NULL COMMENT '并发标记',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
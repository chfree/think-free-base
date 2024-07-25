select * from base_quartz_task

INSERT INTO base_quartz_task (id, name, method_name, bean_name, cron, parameter, description, status, concurrent, scope)
VALUES ('2', 'testname', 'test', 'testService', '0/5 * * * * ? ', null, 'test desc', 'open', 'n', 'demo1');


commit;


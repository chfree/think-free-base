DROP TABLE IF EXISTS base_sequence;
CREATE TABLE base_sequence(
    name VARCHAR(50) NOT NULL,
    current_value BIGINT NOT NULL,
    increment INT NOT NULL DEFAULT 1,
    min_value  BIGINT NOT NULL,
    max_value  BIGINT NOT NULL,
    PRIMARY KEY (name)
);

DROP FUNCTION IF EXISTS `nextval`;
DELIMITER //
CREATE  FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS bigint
READS SQL DATA
BEGIN
   DECLARE lastInsertId BIGINT;
   DECLARE maxId BIGINT;
   DECLARE minId BIGINT;
   SET lastInsertId = 0;
   SET minId = 0;
   SET maxId = 0;

   UPDATE base_sequence SET current_value=last_insert_id(current_value+increment) WHERE name=seq_name;

   select last_insert_id() into lastInsertId from base_sequence;
   select max_value into maxId from base_sequence where name=seq_name;

   if(lastInsertId>maxId) then
	select min_value into minId from base_sequence where name=seq_name;

	update base_sequence SET current_value = minId where name = seq_name;

        return minId;
   end if;

  return lastInsertId;
END//
DELIMITER ;









-- DROP FUNCTION IF EXISTS nextval;
-- DELIMITER //
-- CREATE FUNCTION nextval(seq_name VARCHAR (50)) returns BIGINT
-- BEGIN
--  UPDATE base_sequence SET current_value=last_insert_id(current_value+increment) WHERE name=seq_name;
--  RETURN last_insert_id();
-- END //
-- DELIMITER;
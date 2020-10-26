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

   UPDATE base_sequence SET current_value=last_insert_id(current_value+increment) WHERE name=seq_name;
   return last_insert_id();

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
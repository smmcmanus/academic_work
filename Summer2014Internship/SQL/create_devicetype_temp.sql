DROP TABLE IF EXISTS tempTable;
CREATE TABLE tempTable(__status VARCHAR(100) , sys_created_by VARCHAR(11), sys_created_on VARCHAR(100), sys_id VARCHAR(100), sys_mod_count VARCHAR(100), sys_updated_by VARCHAR(100), sys_updated_on VARCHAR(100), u_alpha VARCHAR(100), u_mscns_equip_type_code VARCHAR(100), u_short_description VARCHAR(100));
LOAD DATA LOCAL INFILE "output.csv" INTO TABLE tempTable COLUMNS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\r\n';

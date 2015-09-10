Truncate Table telecom_room;
ALTER TABLE telecom_room DROP COLUMN Telecom_RoomId;
ALTER TABLE telecom_room ADD COlUMN sys_updated_on VARCHAR(100) AFTER sys_id, ADD COlUMN sys_updated_by VARCHAR(100) AFTER sys_id, ADD COlUMN sys_mod_count VARCHAR(100) AFTER sys_id, ADD COlUMN sys_created_on VARCHAR(100) FIRST, ADD COLUMN sys_created_by VARCHAR(100) FIRST, ADD COlUMN __status VARCHAR(100) FIRST;
Insert Into telecom_room Select * From tempTable;
ALTER TABLE telecom_room ADD COLUMN Telecom_RoomId int(10) PRIMARY KEY AUTO_INCREMENT FIRST;
ALTER TABLE telecom_room DROP COlUMN __status, DROP COlUMN sys_created_by, DROP COlUMN sys_created_on, DROP COlUMN sys_mod_count ,DROP COlUMN sys_updated_by, DROP COlUMN sys_updated_on;
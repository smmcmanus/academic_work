Truncate Table mediatype;
ALTER TABLE mediatype DROP COLUMN MediaTypeId;
ALTER TABLE mediatype ADD COlUMN sys_updated_on VARCHAR(100) AFTER sys_id, ADD COlUMN sys_updated_by VARCHAR(100) AFTER sys_id, ADD COlUMN sys_mod_count VARCHAR(100) AFTER sys_id, ADD COlUMN sys_created_on VARCHAR(100) FIRST, ADD COLUMN sys_created_by VARCHAR(100) FIRST, ADD COlUMN __status VARCHAR(100) FIRST;
Insert Into mediatype Select * From tempTable;
ALTER TABLE mediatype ADD COLUMN MediaTypeId int(10) PRIMARY KEY AUTO_INCREMENT FIRST;
ALTER TABLE mediatype DROP COlUMN __status, DROP COlUMN sys_created_by, DROP COlUMN sys_created_on, DROP COlUMN sys_mod_count ,DROP COlUMN sys_updated_by, DROP COlUMN sys_updated_on;
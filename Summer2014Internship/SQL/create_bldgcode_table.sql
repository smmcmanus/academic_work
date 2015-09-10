Truncate Table bldgcode;
ALTER TABLE bldgcode DROP COLUMN BldgCodeId;
ALTER TABLE bldgcode ADD COlUMN sys_updated_on VARCHAR(100) AFTER sys_id, ADD COlUMN sys_updated_by VARCHAR(100) AFTER sys_id, ADD COlUMN sys_mod_count VARCHAR(100) AFTER sys_id, ADD COlUMN sys_created_on VARCHAR(100) FIRST, ADD COLUMN sys_created_by VARCHAR(100) FIRST, ADD COlUMN __status VARCHAR(100) FIRST;
Insert Into bldgcode Select * From tempTable;
ALTER TABLE bldgcode ADD COLUMN BldgCodeId int(10) PRIMARY KEY AUTO_INCREMENT FIRST;
ALTER TABLE bldgcode DROP COlUMN __status, DROP COlUMN sys_created_by, DROP COlUMN sys_created_on, DROP COlUMN sys_mod_count ,DROP COlUMN sys_updated_by, DROP COlUMN sys_updated_on;

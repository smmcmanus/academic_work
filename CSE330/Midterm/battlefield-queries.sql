CREATE DATABASE battlefield;
CREATE TABLE reports
(
id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
ammunition smallint unsigned not null,
soldiers smallint unsigned not null,
duration double(6,1) unsigned not null,
critique tinytext,
posted timestamp not null default current_timestamp,
PRIMARY KEY (id)
);
select critique from reports order by posted limit 5;
INSERT INTO reports (ammunition, soldiers, duration, critique) VALUES (?, ?, ?, ?);
select soldiers, avg(ammunition / duration) as pounds_per_sec from reports group by soldiers order by soldiers desc;
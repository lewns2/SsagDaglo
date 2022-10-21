CREATE TABLE `user` (
	`email`	VARCHAR(30)	NOT NULL	COMMENT '이메일(기본키)',
	`password`	VARCHAR(50)	NULL,
	`nickname`	VARCHAR(10)	NULL
);

CREATE TABLE `storage` (
	`no`	INT(10)	NOT NULL,
	`email`	VARCHAR(30)	NOT NULL	COMMENT '이메일(기본키)',
	`category_no`	INT	NULL,
	`origin_path`	VARCHAR(50)	NULL,
	`filename`	VARCHAR(150)	NULL,
	`output_path`	VARCHAR(150)	NULL,
	`output_filename`	VARCHAR(150)	NULL,
	`create_date`	DATETIME	NULL,
	`update_date`	DATETIME	NULL
);

CREATE TABLE `category` (
	`no`	INT(10)	NOT NULL,
	`email`	VARCHAR(30)	NOT NULL	COMMENT '이메일(기본키)',
	`category_name`	VARCHAR(15)	NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`email`
);

ALTER TABLE `storage` ADD CONSTRAINT `PK_STORAGE` PRIMARY KEY (
	`no`,
	`email`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`no`,
	`email`
);

ALTER TABLE `storage` ADD CONSTRAINT `FK_user_TO_storage_1` FOREIGN KEY (
	`email`
)
REFERENCES `user` (
	`email`
);

ALTER TABLE `category` ADD CONSTRAINT `FK_user_TO_category_1` FOREIGN KEY (
	`email`
)
REFERENCES `user` (
	`email`
);


CREATE TABLE `User` (
	`email`	VARCHAR(30)	NOT NULL	COMMENT '이메일(기본키)',
	`password`	VARCHAR(50)	NULL,
	`nickname`	VARCHAR(10)	NULL
);

CREATE TABLE `Storage` (
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

CREATE TABLE `Category` (
	`no`	INT(10)	NOT NULL,
	`email`	VARCHAR(30)	NOT NULL	COMMENT '이메일(기본키)',
	`category_name`	VARCHAR(15)	NULL
);

ALTER TABLE `User` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`email`
);

ALTER TABLE `Storage` ADD CONSTRAINT `PK_STORAGE` PRIMARY KEY (
	`no`,
	`email`
);

ALTER TABLE `Category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`no`,
	`email`
);

ALTER TABLE `Storage` ADD CONSTRAINT `FK_User_TO_Storage_1` FOREIGN KEY (
	`email`
)
REFERENCES `User` (
	`email`
);

ALTER TABLE `Category` ADD CONSTRAINT `FK_User_TO_Category_1` FOREIGN KEY (
	`email`
)
REFERENCES `User` (
	`email`
);


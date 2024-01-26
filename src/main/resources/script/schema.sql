SHOW DATABASES;
USE nhn_academy_1;
SHOW TABLES;

ALTER TABLE task_tag
    DROP FOREIGN KEY FK_task_TO_task_tag_1;
ALTER TABLE task_tag
    DROP FOREIGN KEY FK_tag_TO_task_tag_1;
ALTER TABLE project_member
    DROP FOREIGN KEY FK_project_TO_project_member_1;

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
                           `project_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           `project_status_id`	BIGINT	NOT NULL,
                           `project_name`	VARCHAR(20)	NULL
);

DROP TABLE IF EXISTS `project_status`;

CREATE TABLE `project_status` (
                                  `project_status_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  `project_status_name`	VARCHAR(20)	NULL
);

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
                       `tag_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       `project_id`	BIGINT	NOT NULL,
                       `tag_name`	VARCHAR(20)	NULL
);

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
                        `task_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `project_id`	BIGINT	NOT NULL,
                        `task_title`	VARCHAR(100)	NULL,
                        `task_content`	VARCHAR(1000)	NULL,
                        `task_write_member_id`	VARCHAR(30)	NULL
);

DROP TABLE IF EXISTS `task_tag`;

CREATE TABLE `task_tag` (
                            `task_id`	BIGINT	NOT NULL,
                            `tag_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `milestone`;

CREATE TABLE `milestone` (
                             `milestone_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             `project_id`	BIGINT	NOT NULL,
                             `task_id`	BIGINT	NOT NULL,
                             `milestone_name`	VARCHAR(20)	NULL,
                             `start_period`	DATE	NULL,
                             `end_period`	DATE	NULL
);

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
                           `comment_id`	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           `task_id`	BIGINT	NOT NULL,
                           `comment_created_at`	DATETIME	NULL,
                           `comment_writer_member_id`	VARCHAR(30)	NULL,
                           `comment_content`	VARCHAR(50)	NULL
);

DROP TABLE IF EXISTS `project_member`;

CREATE TABLE `project_member` (
                                  `project_member_id`	VARCHAR(20)	NOT NULL,
                                  `project_id`	BIGINT	NOT NULL,
                                  `project_role`	VARCHAR(10)	NULL
);

ALTER TABLE `task_tag` ADD CONSTRAINT `PK_TASK_TAG` PRIMARY KEY (
                                                                 `task_id`,
                                                                 `tag_id`
    );

ALTER TABLE `project_member` ADD CONSTRAINT `PK_PROJECT_MEMBER` PRIMARY KEY (
                                                                             `project_member_id`,
                                                                             `project_id`
    );

ALTER TABLE `task_tag` ADD CONSTRAINT `FK_task_TO_task_tag_1` FOREIGN KEY (
                                                                           `task_id`
    )
    REFERENCES `task` (
                       `task_id`
        );

ALTER TABLE `task_tag` ADD CONSTRAINT `FK_tag_TO_task_tag_1` FOREIGN KEY (
                                                                          `tag_id`
    )
    REFERENCES `tag` (
                      `tag_id`
        );

ALTER TABLE `project_member` ADD CONSTRAINT `FK_project_TO_project_member_1` FOREIGN KEY (
                                                                                          `project_id`
    )
    REFERENCES `project` (
                          `project_id`
        );




###
INSERT INTO project_status(project_status_name)
VALUES ('시작');
INSERT INTO project_status(project_status_name)
VALUES ('진행중');
INSERT INTO project_status(project_status_name)
VALUES ('완료');

INSERT INTO task(project_id, task_title, task_content, task_write_member_id)
VALUES (1, 'task1', 'task1 내용', 'user1');

INSERT INTO milestone(project_id, task_id, milestone_name, start_period, end_period)
VALUES (1, 1, '마일스톤1', '2020-01-01', '2020-01-31');
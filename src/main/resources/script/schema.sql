SHOW DATABASES;
USE nhn_academy_1;
SHOW TABLES;

###

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

DROP TABLE IF EXISTS `task_milestone`;
CREATE TABLE `task_milestone` (
                                  `task_id`	BIGINT	NOT NULL,
                                  `milestone_id`	BIGINT	NOT NULL
);

###

ALTER TABLE `task_milestone` ADD CONSTRAINT `PK_TASK_MILESTONE` PRIMARY KEY (
                                                                             `task_id`,
                                                                             `milestone_id`
    );
ALTER TABLE `project_member` ADD CONSTRAINT `PK_PROJECT_MEMBER` PRIMARY KEY (
                                                                             `project_member_id`,
                                                                             `project_id`
    );
ALTER TABLE `task_tag` ADD CONSTRAINT `PK_TASK_TAG` PRIMARY KEY (
                                                                 `task_id`,
                                                                 `tag_id`
    );

#

ALTER TABLE `project` ADD CONSTRAINT `FK_project_status_TO_project_1` FOREIGN KEY (
                                                                                   `project_status_id`
    )
    REFERENCES `project_status` (
                                 `project_status_id`
        );

ALTER TABLE `tag` ADD CONSTRAINT `FK_project_TO_tag_1` FOREIGN KEY (
                                                                    `project_id`
    )
    REFERENCES `project` (
                          `project_id`
        );

ALTER TABLE `task` ADD CONSTRAINT `FK_project_TO_task_1` FOREIGN KEY (
                                                                      `project_id`
    )
    REFERENCES `project` (
                          `project_id`
        );

ALTER TABLE `task_tag` ADD CONSTRAINT `FK_task_TO_task_tag_1` FOREIGN KEY ( ###
                                                                           `task_id`
    )
    REFERENCES `task` (
                       `task_id`
        );

ALTER TABLE `task_tag` ADD CONSTRAINT `FK_tag_TO_task_tag_1` FOREIGN KEY ( ###
                                                                          `tag_id`
    )
    REFERENCES `tag` (
                      `tag_id`
        );

ALTER TABLE `milestone` ADD CONSTRAINT `FK_project_TO_milestone_1` FOREIGN KEY (
                                                                                `project_id`
    )
    REFERENCES `project` (
                          `project_id`
        );

ALTER TABLE `comment` ADD CONSTRAINT `FK_task_TO_comment_1` FOREIGN KEY (
                                                                         `task_id`
    )
    REFERENCES `task` (
                       `task_id`
        );

ALTER TABLE `project_member` ADD CONSTRAINT `FK_project_TO_project_member_1` FOREIGN KEY (
                                                                                          `project_id`
    )
    REFERENCES `project` (
                          `project_id`
        );

ALTER TABLE `task_milestone` ADD CONSTRAINT `FK_task_TO_task_milestone_1` FOREIGN KEY (
                                                                                       `task_id`
    )
    REFERENCES `task` (
                       `task_id`
        );

ALTER TABLE `task_milestone` ADD CONSTRAINT `FK_milestone_TO_task_milestone_1` FOREIGN KEY (
                                                                                            `milestone_id`
    )
    REFERENCES `milestone` (
                            `milestone_id`
        );





###
INSERT INTO project_status(project_status_name)
VALUES ('시작');
INSERT INTO project_status(project_status_name)
VALUES ('진행중');
INSERT INTO project_status(project_status_name)
VALUES ('완료');

INSERT INTO project(project_status_id, project_name)
VALUES (1, '프로젝트1');

INSERT INTO task(project_id, task_title, task_content, task_write_member_id)
VALUES (1, 'task1', 'task1 내용', 'user1');

INSERT INTO milestone(project_id, milestone_name, start_period, end_period)
VALUES (1, '마일스톤1', '2020-01-01', '2020-01-31');

INSERT INTO task_milestone(task_id, milestone_id)
VALUES (2, 1);
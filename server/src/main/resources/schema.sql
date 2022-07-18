DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` INT NOT NULL auto_increment,
  `visa` varchar(3) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `version` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` INT NOT NULL auto_increment,
  `team_id` INT NOT NULL,
  `project_number` decimal(4,0) NOT NULL,
  `name` varchar(50) NOT NULL,
  `customer` varchar(50) NOT NULL,
  `status` varchar(3) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `version` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`)
--  UNIQUE KEY `projectNumber_UNIQUE` (`projectNumber`),
--  KEY `fk_group_id_idx` (`team_id`),
--  CONSTRAINT `fk_team_id` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
);


DROP TABLE IF EXISTS `project_employee`;
CREATE TABLE `project_employee` (
  `project_id` INT NOT NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`project_id`,`employee_id`)
--  KEY `fk_employee_id_idx` (`employee_id`),
--  CONSTRAINT `fk_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
--  CONSTRAINT `fk_project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
);


DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` INT NOT NULL auto_increment,
  `team_leader_id` INT NOT NULL,
  `version` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`)
--  KEY `fk_group_leader_id_idx` (`team_leader_id`),
--  CONSTRAINT `fk_team_leader_id` FOREIGN KEY (`team_leader_id`) REFERENCES `employee` (`id`)
);

CREATE UNIQUE INDEX IDXNAME ON `project`(project_number);



ALTER TABLE `team`
    ADD FOREIGN KEY (team_leader_id)
        REFERENCES `employee`(id);

ALTER TABLE `project_employee`
    ADD FOREIGN KEY (project_id)
        REFERENCES `project`(id);

ALTER TABLE `project_employee`
    ADD FOREIGN KEY (employee_id)
        REFERENCES `employee`(id);

ALTER TABLE `project`
    Add FOREIGN KEY (team_id)
        REFERENCES `team`(id)
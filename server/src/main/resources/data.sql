INSERT INTO `employee` (visa, first_name, last_name, birth_date, version) VALUES
    ('NGU','NGUYEN THAI','SON','2001-03-09',1)
    ,('TRA','TRAN HOANG','THAI','2001-09-11',1)
    ,('NGT','NGUYEN THAI','BAO','2001-10-11',1)
    ,('NGD','NGUYEN THAI','DUY','2001-09-17',1)
    ,('TTT','TRAN THANH','THINH','2001-10-14',1);

INSERT INTO `team` (team_leader_id, version) VALUES (1,1),(4,1);

INSERT INTO `project` (team_id, project_number, name, customer, status, start_date, end_date, version) VALUES
    (1,1,'iot','ELCA','NEW','2022-06-01','2022-08-31',1)
    ,(2,2,'web','NashTech','PLA','2022-09-01','2022-12-31',2);

INSERT INTO `project_employee` (project_id, employee_id) VALUES (1,1),(1,2),(2,3),(2,4),(2,5);



INSERT INTO task
(`id`, `title`,  `is_task_open`, `created_on`, `priority`)
VALUES (111, 'first test todo', false, CURRENT_TIME(), 'LOW');

INSERT INTO task
(`id`, `title`,  `is_task_open`, `created_on`, `priority`)
VALUES (112, 'second test todo', false, CURRENT_TIME(), 'MEDIUM');

INSERT INTO task
(`id`, `title`,  `is_task_open`, `created_on`, `priority`)
VALUES (113, 'third test todo', true, CURRENT_TIME(), 'HIGH');

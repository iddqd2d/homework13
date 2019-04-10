SELECT project_name FROM `projects` 
WHERE cost = (SELECT MAX(cost) FROM `projects`);
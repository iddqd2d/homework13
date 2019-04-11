SELECT AVG(salary) as poor_dev FROM developers 
JOIN developers_projects ON developers.id = developers_projects.developers_id  
JOIN projects ON projects.id = developers_projects.project_id
WHERE cost = (SELECT MIN(cost) FROM `projects`)

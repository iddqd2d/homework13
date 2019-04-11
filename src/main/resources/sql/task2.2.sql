SELECT SUM(salary) as project_balance, project_name FROM developers 
JOIN developers_projects ON developers.id = developers_projects.developers_id  
JOIN projects ON projects.id = developers_projects.project_id
GROUP BY developers_projects.project_id
ORDER BY project_balance DESC
LIMIT 1;

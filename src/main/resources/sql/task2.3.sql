SELECT SUM(salary) as java_dev FROM developers 
JOIN developers_skills ON developers.id = developers_skills.developer_id  
JOIN skills ON skills.id = developers_skills.skill_id
WHERE skills.skill_name = "Java";

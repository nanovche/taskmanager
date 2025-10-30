INSERT INTO `spring`.`authorities` VALUES (NULL, 'john', 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE username = VALUES(username);

 INSERT INTO `spring`.`users` VALUES (NULL, 'john', '$2a$10$l/M6B4pl8Iq7mqX3KrrV0OGfJjTgjq7rdBxRie5TrsApQUOXEgr/u', '1')
 ON DUPLICATE KEY UPDATE username = VALUES(username);
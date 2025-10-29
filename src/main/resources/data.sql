INSERT INTO `spring`.`authorities` VALUES (NULL, 'john', 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE username = VALUES(username);

 INSERT INTO `spring`.`users` VALUES (NULL, 'john', '12345', '1')
 ON DUPLICATE KEY UPDATE username = VALUES(username);
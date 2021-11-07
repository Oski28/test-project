/* DELETE */

DELETE FROM refresh_token; ALTER TABLE refresh_token AUTO_INCREMENT = 1;
DELETE FROM user_role; ALTER TABLE user_role AUTO_INCREMENT = 1;
DELETE FROM black_list_token; ALTER TABLE black_list_token AUTO_INCREMENT = 1;
DELETE FROM user; ALTER TABLE user AUTO_INCREMENT = 1;
DELETE FROM role; ALTER TABLE role AUTO_INCREMENT = 1;



/* Roles */
INSERT INTO role(role) VALUE ("ROLE_USER"),("ROLE_TEACHER");

/* Users */
INSERT INTO user(add_date,email,enabled,firstname,lastname,password,username) VALUE
(CURRENT_TIMESTAMP() ,"jankowalski@gmail.com","1","Jan","Kowalski",
"$2a$10$T3kTt667dJDWjGvYE.KTlOfrhaTXHZApORlbGY1YwHb35DTZF7xOS","Janek");/*H: User123*/

/* User role */
INSERT INTO user_role(id_user,id_role) VALUE ("1","1");

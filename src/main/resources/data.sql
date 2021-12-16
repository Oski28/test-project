/* DELETE */

DELETE FROM refresh_token; ALTER TABLE refresh_token AUTO_INCREMENT = 1;
DELETE FROM user_role; ALTER TABLE user_role AUTO_INCREMENT = 1;
DELETE FROM black_list_token; ALTER TABLE black_list_token AUTO_INCREMENT = 1;
DELETE FROM quiz_access; ALTER TABLE quiz_access AUTO_INCREMENT = 1;
DELETE FROM test_question; ALTER TABLE test_question AUTO_INCREMENT = 1;
DELETE FROM result_answer_answer; ALTER TABLE result_answer_answer AUTO_INCREMENT = 1;
DELETE FROM result_answer; ALTER TABLE result_answer AUTO_INCREMENT = 1;
DELETE FROM quiz_result; ALTER TABLE quiz_result AUTO_INCREMENT = 1;
DELETE FROM test; ALTER TABLE test AUTO_INCREMENT = 1;
DELETE FROM answer; ALTER TABLE answer AUTO_INCREMENT = 1;
DELETE FROM question; ALTER TABLE question AUTO_INCREMENT = 1;
DELETE FROM user; ALTER TABLE user AUTO_INCREMENT = 1;
DELETE FROM role; ALTER TABLE role AUTO_INCREMENT = 1;


/* Roles */
INSERT INTO role(role) VALUE ("ROLE_USER"),("ROLE_TEACHER");

/* Users */
INSERT INTO user(add_date,email,enabled,firstname,lastname,password,username) VALUE
(CURRENT_TIMESTAMP() ,"jankowalski@gmail.com","1","Jan","Kowalski",
"$2a$10$T3kTt667dJDWjGvYE.KTlOfrhaTXHZApORlbGY1YwHb35DTZF7xOS","Janek"),/*H: User123*/
(CURRENT_TIMESTAMP() ,"jan2kowalski@gmail.com","1","Jan2","Kowalski2",
"$2a$10$T3kTt667dJDWjGvYE.KTlOfrhaTXHZApORlbGY1YwHb35DTZF7xOS","Janek2"),/*H: User123*/
(CURRENT_TIMESTAMP() ,"piotrnowak@gmail.com","1","Piotr","Nowak",
"$2a$10$T3kTt667dJDWjGvYE.KTlOfrhaTXHZApORlbGY1YwHb35DTZF7xOS","Piotrek");/*H: User123*/

/* User role */
INSERT INTO user_role(id_user,id_role) VALUE ("1","1"),("1","2"),("3","1"),("3","2"),("2","1");

/* Questions */
INSERT INTO question(points,text,type,creator_id) VALUE ("1","Typem logicznym jest:","SINGLE", "1"),
("2","Typy liczbowe to:","MULTI", "1"), ("1","Aktualna wersja Javy to:","SINGLE", "1");

/* Answer */
INSERT INTO answer(correct,text,question_id) VALUE (true,"boolean","1"), (false ,"String","1"),
(false ,"number","1"), (false ,"float","1"), (true,"double","2"), (false,"String","2"), (true,"int","2"),
(false ,"char","2"), (false ,"8","3"), (false ,"11","3"), (false ,"13","3"), (true ,"17","3"), (false ,"15","3");

/* Test */
INSERT INTO test(end_date, number_of_questions, start_date, time, organizer_id, name)
VALUE("2022-02-14 19:00:00","2","2021-11-14 13:00:00","300000","1","Test 1");

/* Test_Question */
INSERT INTO test_question(id_test,id_question) VALUE ("1","1"),("1","2"),("1","3");

/* Quiz_Access */
INSERT INTO quiz_access(id_test,id_user) VALUE ("1","1"),("1","2");

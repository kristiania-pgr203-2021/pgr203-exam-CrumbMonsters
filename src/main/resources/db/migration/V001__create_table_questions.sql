create table questions
(
    question_Title varchar(100),
    question_Name varchar(1000) primary key
);

insert into questions (question_title, question_name) values
                                                          ('Car', 'What is your favorite car brand?'),
                                                          ('Car', 'What do you drive?'),
                                                          ('Car', 'What is your dream car?'),
                                                          ('Food', 'What is your favorite breakfast?'),
                                                          ('Food', 'What is your favorite lunch?'),
                                                          ('Food', 'What is your favorite dinner?'),
                                                          ('Food', 'What is your favorite kind of pizza?'),
                                                          ('Food', 'What is your favorite kind of pasta?'),
                                                          ('Electronics', 'What brand of phone do you have?'),
                                                          ('Electronics', 'What kind of PC do you have?');
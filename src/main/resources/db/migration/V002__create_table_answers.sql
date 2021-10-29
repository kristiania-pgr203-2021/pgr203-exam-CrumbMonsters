create table answers
(
    answerID serial primary key ,
    questionName varchar(100),
    questionAnswer varchar(1000),
        foreign key (questionName)
            references questions(questionname)
);
create table answers
(
    questionName varchar(1000),
    questionAnswer varchar(1000),
    constraint FK_QuestionName foreign key (questionName)
            references questions(questionname)
);
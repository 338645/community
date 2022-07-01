create table tags
(
    id          int auto_increment,
    tag         varchar(8),
    question_id int,
    constraint TAGS_PK
        primary key (id)
);

create unique index TAGS_ID_UINDEX
    on tags (id);
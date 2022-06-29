create table QUESTIONS
(
    ID            INTEGER auto_increment,
    TITLE         CHARACTER VARYING(50),
    DESCRIPTION   CHARACTER LARGE OBJECT,
    GMT_CREATE    BIGINT,
    GMT_MODIFIED  BIGINT,
    CREATOR       INTEGER,
    COMMENT_COUNT INTEGER default 0,
    VIEW_COUNT    INTEGER default 0,
    LIKE_COUNT    INTEGER default 0,
    TAG           CHARACTER VARYING(256),
    constraint QUESTION_PK
        primary key (ID)
);
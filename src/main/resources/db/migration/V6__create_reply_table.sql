create table REPLYDB
(
    ID           INTEGER auto_increment,
    USER_ID      INTEGER,
    QUEST_ID     INTEGER,
    REPLY        CHARACTER VARYING(255),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    PARENT       INTEGER,
    constraint REPLY_PK
        primary key (ID)
);
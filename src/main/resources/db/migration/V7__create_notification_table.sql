create table notification
(
    id         INT auto_increment,
    notifier   int                   not null,
    receiver   int                   not null,
    type       int,
    OUTERID    int                   not null,
    gmt_create bigint                not null,
    status     boolean default false not null,
    constraint NOTIFICATION_PK
        primary key (id)
);
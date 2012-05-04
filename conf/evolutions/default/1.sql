# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table app (
  id                        bigint not null,
  uri                       varchar(255),
  name                      varchar(255),
  constraint pk_app primary key (id))
;

create table account (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (id))
;


create table app_account (
  app_id                         bigint not null,
  account_id                     bigint not null,
  constraint pk_app_account primary key (app_id, account_id))
;
create sequence app_seq;

create sequence account_seq;




alter table app_account add constraint fk_app_account_app_01 foreign key (app_id) references app (id) on delete restrict on update restrict;

alter table app_account add constraint fk_app_account_account_02 foreign key (account_id) references account (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app;

drop table if exists app_account;

drop table if exists account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_seq;

drop sequence if exists account_seq;


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

create sequence app_seq;

create sequence account_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app;

drop table if exists account;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_seq;

drop sequence if exists account_seq;


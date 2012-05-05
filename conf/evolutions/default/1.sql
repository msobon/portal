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


create table account_app (
  account_id                     bigint not null,
  app_id                         bigint not null,
  constraint pk_account_app primary key (account_id, app_id))
;
create sequence app_seq;

create sequence account_seq;




alter table account_app add constraint fk_account_app_account_01 foreign key (account_id) references account (id) on delete restrict on update restrict;

alter table account_app add constraint fk_account_app_app_02 foreign key (app_id) references app (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app;

drop table if exists account;

drop table if exists account_app;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_seq;

drop sequence if exists account_seq;


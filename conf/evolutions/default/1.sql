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
  sso_token                 varchar(255),
  credits                   bigint,
  is_admin                  boolean,
  constraint pk_account primary key (id))
;


create table user_requestedApps (
  email                          bigint not null,
  id                             bigint not null,
  constraint pk_user_requestedApps primary key (email, id))
;

create table user_userApps (
  email                          bigint not null,
  id                             bigint not null,
  constraint pk_user_userApps primary key (email, id))
;
create sequence app_seq;

create sequence account_seq;




alter table user_requestedApps add constraint fk_user_requestedApps_account_01 foreign key (email) references account (id) on delete restrict on update restrict;

alter table user_requestedApps add constraint fk_user_requestedApps_app_02 foreign key (id) references app (id) on delete restrict on update restrict;

alter table user_userApps add constraint fk_user_userApps_account_01 foreign key (email) references account (id) on delete restrict on update restrict;

alter table user_userApps add constraint fk_user_userApps_app_02 foreign key (id) references app (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists app;

drop table if exists account;

drop table if exists user_requestedApps;

drop table if exists user_userApps;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists app_seq;

drop sequence if exists account_seq;


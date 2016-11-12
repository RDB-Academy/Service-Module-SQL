# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table column_def (
  id                            bigint auto_increment not null,
  created_at                    timestamp,
  modified_at                   timestamp,
  table_def_id                  bigint not null,
  name                          varchar(255) not null,
  data_type                     varchar(255) not null,
  is_primary                    boolean not null,
  is_nullable                   boolean not null,
  meta_value_set                integer,
  constraint pk_column_def primary key (id)
);

create table schema_def (
  id                            bigint auto_increment not null,
  created_at                    timestamp,
  modified_at                   timestamp,
  name                          varchar(255),
  constraint pk_schema_def primary key (id)
);

create table table_def (
  id                            bigint auto_increment not null,
  created_at                    timestamp,
  modified_at                   timestamp,
  name                          varchar(255) not null,
  schema_def_id                 bigint not null,
  constraint pk_table_def primary key (id)
);

create table task (
  id                            bigint auto_increment not null,
  created_at                    timestamp,
  modified_at                   timestamp,
  text                          varchar(255),
  reference_statement           varchar(255),
  schema_def_id                 bigint,
  constraint pk_task primary key (id)
);

alter table column_def add constraint fk_column_def_table_def_id foreign key (table_def_id) references table_def (id) on delete restrict on update restrict;
create index ix_column_def_table_def_id on column_def (table_def_id);

alter table table_def add constraint fk_table_def_schema_def_id foreign key (schema_def_id) references schema_def (id) on delete restrict on update restrict;
create index ix_table_def_schema_def_id on table_def (schema_def_id);

alter table task add constraint fk_task_schema_def_id foreign key (schema_def_id) references schema_def (id) on delete restrict on update restrict;
create index ix_task_schema_def_id on task (schema_def_id);


# --- !Downs

alter table column_def drop constraint if exists fk_column_def_table_def_id;
drop index if exists ix_column_def_table_def_id;

alter table table_def drop constraint if exists fk_table_def_schema_def_id;
drop index if exists ix_table_def_schema_def_id;

alter table task drop constraint if exists fk_task_schema_def_id;
drop index if exists ix_task_schema_def_id;

drop table if exists column_def;

drop table if exists schema_def;

drop table if exists table_def;

drop table if exists task;


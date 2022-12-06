drop database if exists my_media;
create database my_media;
use my_media;

create table movie (
    movie_id int primary key auto_increment,
    movie_name varchar(256) not null,
    movie_year int,
    movie_length int,
    movie_overview varchar(2048)
);

create table tv_show (
    tv_show_id int primary key auto_increment,
    tv_show_name varchar(256) not null,
    tv_show_overview varchar(2048),
    tv_show_release_year int
);

create table tv_show_season (
    tv_show_season_id int primary key auto_increment,
    tv_show_id int not null,
    tv_show_season_index int not null default 0,
    constraint fk_tv_show_season_tv_show_id_tv_show_tv_show_id
		foreign key (tv_show_id)
        references tv_show(tv_show_id)
);

create table tv_show_episode (
    tv_show_episode_id int primary key auto_increment,
    tv_show_season_id int not null,
    tv_show_episode_index int default 0,
    tv_show_episode_name varchar(256),
    tv_show_episode_length int not null default 0,
    constraint fk_tv_show_episode_tv_show_season_tv_show_season_id
        foreign key (tv_show_season_id)
        references tv_show_season(tv_show_season_id)
);

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default 1
);

create table app_user_movie (
	app_user_movie_id int primary key auto_increment,
    app_user_id int not null,
    movie_id int not null,
    watched bool not null default false,
    constraint fk_app_user_movie_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_movie_movie_id
        foreign key (movie_id)
        references movie(movie_id)
);
-- drop table app_user_tv_show;
create table app_user_tv_show (
	app_user_tv_show_id int,
    app_user_id int not null,
    tv_show_id int not null,
    constraint pk_app_user_tv_show_app_user_tv_show_id
        primary key (app_user_tv_show_id),
    constraint fk_app_user_tv_show_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_tv_show_tv_show_id
        foreign key (tv_show_id)
        references tv_show(tv_show_id)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

insert into app_role (`name`) values
    ('ADMIN'),
    ('USER');
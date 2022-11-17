drop database if exists my_media_test;
create database my_media_test;
use my_media_test;

create table movie (
    movie_id int primary key auto_increment,
    movie_name varchar(256) not null,
    movie_year int,
    movie_length int
);

create table tv_show (
    tv_show_id int primary key auto_increment,
    tv_show_name varchar(256) not null
);

create table tv_show_season (
    tv_show_season_id int primary key auto_increment,
    tv_show_id int not null,
    tv_show_season_index int not null default(0)
);

create table tv_show_season_tv_show (
    tv_show_season_id int not null,
    tv_show_id int not null,
    primary key(tv_show_season_id, tv_show_id),
    constraint fk_tv_show_season_tv_show_tv_show_season_tv_show_season_id
        foreign key (tv_show_season_id)
        references tv_show_season(tv_show_season_id),
    constraint fk_tv_show_season_tv_show_tv_show_tv_show_id
        foreign key (tv_show_id)
        references tv_show(tv_show_id)
);

create table tv_show_episode (
    tv_show_episode_id int primary key auto_increment,
    tv_show_season_id int not null,
    tv_show_episode_index int default(0),
    tv_show_episode_name varchar(256),
    tv_show_episode_length int not null default(0),
    constraint fk_tv_show_episode_tv_show_season_tv_show_season_id
        foreign key (tv_show_season_id)
        references tv_show_season(tv_show_season_id)
);

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_user_movie (
    app_user_id int not null,
    movie_id int not null,
    constraint pk_app_user_movie_u_id_m_id
        primary key (app_user_id, movie_id),
    constraint fk_app_user_movie_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_movie_movie_id
        foreign key (movie_id)
        references movie(movie_id)
);

create table app_user_tv_show (
    app_user_id int not null,
    tv_show_id int not null,
    constraint pk_app_user_tv_show_u_id_tv_s_id
        primary key (app_user_id, tv_show_id),
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

delimiter //
create procedure set_known_good_state()
begin
set sql_safe_updates = 0;

delete from app_user_role;
    alter table app_user_role auto_increment = 1;
delete from app_user_movie;
delete from app_user_tv_show;
delete from app_user;
    alter table app_user auto_increment = 1;
delete from tv_show_season_tv_show;
    alter table tv_show_season_tv_show auto_increment = 1;
delete from tv_show_episode;
    alter table tv_show_episode auto_increment = 1;
delete from tv_show_season;
    alter table tv_show_season auto_increment = 1;
delete from tv_show;
    alter table tv_show auto_increment = 1;
delete from movie;
    alter table movie auto_increment = 1;

    insert into movie (movie_name, movie_year, movie_length) values
        ('Iron Man', 2008, 126), -- test read
        ('Iron Man 2', 2010, 125), -- test update
        ('Iron Man 3', 2013, 130); -- test delete
        -- test create ('Spider-Man', null, 121)

    insert into tv_show (tv_show_name) values
        ('Rick and Morty'), -- test read
        ('Suits'), -- test update
        ('Stranger Things'); -- test delete
        -- test create ('Tokyo Ghoul')

    insert into tv_show_season (tv_show_id, tv_show_season_index) values
        (1, 1),
        (1, 2),
        (1, 3),
        (2, 1), -- test update
        (2, 2), -- test delete
        (2, 3); -- test read
        -- test create (1, 4)

    insert into tv_show_season_tv_show (tv_show_season_id, tv_show_id) values
        (1, 1),
        (2, 2);

    insert into tv_show_episode (tv_show_season_id, tv_show_episode_index) values
        (1, 1),
        (1, 2), -- test read
        (1, 3),
        (1, 4), -- test update
        (4, 1),
        (4, 2),
        (4, 3), -- test delete
        (4, 4);
        -- test create (1, 5)

    -- P@ssw0rd!
    insert into app_user (username, password_hash) values
        ('johnsmith', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'),
        ('janedoe', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa'),
        ('ashketchum', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa');

    insert into app_user_movie (app_user_id, movie_id) values
        (1, 1),
        (1, 2),
        (2, 1);

    insert into app_user_tv_show (app_user_id, tv_show_id) values
        (1, 1),
        (1, 2),
        (2, 2);

    insert into app_user_role (app_user_id, app_role_id) values
        (1, 1),
        (2, 2);

    set sql_safe_updates = 1;
end //
delimiter ;

call set_known_good_state();

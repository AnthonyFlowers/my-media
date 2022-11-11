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
    tv_show_season_id int,
    tv_show_id int,
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

delimiter //
create procedure set_known_good_state()
begin
    set sql_safe_updates = 0;
    
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
        ('Iron Man', 2008, 126),
        ('Iron Man 2', 2010, 125),
        ('Iron Man 3', 2013, 130);

    insert into tv_show (tv_show_name) values
        ('Rick and Morty'),
        ('Suits'),
        ('Stranger Things');

    insert into tv_show_season (tv_show_id, tv_show_season_index) values
        (1, 1), 
        (1, 2),
        (1, 3),
        (2, 1),
        (2, 2),
        (2, 3);

    insert into tv_show_season_tv_show (tv_show_season_id, tv_show_id) values
        (1, 1),
        (2, 2);

    insert into tv_show_episode (tv_show_season_id, tv_show_episode_index) values
        (1, 1), 
        (1, 2),
        (1, 3),
        (1, 4),
        (4, 1),
        (4, 2),
        (4, 3),
        (4, 4);
        
    set sql_safe_updates = 1;
end //
delimiter ;
use my_media;
use my_media_test;
call set_known_good_state();
select * from app_user;
select * from app_user_role;
select * from app_role;
select * from tv_show_episode;
select * from tv_show;
select * from tv_show_season;
select * from tv_show_episode e join
    tv_show_season s join
    tv_show t
    on e.tv_show_season_id = s.tv_show_season_id
    and s.tv_show_id = t.tv_show_id;

select username, movie_name from app_user a join app_user_movie am join movie m
    on a.app_user_id = am.app_user_id
    and am.movie_id = m.movie_id;

select username, tv_show_name from app_user a join app_user_tv_show ats join tv_show ts
    on a.app_user_id = ats.app_user_id
    and ats.tv_show_id = ts.tv_show_id;
select * from movie;
select * from movie
where movie_name = "Iron Man"
and movie_year = 2008;
select count(*) from movie;

select * from tv_show;
use my_media_test;
call set_known_good_state();
select * from tv_show_episode;
select * from tv_show;
select * from tv_show_season;
select * from tv_show_episode e join
	tv_show_season s join
    tv_show t
	on e.tv_show_season_id = s.tv_show_season_id
    and s.tv_show_id = t.tv_show_id;
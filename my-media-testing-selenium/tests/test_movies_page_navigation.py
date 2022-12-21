import pytest
from selenium.webdriver.remote.webdriver import WebDriver

from page_objects.movies_page import MoviesPage


@pytest.mark.movies_page
class TestHomePageNavigation:

    @pytest.mark.nav_to_home
    @pytest.mark.navigation
    def test_navigate_to_home(self, driver: WebDriver):
        movies_page = MoviesPage(driver)
        movies_page.open()
        movies_page.navigate_to_home()
        assert movies_page.current_url == movies_page.expected_home_url, \
            'did not navigate to the expected url: ' + \
            movies_page.expected_home_url
        assert movies_page.get_active_nav_name() == 'Home', \
            'the "Home" nav element should be active'

    @pytest.mark.nav_to_movies
    @pytest.mark.navigation
    def test_navigate_to_movies(self, driver: WebDriver):
        movies_page = MoviesPage(driver)
        movies_page.open()
        movies_page.navigate_to_movies()
        assert movies_page.current_url == movies_page.expected_movie_url, \
            'did not navigate to the expected url'
        assert movies_page.get_active_nav_name() == 'Movies', \
            'the "Movies" nav element should be active'

    @pytest.mark.nav_to_tv_shows
    @pytest.mark.navigation
    def test_navigate_to_tv_shows(self, driver: WebDriver):
        movies_page = MoviesPage(driver)
        movies_page.open()
        movies_page.navigate_to_tv_shows()
        assert movies_page.current_url == movies_page.expected_tv_show_url, \
            'did not navigate to the expected url: ' + \
            movies_page.expected_tv_show_url
        assert movies_page.get_active_nav_name() == 'TV Shows', \
            'the "TV Shows" nav element should be active'

import pytest
from selenium.webdriver.remote.webdriver import WebDriver

from page_objects.home_page import HomePage


@pytest.mark.home_page
class TestHomePageNavigation:

    @pytest.mark.nav_to_home
    @pytest.mark.navigation
    def test_navigate_to_home(self, driver: WebDriver):
        page = HomePage(driver)
        page.open()
        page.navigate_to_home()
        assert page.current_url == page.expected_home_url, \
            'did not navigate to the expected url: ' + \
            page.expected_home_url
        assert page.get_active_nav_name() == 'Home', \
            'the "Home" nav element should be active'

    @pytest.mark.nav_to_movies
    @pytest.mark.navigation
    def test_navigate_to_movies(self, driver: WebDriver):
        page = HomePage(driver)
        page.open()
        page.navigate_to_movies()
        assert page.current_url == page.expected_movie_url, \
            'did not navigate to the expected url'
        assert page.get_active_nav_name() == 'Movies', \
            'the "Movies" nav element should be active'

    @pytest.mark.nav_to_tv_shows
    @pytest.mark.navigation
    def test_navigate_to_tv_shows(self, driver: WebDriver):
        home_page = HomePage(driver)
        home_page.open()
        home_page.navigate_to_tv_shows()
        assert home_page.current_url == home_page.expected_tv_show_url, \
            'did not navigate to the expected url: ' + \
            home_page.expected_tv_show_url
        assert home_page.get_active_nav_name() == 'TV Shows', \
            'the "TV Shows" nav element should be active'

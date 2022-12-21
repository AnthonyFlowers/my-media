import pytest
from selenium.webdriver.remote.webdriver import WebDriver

from page_objects.login_page import LoginPage


@pytest.mark.login_page
class TestHomePageNavigation:

    @pytest.mark.nav_to_home
    @pytest.mark.navigation
    def test_navigate_to_home(self, driver: WebDriver):
        login_page = LoginPage(driver)
        login_page.open()
        login_page.navigate_to_home()
        assert login_page.current_url == login_page.expected_home_url, \
            'did not navigate to the expected url: ' + \
            login_page.expected_home_url
        assert login_page.get_active_nav_name() == 'Home', \
            'the "Home" nav element should be active'

    @pytest.mark.nav_to_movies
    @pytest.mark.navigation
    def test_navigate_to_movies(self, driver: WebDriver):
        login_page = LoginPage(driver)
        login_page.open()
        login_page.navigate_to_movies()
        assert login_page.current_url == login_page.expected_movie_url, \
            'did not navigate to the expected url'
        assert login_page.get_active_nav_name() == 'Movies', \
            'the "Movies" nav element should be active'

    @pytest.mark.nav_to_tv_shows
    @pytest.mark.navigation
    def test_navigate_to_tv_shows(self, driver: WebDriver):
        login_page = LoginPage(driver)
        login_page.open()
        login_page.navigate_to_tv_shows()
        assert login_page.current_url == login_page.expected_tv_show_url, \
            'did not navigate to the expected url: ' + \
            login_page.expected_tv_show_url
        assert login_page.get_active_nav_name() == 'TV Shows', \
            'the "TV Shows" nav element should be active'

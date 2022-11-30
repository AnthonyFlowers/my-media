import pytest

from page_objects.base_page import NavItems
from page_objects.logged_in_page import LoggedInPage
from page_objects.login_page import LoginPage


class TestLoginSuccess:
    @pytest.mark.success
    @pytest.mark.login
    def test_login_success(self, driver):
        login_page = LoginPage(driver)
        login_page.open()
        assert login_page.get_active_nav_name() == 'Login',\
            'the "Login" nav element should be active'
        login_page.execute_login('johnsmith', 'P@ssw0rd!')
        logged_in_page = LoggedInPage(driver)
        logged_in_page.wait_for_movies(1)
        assert logged_in_page.get_active_nav_name() == 'Movies', \
            'the "Movies" nav element should be active'
        assert logged_in_page.is_nav_item_displayed(NavItems.PROFILE), \
            'after a successful login the profile nav item should be visible'

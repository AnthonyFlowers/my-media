from page_objects.logged_in_page import LoggedInPage
from page_objects.login_page import LoginPage


class TestLoginSuccess:
    def test_login_success(self, driver):
        login_page = LoginPage(driver)
        login_page.open()
        login_page.execute_login('johnsmith', 'P@ssw0rd!')
        logged_in_page = LoggedInPage(driver)
        logged_in_page.wait_for_movies(1)
        assert logged_in_page.get_active_nav_name() == 'Movies', \
            'the "Movies" nav element should be active'

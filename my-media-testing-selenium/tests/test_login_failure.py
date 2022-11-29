import pytest

from page_objects.login_page import LoginPage


class TestLoginFailure:
    @pytest.mark.fail
    @pytest.mark.login
    @pytest.mark.parametrize(
        'username, password, error_message', [
            (
                    'incorrectUser',
                    'P@ssw0rd!',
                    'Could not login. Username/Password combination incorrect.'
            ),
            (
                    'johnsmith',
                    'incorrectPassword',
                    'Could not login. Username/Password combination incorrect.'
            )
        ]
    )
    def test_login_failure(self, driver, username, password, error_message):
        login_page = LoginPage(driver)
        login_page.open()
        assert login_page.get_active_nav_name() == 'Login', \
            'the "Login" nav element should be active'
        login_page.execute_login(username, password)
        assert login_page.expected_url == login_page.current_url, \
            'unexpected url for login page'
        assert login_page.login_error_text == 'Could not login. Username/Password combination incorrect.', \
            'unexpected login error'

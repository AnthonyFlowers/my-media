import uuid

import pytest

from page_objects.register_page import RegisterPage
from tests.page_form_errors import FormError


class TestRegisterFailure:

    @pytest.mark.fail
    @pytest.mark.execute_registration
    @pytest.mark.parametrize('username, password, password_confirm, error', [
        ('username', 'passw123!', '', FormError.passwords_do_not_match),
        ('', 'passw123!', 'passw123!', FormError.username_empty),
        ('johnsmith', 'passw123!', 'passw123!', FormError.username_in_use),
        ('willNeverUseThisUsername-12', 'pass', 'pass', FormError.password_invalid)
    ])
    def test_register_failure(self, driver, username: str, password: str, password_confirm: str, error):
        register_page = RegisterPage(driver)
        register_page.open()
        assert register_page.get_active_nav_name() == 'Create Account', \
            'The "Create Account" nav element should be active'
        register_page.execute_registration(username, password, password_confirm)
        assert driver.current_url == register_page.expected_url, \
            'unexpected url for registration failure'
        assert register_page.get_active_nav_name() == 'Create Account', \
            'the "Create Account" nav element should be active'
        assert register_page.register_error_text == error, \
            'the error message was incorrect'

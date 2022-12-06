import uuid

import pytest

from page_objects.register_page import RegisterPage
from page_objects.registered_page import RegisteredPage


class TestRegisterSuccess:

    @pytest.mark.success
    @pytest.mark.execute_registration
    def test_register_success(self, driver):
        username = str(uuid.uuid4())
        password = 'passw123!'
        register_page = RegisterPage(driver)
        register_page.open()
        assert register_page.get_active_nav_name() == 'Create Account', \
            'The "Create Account" nav element should be active'
        register_page.execute_registration(username, password, password)
        registered_page = RegisteredPage(driver)
        registered_page.wait_for_user_profile()
        assert driver.current_url == registered_page.expected_url, \
            'unexpected url for registration success'
        assert registered_page.get_active_nav_name() == 'Profile', \
            'the "Profile" nav element should be active'
        assert registered_page.logged_in_user == username, \
            'the incorrect username is displayed on the profile page'
        assert registered_page.user_status == 'Status: USER', \
            'the user status is incorrect'

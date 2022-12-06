import uuid

from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class RegisterPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__error_message_locator = (By.XPATH, '//*[@id="errors"]/li')
        self.__password_locator = (By.XPATH, '//*[@id="password"]')
        self.__password_confirm_locator = (By.XPATH, '//*[@id="passwordConfirm"]')
        self.__username_locator = (By.XPATH, '//*[@id="username"]')
        self.__registration_form_locator = (By.XPATH, '//*[@id="registrationForm"]')
        self.__registration_submit_button = (By.XPATH, '//*[@id="createAccount"]')
        self.__url = f'{self._base_url}/register'

    @property
    def expected_url(self) -> str:
        return self.__url

    def execute_registration(self, username: str, password: str, password_confirm: str):
        super()._wait_until_element_is_visible(self.__registration_form_locator)
        self._type_username(username)
        self._type_password(password)
        self._type_password_confirm(password_confirm)
        super()._click(self.__registration_submit_button)

    def open(self):
        super()._open_url(self.__url)

    def _type_username(self, username):
        super()._type(self.__username_locator, username)

    def _type_password(self, password):
        super()._type(self.__password_locator, password)

    def _type_password_confirm(self, password):
        super()._type(self.__password_confirm_locator, password)

    @property
    def register_error_text(self):
        return super()._get_text(self.__error_message_locator)

from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class LoginPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__error_message = (By.XPATH, '//*[@id="errorDiv"]/p')
        self.__login_button_locator = (By.XPATH, '//*[@id="login"]')
        self.__password_locator = (By.XPATH, '//*[@id="password"]')
        self.__username_locator = (By.XPATH, '//*[@id="username"]')
        self.__login_form_locator = (By.XPATH, '//*[@id="loginForm"]')
        self.__url = f'{self._base_url}/login'

    def open(self):
        super()._open_url(self.__url)

    def _type_username(self, username: str):
        super()._type(self.__username_locator, username)

    def _type_password(self, password: str):
        super()._type(self.__password_locator, password)

    def execute_login(self, username: str, password: str):
        super()._wait_until_element_is_visible(self.__login_form_locator, 2)
        super()._wait_until_element_is_visible(self.__username_locator, 1)
        self._type_username(username)
        super()._wait_until_element_is_visible(self.__password_locator, 1)
        self._type_password(password)
        super()._wait_until_element_is_visible(self.__login_button_locator, 1)
        super()._click(self.__login_button_locator)

    @property
    def expected_url(self):
        return self.__url

    @property
    def login_error_text(self):
        return super()._get_text(self.__error_message)


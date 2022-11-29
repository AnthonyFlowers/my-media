from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class LoginPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__submit_button_locator = (By.XPATH, '//*[@id="root"]/div/div/form/div[3]/button')
        self.__password_locator = (By.XPATH, '//*[@id="password"]')
        self.__username_locator = (By.XPATH, '//*[@id="username"]')
        self.__url = f'{self._base_url}/login'

    def open(self):
        super()._open_url(self.__url)

    def _type_username(self, username: str):
        super()._type(self.__username_locator, username)

    def _type_password(self, password: str):
        super()._type(self.__password_locator, password)

    def execute_login(self, username: str, password: str):
        super()._wait_until_element_is_visible(self.__username_locator, 1)
        self._type_username(username)
        super()._wait_until_element_is_visible(self.__password_locator, 1)
        self._type_password(password)
        super()._wait_until_element_is_visible(self.__submit_button_locator, 1)
        super()._click(self.__submit_button_locator)


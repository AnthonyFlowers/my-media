from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class RegisterPage(BasePage):
    def __init__(self, driver):
        super().__init__(self, driver)
        self.__error_message = (By.XPATH, '//*[@id="errorDiv"]/p')
        self.__create_account_button_locator = (By.XPATH, '//*[@id="createAccount"]')
        self.__password_locator = (By.XPATH, '//*[@id="password"]')
        self.__password_confirm_locator = (By.XPATH, '//*[@id="passwordConfirm"]')
        self.__username_locator = (By.XPATH, '//*[@id="username"]')
        self.__registration_form_locator = (By.XPATH, '//*[@id="registrationForm"]')
        self.__url = f'{self._base_url}/register'

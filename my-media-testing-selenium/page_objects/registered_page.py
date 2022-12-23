from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class RegisteredPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__username_locator = (By.XPATH, '//*[@id="username"]')
        self.__status_locator = (By.XPATH, '//*[@id="status"]')
        self.__movie_stats = (By.XPATH, '//*[@id="profileMovieStats"]')
        self.__url = f'{self._base_url}/profile'

    @property
    def expected_url(self) -> str:
        return self.__url

    @property
    def logged_in_user(self) -> str:
        return super()._get_text(self.__username_locator)

    @property
    def user_status(self) -> str:
        return super()._get_text(self.__status_locator)

    def wait_for_user_profile(self):
        super()._wait_until_element_is_visible(self.__username_locator)
        super()._wait_until_element_is_visible(self.__status_locator)
        super()._wait_until_element_is_visible(self.__movie_stats)

from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage, NavItems


class LoggedInPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__movies_div = (By.XPATH, '//*[@id="movies"]')
        self._url = f'{self._base_url}/movies'

    @property
    def expected_url(self) -> str:
        return self._url

    def wait_for_movies(self, time: int = 10):
        super()._wait_until_element_is_visible(self.__movies_div, time)


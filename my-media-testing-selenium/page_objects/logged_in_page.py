from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage


class LoggedInPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__movies_div = (By.XPATH, '//*[@id="movies"]')
        self.__selected_nav = (
            By.XPATH,
            '//*[@id="root"]/div/nav/ul/li/a[@class="btn-nav btn-nav-active"]'
        )
        self._url = f'{self._base_url}/movies'

    @property
    def expected_url(self) -> str:
        return self._url

    def get_active_nav_name(self) -> str:
        return super()._get_text(self.__selected_nav, 3)

    def wait_for_movies(self, time: int = 10):
        super()._wait_until_element_is_visible(self.__movies_div, time)

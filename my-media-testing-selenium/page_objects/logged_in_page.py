from selenium.webdriver.common.by import By

from page_objects.base_page import BasePage, NavItems


class LoggedInPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__profile_locator = (By.XPATH, )
        self.__url = f'{self._base_url}/profile'

    @property
    def expected_url(self) -> str:
        return self.__url

    def wait_for_profile_nav_item(self, time: int = 3):
        super()._wait_until_element_is_visible(NavItems.PROFILE.value)


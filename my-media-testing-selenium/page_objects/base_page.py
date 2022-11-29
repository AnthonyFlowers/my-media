from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webdriver import WebDriver
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec


class BasePage:
    def __init__(self, driver: WebDriver):
        self.__selected_nav = (
            By.XPATH,
            '//*[@id="root"]/div/nav/ul/li/a[@class="btn-nav btn-nav-active"]'
        )
        self._base_url = 'http://localhost:3000'
        self._driver = driver

    def _find(self, locator: tuple) -> WebElement:
        return self._driver.find_element(*locator)

    def _type(self, locator: tuple, text: str, time: int = 5):
        self._wait_until_element_is_visible(locator, time)
        self._find(locator).send_keys(text)

    def _clear(self, locator: tuple, time: int = 5):
        self._wait_until_element_is_visible(locator, time)
        self._find(locator).clear()

    def _wait_until_element_is_visible(self, locator: tuple, time: int = 5):
        wait = WebDriverWait(self._driver, time)
        wait.until(ec.visibility_of_element_located(locator))

    def _click(self, locator: tuple, time: int = 5):
        self._wait_until_element_is_visible(locator, time)
        self._find(locator).click()

    @property
    def current_url(self) -> str:
        return self._driver.current_url

    def _is_displayed(self, locator: tuple) -> bool:
        try:
            return self._find(locator).is_displayed()
        except NoSuchElementException:
            return False

    def _open_url(self, url: str):
        self._driver.get(url)

    def _get_text(self, locator: tuple, time: int = 5) -> str:
        self._wait_until_element_is_visible(locator, time)
        return self._find(locator).text

    def _get_attribute(self, locator: tuple, attribute: str, time: int = 5):
        self._wait_until_element_is_visible(locator, time)
        return self._find(locator).get_attribute(attribute)

    def _wait_until_element_is_clickable(self, locator: tuple, time: int = 5):
        wait = WebDriverWait(self._driver, time)
        wait.until(ec.element_to_be_clickable(locator))

    def get_active_nav_name(self) -> str:
        return self._get_text(self.__selected_nav, 3)

from page_objects.base_page import BasePage


class MoviesPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.__url = f'{self._base_url}/movies'

    def open(self):
        super()._open_url(self.__url)


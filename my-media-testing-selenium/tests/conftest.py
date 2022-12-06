import pytest
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeDriver
from selenium.webdriver.firefox.service import Service as FirefoxService
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager


# @pytest.fixture(params=['chrome', 'firefox'])
@pytest.fixture(params=['chrome'])
def driver(request):
    browser = request.param
    print(f'creating {browser} driver')
    if browser == 'chrome':
        my_driver = webdriver.Chrome(service=ChromeDriver(ChromeDriverManager().install()))
    elif browser == 'firefox':
        my_driver = webdriver.Firefox(service=FirefoxService(GeckoDriverManager().install()))
    else:
        raise TypeError(f'Expected "chrome" or "firefox", but got {browser}')
    yield my_driver
    print(f'closing {browser} driver')
    my_driver.quit()


def pytest_addoption(parser):
    parser.addoption(
        '--browser', action='store', default='chrome', help='browser to execute tests (chrome or firefox)'
    )

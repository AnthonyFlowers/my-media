# My Media
An app for tracking a user's media they have watched (and read/listened to eventually). 
A user has the option of exploring the current available movies and tv shows through multiple pages. The Home page shows 6 of the most recent movies and tv shows in the database. There is a page for both movies and tv shows that lists the available media to choose from. When logged in a user will have access to a profile page and can add media from the movies and tv show pages along with pages to manage their tracked media.

This project contains a Spring Boot REST API, a React frontend, and uses a MySql database for data persistence. CSS styling was done using Tailwind a CSS framework. The movie and tv show data were downloaded from kaggle and loaded into the database using a Python script. Also included are some tests using jUnit, postman, and selenium. 

## Database setup [my-media-sql](https://github.com/AnthonyFlowers/my-media-api/tree/main/my-media-sql)
This app requires a MySQL database. Using the `production.sql` script file, run it on a database and use it in the Spring Boot API section when setting up the datasource.

## React Client [my-media-client](/my-media-client)
A single page React app is used as the frontend. Using a handfull of pages a user can navigate between searching for movies, finding tv shows, updating their tracked media and a basic profile page with some stats based on their media.
- BrowserRouter from react-router-dom is used for single page navigation
- JWT tokens are stored to handle user sessions
- Tailwind is used for CSS styling

## Spring Boot API my-media-api
moved [my-media-api](https://github.com/AnthonyFlowers/my-media-api)

## Python Selenium [my-media-testing-selenium](/my-media-testing-selenium)
Using Python and Selenium, some unit tests were created to test out the React UI tests include:
- Creating an account
- Making sure navigation buttons are correctly highlighted
- Login form errors


## Future Improvements
- [ ] Example images in the README
- [ ] Improve overall UI/UX
- [ ] Adding more media types like books, music, comic books, and manga
- [ ] Better media filtering and sorting

Community Features
- [ ] Friends list
- [ ] Groups
- [ ] Social media sharing

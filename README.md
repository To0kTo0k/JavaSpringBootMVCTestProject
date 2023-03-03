# JavaSpringBootMVCTestProject



Service functions:
1. User photo uploader (put JPG-photo on server, save photo on server catalog, server response - photo URI) - DONE!
(Code taken from Spring dev's Github repository and adapted for this project)
2. Add new user (put user's personal data on server, save data on DB, response - unique user's ID) - DONE!
3. Get existing user's data (put unique user's ID on server, get data from DB, response - user's personal data)
4. User's status update (put unique user's ID and new status on server, update user's status in DB, response - unique user's status and new and old user's status) - DONE!
5. Server statistic (put user's status or UNIX-timestamp on server, response - sorted data about users: status, name, photo URI and timestamp) - DONE!
(Request to DB was made with Spring Data JPA)

Additional functions (not claimed in order):
Input data validation was made with Jakarta.validation.constraints. Used annotations and regex.
Was made DTO for comfort response and object/DTO or DTO/object convertation.


Primary requirements:
1. Exception handling (made with ControllerAdvice) - DONE!
2. Request priority - WASTED|HAVE NO IDEA HOW TO DO!
3. All in JSON - DONE!
4. RESTful - DONE!
5. DB access (made with Spring Data JPA) - DONE!

Secondary requirements:
1. Tests - WASTED!
2. For highload system - HAVE NO IDEA!
3. Code documentation - DONE!

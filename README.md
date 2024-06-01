## To run the project Create DB table using MYSQL w change the database url, username and password according to your configration (that is in the application properties file)
##  Also pay attention to the running port in in the application properties file
### Samples of API calls to use direct on postman:-
### GET:  http://localhost:8081/api/books
### GET: http://localhost:8081/api/patron
### POST:  http://localhost:8081/api/books 
###   body: [
    {
        "id": 105,
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "publicationYear": "1105",
        "isbn": "9780743273542"
    },
    {
        "id": 106,
        "title": "The Great Gatsby",
        "author": "F. Scott Fitzgerald",
        "publicationYear": "1926",
        "isbn": "9780743273542"
    },
    {
        "id": 107,
        "title": "The Great Gatsby2",
        "author": "F. Scott Fitzgerald2",
        "publicationYear": "1926",
        "isbn": "9780743273562"
    }
]
PUT: http://localhost:8081/api/patron/return/105/patron/2

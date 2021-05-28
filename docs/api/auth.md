
### Login (POST /auth/login)

Example Request
```json
{
    "username": "willnode@wellosoft.net",
    "password": "user"
}
```

Example Response
```json
{
    "status": "success",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjE2OTU3ODc4LCJleHAiOjE2MTY5NTk2Nzh9._Qy3XbnBRnQ3JadzhFyQnqzvB34EzSvi1VumNDKQYCw"
}
```

### Register (POST /auth/register)


Example Request
```json
{
    "name": "Wildan",
    "email": "willnode@wellosoft.net",
    "password": "1234",
}
```

Example Response
```json
{
    "status": "success",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjE2OTU3ODc4LCJleHAiOjE2MTY5NTk2Nzh9._Qy3XbnBRnQ3JadzhFyQnqzvB34EzSvi1VumNDKQYCw"
}
```

> Setelah ini, semua endpoint membutuhkan authorization bearer

### Info (GET /auth/info)

```json
{
    "status": "success",
    "id": 1,
    "name": "Wildan",
    "email": "willnode@wellosoft.net",
}
```

### Update (POST /auth/info)

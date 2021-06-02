
### Fetch (GET /wall/?start_from=[:start_from])

```json
{
    "status": "success",
    "data": [
        {
            "id": "Judul",
            "excerpt": "Lalalaland",
            "rating": 1,
            "raters": 2,
            "viewers": 3,
            "created_at": "2020-02-02T01:01:01Z",
        }
    ]
}
```

### Get Detail (GET /wall/:1)

```json
{
    "status": "success",
    "data": {
        "id": "Judul",
        "user": {
            "name": "Lalala Fans"
        },
        "content": "Lalalaland",
        "source": "lalalaland.com",
        "rating": 1,
        "raters": 2,
        "viewers": 3,
        "created_at": "2020-02-02T01:01:01Z",
        "user_rating": null
    }
}
```

### Rate The Wall (POST /wall/:id)

```json
{
    "action": "rate",
    "rating": 4
}
```
export default {
    "database": {
        "main": {
            "database": "beehive_db",
            "host": "localhost",
            "user": "root",
            "password": ""
        }
    },
    "jwt": {
        "secret": "please run: require('crypto').randomBytes(64).toString('hex')",
        "expiresIn": "18000s",
    },
}
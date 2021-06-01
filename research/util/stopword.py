# Py Module to find stop word

from .db import mydb

# Load from DB
mycursor = mydb.cursor()
mycursor.execute("SELECT * FROM stopwords")
stopwords = set([x[0] for x in mycursor.fetchall()])

def filterword(word=''):
    word = word.strip(' -_')
    if len(word) == 0:
        word = None
    elif word[0].isdecimal():
        word = None
    elif word in stopwords:
        word = None
    return word

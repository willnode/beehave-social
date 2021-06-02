# Perform hourly cron job to check and index wall
# done using NLP: Sastrawi & TF-IDF

from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from util.db import mydb
from util.stopword import filterword
import re

factory = StemmerFactory()
stemmer = factory.create_stemmer()
mycursor = mydb.cursor()

sql = "SELECT id, title, content FROM wall WHERE keyword IS NULL"
mycursor.execute(sql)
walls = mycursor.fetchall()
cleanr = re.compile('<.*?>')
for [id, title, content] in walls:
    word = re.sub(cleanr, '', title + ' ' + content)
    stem = stemmer.stem(word).split()
    stem = [filterword(x) for x in stem]
    stem = [x for x in stem if x is not None]
    keywords = ' '.join(stem)
    mycursor.execute("UPDATE wall SET keyword=%s WHERE id=%s", [keywords, id])

mydb.commit()
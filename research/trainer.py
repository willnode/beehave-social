# Perform daily cron job to build our predictor model and rerank the walls
# where input is keyword score in tf idf and output is predicted goodness (avg rating) and hotness (raters/viewers)
# the final score will be based on mix of (goodness+hotness+age) and users get refreshed wall everyday.

from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from .util.db import mydb
from collections import Counter
from .extractor import mycursor # also execute extractor first
from sklearn.feature_extraction.text import TfidfVectorizer

sql = "SELECT id, keyword FROM wall WHERE keyword IS NOT NULL and created_at > now() - INTERVAL 60 DAY"
walls = mycursor.fetchall()

for [id, keywords] in walls:
    stem = dict(Counter(keywords.split()))

# TODO: The CNN




# Perform daily cron job to build our predictor model and rerank the walls
# where input is keyword score in tf idf and output is predicted goodness (avg rating) and hotness (raters/viewers)
# the final score will be based on mix of (goodness+hotness+age) and users get refreshed wall everyday.

from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
#from .util.db import mydb
from collections import Counter
#from .extractor import mycursor # also execute extractor first
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neural_network import MLPClassifier
import numpy as np

"""sql = "SELECT id, keyword FROM wall WHERE keyword IS NOT NULL and created_at > now() - INTERVAL 60 DAY"
walls = mycursor.fetchall()

for [id, keywords] in walls:
    stem = dict(Counter(keywords.split()))"""

# TODO: The CNN
#train_x = np.array([['depresi','lelah'], ['keren','kaya','nyaman'], ['bijak','tentram'], ['sukses','hebat','usaha']],dtype=object)
#train_y = np.array([[3,11,56], [4,28,100], [4,39,120], [5,19,78]],dtype=object)

train_x = [['depresi','lelah'], ['keren','kaya','nyaman'], ['bijak','tentram'], ['sukses','hebat','usaha']]
train_y = [[3,11,56], [4,28,100], [4,39,120], [5,19,78]]

train_x = np.array(train_x, dtype=object)
train_x = train_x.reshape(1,-1)
from sklearn.feature_extraction.text import TfidfTransformer
tfidfconverter = TfidfTransformer()
train_x = tfidfconverter.fit_transform(train_x).toarray()


print(train_x)
clf = MLPClassifier(solver='adam', hidden_layer_sizes=(10, 3))

clf.fit(train_x, train_y)




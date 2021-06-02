# Perform daily cron job to build our predictor model and rerank the walls
# where input is keyword score in tf idf and output is predicted goodness (avg rating) and hotness (raters/viewers)
# the final score will be based on mix of (goodness+hotness+age) and users get refreshed wall everyday.

from util.db import mydb
import tensorflow as tf
from collections import Counter
from extractor import mycursor # also execute extractor first
from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer, TfidfTransformer
from sklearn.model_selection import train_test_split
from tensorflow.keras import datasets, layers, models
from sklearn.preprocessing import MinMaxScaler
import numpy as np

# fetch from DB
sql = ("SELECT id, keyword,rating,raters,viewers,TIMESTAMPDIFF(DAY, created_at, NOW())"
    "FROM wall WHERE keyword IS NOT NULL and created_at > now() - INTERVAL 60 DAY")
mycursor.execute(sql)

walls = mycursor.fetchall()

# Grab X, Y
ids = [x[0] for x in walls]
corpus = [x[1] for x in walls]
statistics = [x[2:5] for x in walls]
age = [x[5] for x in walls]
scaler = MinMaxScaler()
X = CountVectorizer().fit_transform(corpus)
X = TfidfTransformer().fit_transform(X)
X = np.array(X.toarray())
Y = np.array(statistics)
Y = scaler.fit_transform(Y)

# Compile
model = models.Sequential([
        tf.keras.layers.Dense(len(ids), activation="relu"),
        tf.keras.layers.Dense(32, activation="relu"),
        tf.keras.layers.Dense(24, activation="relu"),
        tf.keras.layers.Dense(10, activation="relu"),
        tf.keras.layers.Dense(3, activation='sigmoid')
    ])
model.compile(loss=tf.keras.losses.MeanSquaredError(), optimizer='adam', metrics=['accuracy'])

# train
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.4)
history = model.fit(X_train, Y_train, epochs=10, validation_data=(X_test, Y_test))

predicted = scaler.inverse_transform(model.predict(X))
# push to mysql
sql = "UPDATE wall SET predicted_rating = %s, predicted_raters=%s, predicted_viewers=%s, predicted_score=%s  WHERE id=%s"
for k, v in enumerate(predicted.tolist()):
    mycursor.execute(sql, [*v,v[0]*v[2]/(age[k]+1), ids[k]])
mydb.commit()
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
import tensorflow as tf
from tensorflow.python.framework import dtypes
import keras

"""sql = "SELECT id, keyword FROM wall WHERE keyword IS NOT NULL and created_at > now() - INTERVAL 60 DAY"
walls = mycursor.fetchall()

for [id, keywords] in walls:
    stem = dict(Counter(keywords.split()))"""

# TODO: The CNN
#train_x = np.array([['depresi','lelah'], ['keren','kaya','nyaman'], ['bijak','tentram'], ['sukses','hebat','usaha']],dtype=object)
#train_y = np.array([[3,11,56], [4,28,100], [4,39,120], [5,19,78]],dtype=object)

"""train_x = np.array(train_x, dtype=object)
train_x = train_x.reshape(1,-1)
from sklearn.feature_extraction.text import TfidfTransformer
tfidfconverter = TfidfTransformer()
train_x = tfidfconverter.fit_transform(train_x)


print(train_x)"""
"""clf = MLPClassifier(solver='adam', hidden_layer_sizes=(10, 3))

clf.fit(train_x, train_y)
"""

train_x = [['depresi','lelah'], ['keren','kaya','nyaman'], ['bijak','tentram'], ['sukses','hebat','usaha']]
train_y = [[3,11,56], [4,28,100], [4,39,120], [5,19,78]]

def my_gen():
    x = train_x
    y = train_y
    yield x,y

dataset = tf.data.Dataset.from_generator(my_gen, 
                                        output_types=({'x':tf.string}, {'y':tf.int32}), 
                                        output_shapes=({'x':tf.TensorShape([None])}, {'y':tf.TensorShape([None])}))

test_x = [['lelah','depresi'], ['hebat','keren','sukses']]
test_y = [[2,14,70], [4,22,90]]

def my_gen2():
    x = test_x
    y = test_y
    yield x,y

dataset_val = tf.data.Dataset.from_generator(my_gen2, 
                                            output_types=({'x':tf.string}, {'y':tf.int32}), 
                                            output_shapes=({'x':tf.TensorShape([None])}, {'y':tf.TensorShape([None])}))

"""dataset_x = tf.data.Dataset.from_generator(lambda : train_x, tf.string, output_shapes=[None])
dataset_y = tf.data.Dataset.from_generator(lambda : train_y, tf.int32, output_shapes=[None])
dataset_x_test = tf.data.Dataset.from_generator(lambda : test_x, tf.string, output_shapes=[None])
dataset_y_test = tf.data.Dataset.from_generator(lambda : test_y, tf.int32, output_shapes=[None])"""
#print(dataset_x)
#input_dim = dataset_x.shape[1]
model = keras.Sequential()
model.add(tf.keras.layers.Dense(10, input_shape=(None,), activation="relu"))
model.add(tf.keras.layers.Dense(1, activation='sigmoid'))
"""model = keras.Sequential(
    [
        tf.keras.layers.Dense(10, input_shape=(None,), activation="relu"),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ]
)"""
model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
#model.summary()

history = model.fit(dataset, epochs=100, verbose=False, validation_data=dataset_val)

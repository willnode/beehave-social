import numpy as np
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from Sastrawi.StopWordRemover.StopWordRemoverFactory import StopWordRemoverFactory
import pandas as pd
from scipy.sparse import data
from sklearn.feature_extraction.text import TfidfVectorizer

dataset = [
    "Mengapa tuhan aku dilahirkan berbeda? Aku hanya ingin hidup bahagia seperti teman-temanku, bermain bersama keluarga lengkap dengan ayah, ibu dan saudara",
    "Saya mengalami masalah finansial keluarga, hingga menyebabkan perkelahian besar antara saya dan pasangan. Saya sangat depresi dan butuh saran dalam mengatur keuangan keluarga yang baik",
    "2 tahun belakang saya mengalami masalah depresi karena tugas kuliah yang sangat menumpuk. Bulan-bulan terakhir ini, masalah ini dapat mengakibatkan terganggunya fisik saya hingga sering jatuh sakit.",
    "Saya tidak menangis karena merasa semuanya tidak layak, saya menangis hanya karena merasa hancur oleh kebenaran siapa diri saya sebenarnya.",
    "Rasanya seperti ini tidak akan pernah berakhir. Dunia tidak akan berhenti runtuh sampai tidak ada yang tersisa dariku kecuali debu.",
    "Saya sedih sepanjang waktu dan kesedihan itu terlalu sangat berat sehingga saya tidak dapat melepaskan diri darinya.",
    "Wanita menangis bukan karena lemah tapi dimana dia sedang merasa lelah dengan keadaannya. Bolehkah aku mengeluh? Jujur aku lelah dengan keadaan seperti ini."
]

dataframe = pd.DataFrame(dataset, columns=['content'])
dataframe.index.name = 'id'
#print(dataframe)
factory = StemmerFactory()
stemmer = factory.create_stemmer()

stop_factory = StopWordRemoverFactory()
more_stopwords=['aku','saya','dan', 'ini','itu','tidak', 'akan']
data = stop_factory.get_stop_words()+more_stopwords
stopword = stop_factory.create_stop_word_remover()
#print(data)

for i in range(len(dataset)):
    dataset[i] = stemmer.stem(dataset[i])
    #print("sebelum = " + dataset[i])
    dataset[i] = stopword.remove(dataset[i])
    #print("sesudah = " + dataset[i])

tfIdfVectorizer = TfidfVectorizer(use_idf=True)
tfIdf = tfIdfVectorizer.fit_transform(dataset)

keywords = []
for i in range(len(dataframe)):
    df = pd.DataFrame(tfIdf[i].T.todense(), index=tfIdfVectorizer.get_feature_names(), columns=["TF-IDF"])
    df = df[df['TF-IDF']!=0].sort_values('TF-IDF', ascending=False)
    keyword = df.index.tolist()
    keywords.append(keyword)

dataframe['keywords'] = keywords

dataframe.to_csv("wallcoba.csv")
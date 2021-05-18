#Belum bener yakk

from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from Sastrawi.StopWordRemover.StopWordRemoverFactory import StopWordRemoverFactory
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer

dataset = [
    "Mengapa tuhan aku dilahirkan berbeda? Aku hanya ingin hidup bahagia seperti teman-temanku, bermain bersama keluarga lengkap dengan ayah, ibu dan saudara",
    "Saya mengalami masalah finansial keluarga, hingga menyebabkan perkelahian besar antara saya dan pasangan. Saya sangat depresi dan butuh saran dalam mengatur keuangan keluarga yang baik",
    "2 tahun belakang saya mengalami masalah depresi karena tugas kuliah yang sangat menumpuk. Bulan-bulan terakhir ini, masalah ini dapat mengakibatkan terganggunya fisik saya hingga sering jatuh sakit."
]

factory = StemmerFactory()
stemmer = factory.create_stemmer()
factory2 = StopWordRemoverFactory()
stopword = factory2.create_stop_word_remover()
tfIdfVectorizer = TfidfVectorizer(use_idf=True)
tfIdf = tfIdfVectorizer.fit_transform(dataset)

for i in range(len(dataset)):
    dataset[i] = stemmer.stem(dataset[i])
    dataset[i] = stopword.remove(dataset[i])
    
for i in range(len(dataset)):
    df = pd.DataFrame(tfIdf[i].T.todense(), index=tfIdfVectorizer.get_feature_names(), columns=["TF-IDF"])
    df = df.sort_values('TF-IDF', ascending=False)

print(dataset)


print (df.head(100))
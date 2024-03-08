import numpy as np
import scipy as sp
import matplotlib as mpl
import matplotlib.cm as cm
import matplotlib.pyplot as plt
import pandas as pd
from sklearn import preprocessing

# X_train = np.array([[ 1., -1.,  2.],
#                     [ 2.,  0.,  0.],
#                     [ 0.,  1., -1.]])

# max_abs_scaler = preprocessing.MaxAbsScaler()
# X_train_maxabs = max_abs_scaler.fit_transform(X_train)
# print(X_train_maxabs)

X = [[ 1., -1.,  2.],
     [ 2.,  0.,  0.],
     [ 0.,  1., -1.]]

X_normalized = preprocessing.normalize(X, norm='l1')

print(X_normalized)
# Positive and Unlabeled Learning for Prioritization

Our 2013 publication describing this work:
Wagner, A. H. et al. Prioritization of retinal disease genes: an integrative approach. _Hum Mutat_ 34, 853-859, [doi:10.1002/humu.22317](https://onlinelibrary.wiley.com/doi/abs/10.1002/humu.22317) (2013).


Here is a compiled and packaged version of our code for our project. This runs the logistic regression method for producing probabilities for each gene.  The output could then be redirected into a text file and then sorted in decreasing order of probabilities to get the ranking.

Feature vectors are for the training of a retinal degenerative disease model, as described in our paper.
Classifier results are from training features.csv using PULP with the respective regression function.

Usage:

java -jar -Xmx2G CG_Method_LogisticRegression.jar features.csv

OR

java -jar -Xmx2G CG_Method_LogisticRegression.jar features.csv > output.csv

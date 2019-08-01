import numpy as np 
import pylab 
import scipy.stats as stats
import pickle
import matplotlib.pyplot as plt


with open("energyData.pickle", 'rb') as f:
    energyData = pickle.load(f)

def printMannWhitneyU(data1, data2, message):
    print("\nMann Whitney U Test\n%s" % message)

    print("Two-Sided: U = %s p = %s " % stats.mannwhitneyu(data1, 
        data2, alternative="two-sided"))

    print("Less: U = %s p = %s " % stats.mannwhitneyu(data1,
        data2, alternative="less"))

    print("Greater: U = %s p = %s " % stats.mannwhitneyu(data1,
        data2, alternative="greater"))  


    precentage = ( (100 * np.mean(data1)) / np.mean(data2) ) - 100
    print("n1: %s n2: %s" % (len(data1), len(data2)))
    print("Mean1: %s Mean2: %s Compare: %s" % (np.mean(data1), np.mean(data2), precentage))

for key in energyData:
    print(key)
    printMannWhitneyU(energyData[key], energyData["classmate-original-2-150000-"], key)
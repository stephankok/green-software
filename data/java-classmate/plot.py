import pickle
import matplotlib.pyplot as plt
import numpy as np
from decimal import Decimal

def plotMedianOnAxes(ax, medians):
    # Plot median on top of boxplot
    i=0.7
    ax.text(0, 1 , "Mean (J):\nCount:", transform=ax.get_xaxis_transform(), size=20)
    for (median, size) in medians:
        scFormat = '%.2E\n%i' % (Decimal(median), size)
        ax.text(i, 1 , scFormat, transform=ax.get_xaxis_transform(), size=20)
        i+=1

def createLabelFromKey(key):
    # Create label for measuement
    # programName = key[:key.find("-log4j")]
    # if "classmate" in programName:
    #     programName = "cm-" + programName[programName.find("-")+1:]
    if "duplicate" in key:
        return "DUP"
    if "original" in key:
        return "Or"
    if "longmethod" in key:
        return "LM"
    if "godclass" in key:
        return "LC"
    if "idle" in key:
        return "IDLE"
    return key

def createPlot(medians, identifiers, boxplots):
    fig, ax = plt.subplots()
    # plotMedianOnAxes(ax, medians)
    ax.set_xticklabels(identifiers)
    ax.boxplot(boxplots)
    ax.grid(which='major', linestyle='--', linewidth='0.5')
    plt.xticks(size = 15)
    plt.yticks(size = 15)
    plt.ylabel("Energy (J)", size=20, fontweight="bold")
    plt.xlabel("Treatment", size=20, fontweight="bold")
    plt.title("FasterXML/java-classmate", size=30, fontweight="bold")
    plt.show()

def energyPlot():
    # Load data from pickle
    with open("energyData.pickle", 'rb') as f:
        energyData = pickle.load(f)

    # Create data
    boxplots = []
    medians = []
    identifiers =[]
    for key in energyData:
        label = createLabelFromKey(key)
        if "Or" == label:
            identifiers.insert(1,label)
            boxplots.insert(1,energyData[key])
        else:
            identifiers.append(label)
            boxplots.append(energyData[key])
        medians.append((np.mean(energyData[key]),len(energyData[key])))

    # Plot figure
    createPlot(medians, identifiers, boxplots)
energyPlot()
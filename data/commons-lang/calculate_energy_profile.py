import pandas
import os
import pickle
import numpy as np

result = []
labels = ['Name', 'real Joule', 'real kWh','Joule(surface)', 'kWh(surface)',
          'Mean', 'Median', 'Stddev', 'time(ms)', 'time(string)']

# probably making separate result directories for a single file,
# because of running a program multiple times
path = os.getcwd() + '/energy_profiles/'
# path = "/home/stephan/Desktop/data/results/"
files = os.listdir(path)
files.sort()
print(files)

# https://www.statisticshowto.datasciencecentral.com/combined-mean/
meanIdlePower=0
totalMeasurePoints=0
for name in files:
    if os.path.isdir(path+name):
        continue
    if "idle" in name:    
        data = pandas.read_csv(path + name, sep='\s*,\s*',
                           engine='python')

        idlepower = data['Power(W)'].mean()
        measurePoints = len(data['Power(W)'])

        meanIdlePower = ( (meanIdlePower * totalMeasurePoints) + (idlepower * measurePoints) ) / (totalMeasurePoints + measurePoints)
        totalMeasurePoints += measurePoints


energyData = {}
powerData = {}
timeData = {}
powerDataMean = {}
done=len(files)
loc=0
for name in files:
    if os.path.isdir(path+name):
        continue
    precentage=(loc*100.0) / done
    if (precentage%10 < 0.5):
        print(precentage)
    loc+=1

    data = pandas.read_csv(path + name, sep='\s*,\s*',
                           engine='python')

    # Calculate Duration in Milliesecond, but also nicely formated (caution
    # days/weeks/months not implemented because we know that the programs
    # are not running that long)
    timeMs = data['TimeNODE(mS)'][data.index[-1]] - data['TimeNODE(mS)'][data.index[0]]
    milli = timeMs % 1000
    try:
        sec = (int(timeMs / 1000) % 60)
        min = (int(timeMs / (1000*60)) % 60)
        hour = (int(timeMs / (1000*60*60)) % 60)
        timeString = str(hour) + " Hours, " + str(min) + " Minutes, " + \
            str(sec) + " Seconds, " + str(milli) + " Milliseconds"
    except:
        print("TimeMS", timeMs)
        print(name)
        timeString = "error"
    # Calculate Energy Consumption in Joule and kWh by taking the
    # average between two points, calculating the surface and then
    # adding all the surfaces together
    surface = 0
    realSurface = 0
    for i in range(data.index[-1]):
        power = (data['Power(W)'][data.index[i]] +
                    data['Power(W)'][data.index[i+1]]) / 2
        time = data['TimeNODE(mS)'][data.index[i+1]] - data['TimeNODE(mS)'][data.index[i]]

        surface += power * time
        realSurface += (power - meanIdlePower) * time
    surfaceJ = surface/1000.0  # Make it in Joule
    surfacekWh = surfaceJ/3600000.0  # Make it in kWh

    realSurfaceJ = realSurface/1000.0  # Make it in Joule
    realSurfacekWh = realSurface/3600000.0  # Make it in kWh

    if name.endswith("idle.csv"):
        sortKey="idle"
    else:     
        index = name[::-1].find("-")
        sortKey =  name[:-index]

    if sortKey in energyData:
        energyData[sortKey].append(realSurfaceJ)
        for p in data["Power(W)"]:
            powerData[sortKey].append(p)
        powerDataMean[sortKey].append(np.mean(data["Power(W)"]))
        timeData[sortKey].append(timeMs / 1000)
    else:
        energyData[sortKey] = [realSurfaceJ]
        powerData[sortKey] = [p for p in data["Power(W)"]]
        powerDataMean[sortKey] = [np.mean(data["Power(W)"])]
        timeData[sortKey] = [timeMs/1000]

    # Calculate average and median of power
    mean = data['Power(W)'].mean()
    median = data['Power(W)'].median()
    stddev = data['Power(W)'].std()

    result.append((name, realSurfaceJ, realSurfacekWh, surfaceJ, surfacekWh,
                   mean, median, stddev, timeMs, timeString))


df = pandas.DataFrame.from_records(result, columns=labels)
df.to_csv('results.csv')

# Safe
with open('energyData.pickle', 'wb') as handle:
    pickle.dump(energyData, handle, protocol=pickle.HIGHEST_PROTOCOL)

with open('powerData.pickle', 'wb') as handle:
    pickle.dump(powerData, handle, protocol=pickle.HIGHEST_PROTOCOL)
    
with open('powerDataMean.pickle', 'wb') as handle:
    pickle.dump(powerDataMean, handle, protocol=pickle.HIGHEST_PROTOCOL)

with open('timeData.pickle', 'wb') as handle:
    pickle.dump(timeData, handle, protocol=pickle.HIGHEST_PROTOCOL)
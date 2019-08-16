# green-software
Master Thesis: 
The Impact of Refactoring Code Smells on the Energy Consumption of Java-based Open-source Software

By:
Stephan Kok

Date:
August 16, 2019

Abstract:
Code smells are code patterns that highlight code that is difficult to understand or that makes it more difficult to implement a new feature. 24 code smells have been identified by Martin Fowler and have gained the attention of software engineers. While code smells have shown to increase the maintainability of the software, we have to consider what the impact of refactoring these code smells is on the energy efficiency of the software. This work has shown an initial investigation of the impact of refactoring code smells on the energy consumption. It is followed by an empirical study on the impact of refactoring three code smells on the energy consumption. We conducted controlled experiments on three open-source Java projects. The projects have been refactored using JDeodorant as code smell detection tool. We have measured the energy consumption by using a power distribution unit and comparing the difference between the original and refactored version. The workload is created by running all the unit tests in the project. We found that refactoring long methods led to an increase in energy consumption of up-to 3.29%, refactoring large classes has shown an increase of 1.35% and when refactoring for duplicate code when have found both an increase and decrease in energy consumption. This work has shown that refactoring code smells has an impact on the energy consumption and further research is needed to investigate the impact of the other code smells.



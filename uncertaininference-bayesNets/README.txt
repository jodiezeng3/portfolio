Jodie Zeng
jzeng9@u.rochester.edu
CSC 242 Project 3

My project runs following all requirements using the implementation code given to us by the Professor. To build my project, I entered this into the command terminal I initiate in the folder containing all the files for my project:

javac AIMAAlarmNetwork.java AIMAWetGrassNetwork.java ArrayMap.java ArraySet.java Assignment.java BayesianNetwork.java BooleanDomain.java BooleanValue.java BooleanVariable.java CPT.java CPTFormatException.java Distribution.java Domain.java Inferencer.java LikelihoodWeightingInferencer.java NamedVariable.java ParserException.java RandomVariable.java RejectionSamplingInferencer.java StringValue.java UncertainInference.java UncertainInference_approx.java Value.java ValueDomain.java XMLBIFParser.java XMLBIFPrinter.java

In that file I also have the xml documents for the example bayes nets (my program does not parse bif files). To get the exact distribution using enumeration (with the aima-alarm example):

java UncertainInference aima-alarm.xml B J true M true

To get the approximate inference (both rejection sampling and likelihood weighting):

java UncertainInference_approx 100000 aima-alarm.xml B J true M true
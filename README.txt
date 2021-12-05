Genetic Algorithms - Traveling Salesman Problem
-----------------------------------------------

The purpose of this program is to display the advantages and disadvantages of the genetic algorithm, using the traveling salesman problem.

The program reads in the file of an adjacency matrix and extracts the data (locations, a location's cost to the other locations). Then, the genetic algorithm process is applied to an initial population size and for a number of generations, which can be manually inputted by the user. 

Like the name suggests, the genetic algorithm mimics real-life breeding and the overall intracacies of it. A mutation feature is also implemented to further replicate real-life genetics.

The program outputs a list of the information for each generation and it's fittest cost. The genetic algorithm does not always guarantee the solution with the lowest cost; however, the time complexity compared to a brute-force method, especially when traveling salesman problem gets more complex in terms of locations, is a significant trade-off for accuracy (or at least a guaranteed lowest-cost solution every execution).


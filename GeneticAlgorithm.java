import java.util.ArrayList;
import java.util.Collections;

public class GeneticAlgorithm {
	
	private ArrayList<String> locations;
	private ArrayList<ArrayList<Integer>> adjArray;
		
	GeneticAlgorithm(ArrayList<String> locationList, ArrayList<ArrayList<Integer>> costAdjArray) {
		locations = locationList;
		adjArray = costAdjArray;
		System.out.println("Read Matrix with " + locations.size() + " nodes.");
		Integer costFirstToLast = adjArray.get(locations.indexOf(locations.get(0))).get(locations.indexOf(locations.get(locations.size() - 1)));
		Integer costFirstToFirst = adjArray.get(locations.indexOf(locations.get(0))).get(locations.indexOf(locations.get(0)));
		Integer costLocationInOrder = cost(locations);
		System.out.println("Cost of " + locations.get(0) + " -> " + locations.get(locations.size() - 1) + " is: " + costFirstToLast);
		System.out.println("Cost of " + locations.get(0) + " -> " + locations.get(0) + " is: " + costFirstToFirst);
		System.out.println("Cost of " + locations + " is: " + costLocationInOrder + "\n");
	}
	
	
	/**
	 * Creates an initial population of routes based on the given locations.
	 * Utilizes the randomRoute() method to generate random routes.
	 * @param popSize: The amount of routes for the initial population and the proceeding generations.
	 * @return iniPop: The list holding a list of randomly generated routes.
	 */
	private ArrayList<ArrayList<String>> initialPopulation(int popSize) { //DONE
		ArrayList<ArrayList<String>> iniPop = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < popSize; i++) { 
			ArrayList<String> randomRoute = new ArrayList<String>(randomRoute());	//Create a random route and put it
			iniPop.add(randomRoute);												//in the set of initial routes.
		}
		return iniPop;
	}
	
	
	/**
	 * Creates a randomly generated route based on the given locations.
	 * Ensures each location is visited at least once and only once.
	 * @return route: A randomly generated route.
	 */
	private ArrayList<String> randomRoute() { 
		ArrayList<String> route = new ArrayList<String>(locations);		//A route is given the list of locations.
		for(int i = 0; i < route.size(); i++) {							//A random route is generated by simply swapping
			int random = (int) (Math.random() * (locations.size()));    //random elements (and therefore the locations) 
			Collections.swap(route, i, random);							//around as many times as the number of locations.
		}	
		return route;	
	}
	
	
	/**
	 * Calculates the entire cost of a single route given its sequence of locations.
	 * The masterArray adjacency array is utilized to help map and retrieve the costs.
	 * @param dnaStrand: The route to calculate the cost of.
	 * @return cost: The cost of the route.
	 */
	private Integer cost(ArrayList<String> dnaStrand) { 
		Integer cost = 0;
		int locationOne;
		int locationTwo;
		for(int i = 0; i < dnaStrand.size() - 1; i++) {									//Retrieve the indexes of the initial location						
			locationOne = locations.indexOf(dnaStrand.get(i));							//to the destined location to find the cost.
			locationTwo = locations.indexOf(dnaStrand.get(i + 1));							
			cost = cost + adjArray.get(locationOne).get(locationTwo);							
		}
		locationOne = locations.indexOf(dnaStrand.get(dnaStrand.size() - 1)); 			//Acquiring cost of final location to first location,
		locationTwo = locations.indexOf(dnaStrand.get(0));								//since it can't be naturally done within the for loop.
		cost = cost + adjArray.get(locationOne).get(locationTwo);
		return cost;
	}
	
	
	/**
	 * Calculates the cost of every individual route in a population and sorts them from "most fit to least fit".
	 * Utilizes the cost() method to calculate the cost of each route within the list.
	 * @param currGeneration: The routes to calculate each cost of and sort.
	 * @return bestFitList: A sorted list of all the given routes from smallest cost to largest cost.
	 */
	private ArrayList<ArrayList<String>> bestFit(ArrayList<ArrayList<String>> currGeneration) { 
		ArrayList<ArrayList<String>> genClone = new ArrayList<ArrayList<String>>(currGeneration); //Clone of the list of routes.
		ArrayList<ArrayList<String>> bestFitList = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> costList = new ArrayList<Integer>(); 
		
		for(int i = 0; i < currGeneration.size(); i++) {
			costList.add(cost(currGeneration.get(i)));									//Create a list of costs from each route.
		}
		Collections.sort(costList, null);
		int costIndex = 0;
		while(costIndex < costList.size()) { 
			for(int i = 0; i < genClone.size(); i++) {
				if(cost(genClone.get(i)).compareTo(costList.get(costIndex)) == 0) {		//Match the route with the current cost looked at in the cost list,
					bestFitList.add(genClone.get(i));									//starting from the lowest cost. If the route matches that cost,									
					genClone.remove(i);													//add it to the bestFitList and move on to the next cost. To accommodate
					costIndex++;														//all routes that share the same cost, a route is removed from genClone
					break;																//when matched. This process is repeated until all the costs have been
				}																		//matched by all the routes. The product is a list of routes that are 
			}																			//sorted from most fit (smallest cost) to least fit (largest cost).
		}																				
		System.out.println("Best Fit: " + cost(bestFitList.get(0)));
		return bestFitList;	
	}
	
	
	/**
	 * Calculate and returns the average cost of a population of routes.
	 * @param generation: The list of routes.
	 * @return average: The average cost of a population of routes.
	 */
	private Integer averageFit(ArrayList<ArrayList<String>> generation) { //DONE
		int sumCost = 0;
		for(int i = 0; i < generation.size(); i++) {
			sumCost = sumCost + cost(generation.get(i));									//Accumulation of each route's cost.
		}
		int average = sumCost / generation.size();
		System.out.println("Average Fitness: " + average);
		return average;
	}
	
	
	/**
	 * Creates a new set of routes based on a given generation of routes. The amount of children will match the amount of parents.
	 * Selection of which routes will "breed" is hard-coded and explained in detail within the in-line documentation.
	 * Utilizes the crossOver() method to create each child route.
	 * @param parents: The set of routes to make new routes off of.
	 * @return children: The new set of routes based on the "parent" routes. 
	 */
	private ArrayList<ArrayList<String>> offspring(ArrayList<ArrayList<String>> parents) { 
		ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();
		int partnerOneIndex;
		int partnerTwoIndex;

		
		for(int i = 0; i < (parents.size() / 5) - 1; i++) {							//First 20% of children will be a direct "clone" of the
			children.add(parents.get(i));											//top 20% of parents (Top 20% in terms of fitness).
		}
		
		for(int i = children.size(); i < (int) (parents.size() / 1.25); i++) {		//Middle 60% of children will be a crossover of two random
			partnerOneIndex = 0;													//top 50% of parents (fitness).
			partnerTwoIndex = 0;
			while(partnerOneIndex == partnerTwoIndex) {								//These breeding partners are chosen randomly and ensures that
				partnerOneIndex = (int) (Math.random() * ((parents.size() / 2)));	//they aren't the same index. Despite this, it's still possible
				partnerTwoIndex = (int) (Math.random() * ((parents.size() / 2)));	//that both unique indexes share the same route.
			}
			children.add(crossOver(parents.get(partnerOneIndex), parents.get(partnerTwoIndex)));
		}
		
		for(int i = children.size(); i < parents.size(); i++) {						//Last 20% of children will be a crossover of a random
			partnerOneIndex = 0;													//top 50% parent and a random bottom 50% parent (fitness).
			partnerTwoIndex = 0;
			while(partnerOneIndex == partnerTwoIndex) {								//Breeding partners are chosen exactly the same as the middle 60%.
				partnerOneIndex = (int) (Math.random() * ((parents.size() / 2)));
				partnerTwoIndex = (int) (Math.random() * (parents.size() - (parents.size() / 2)) + (parents.size() / 2) );
			}
			children.add(crossOver(parents.get(partnerOneIndex), parents.get(partnerTwoIndex)));
		}
		return children;
	}
	
	
	/**
	 * Creates a new route based on the "genes" of two parent routes. 
	 * @param partnerOne: One route breeding partner.
	 * @param partnerTwo: One route breeding partner.
	 * @return child: The new route created from a mixing of "genes" of two parent routes.
	 */
	private ArrayList<String> crossOver(ArrayList<String> partnerOne, ArrayList<String> partnerTwo) { 
		ArrayList<String> child = new ArrayList<String>(partnerTwo);							//Child is temporarily a clone of parentTwo.
		int randomIndex;
		for(int i = 0; i < (partnerOne.size() / 2); i++) {									    //Swap the genes of partnerTwo based on a given 
			randomIndex = (int) (Math.random() * (partnerOne.size()));						    //gene location of partnerOne. This simulates the 
			Collections.swap(child, child.indexOf(partnerOne.get(randomIndex)), randomIndex);   //adding of half the genes of partnerOne to 
		}																						//partnerTwo to create a new child (route).
		return child;
	}
	
	
	/**
	 * Produces a list of possibly "mutated" routes, if a mutation is triggered. The mutation has a 10% chance of triggering.
	 * The mutation is simulated by swapping two locations within a route.
	 * @param children: The routes to be possibly mutated.
	 */
	private void mutations(ArrayList<ArrayList<String>> children) { //DONE
		int mutationCounter = 0;
		int indexOne;
		int indexTwo;
		for(int i = 0; i < children.size(); i++) {											//Iterate through each child (route).
			indexOne = 0;
			indexTwo = 0;
			while(indexOne == indexTwo) {								   		   			//Ensures different indexes in the event of a mutation.
				indexOne = (int) ((Math.random() * ((children.get(i).size() - 1))));
				indexTwo = (int) ((Math.random() * ((children.get(i).size() - 1))));
			}
			int chance =  (int) (Math.random() * 10);										//Random number from 0-9 is generated.
			if(chance == 0) { 											           			//There is a 10% chance of a mutation.
				Collections.swap(children.get(i), indexOne, indexTwo);						//If mutation is triggered, swap random
				mutationCounter++;															//elements from specific route.
			}
		}
		System.out.println("Mutations: " + mutationCounter);
	}
	
	
	/**
	 * Calculates the most optimal route possible, given uncontrollable factors of a randomized initial set of routes, a
	 * a randomized breeding of two routes, and a randomized mutation of routes. The previously coded methods are combined
	 * to simulate a Genetic Algorithm, where a pattern of sorting routes by fitness, producing offsprings based on fit
	 * rankings, and possible mutations is repeated for an amount of generations. This algorithm is explained in the
	 * in-line documentation.
	 * @param initialPopSize: The population of the initial routes and the following generations of routes to follow.
	 * @param epochs: The number of generations to test.
	 */
	public void shortestFit(int initialPopSize, int epochs) {
		if(epochs == 1) {
			System.out.println("Genetic Algorithm Test with Population Size " + initialPopSize + " and " + epochs + " Generation");
		}
		else {
		System.out.println("Genetic Algorithm Test with Population Size " + initialPopSize + " and " + epochs + " Generations");
		}
		System.out.println("--------------------------------------------------------------------\n");
		ArrayList<ArrayList<String>> parents;
		ArrayList<ArrayList<String>> children;
		
		System.out.println("Initial Population");						
		System.out.println("------------------");						
		parents = initialPopulation(initialPopSize);					//Creation of the initial population of routes.
		parents = bestFit(parents);										//Sort routes by lowest to highest cost.
		averageFit(parents);											//Calculate average cost of initial population.
		System.out.println("Crossing Over...");
		children = offspring(parents);									//Create offspring routes based on current parent routes.
		mutations(children);											//Children routes are mutated by chance.
		System.out.println("\n");
		
		for(int i = 0; i < epochs; i++) {							 	//Creation of n generations of routes.
			System.out.println("Generation " + (i + 1));
			System.out.println("--------------");						
			parents = children;											//The children routes "grow up" to become the parents.				
			parents = bestFit(parents);									//Process identical to that of the initial population;
			averageFit(parents);										//It is merely repeated depending on the amount of generations.
			System.out.println("Crossing Over...");
			children = offspring(parents);
			mutations(children);
			System.out.println("\n");
		}
		System.out.println("Results (Offspring of Generation " + epochs + ")");		//Final result is the best fit child of the last generation
		System.out.println("--------------------------------------");				//specified.
		children = bestFit(children);
		System.out.println("Best Route: " + children.get(0));
	}
		
}
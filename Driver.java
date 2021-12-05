import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Driver {

	public static <T> void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
			
		GeneticAlgorithm route = new GeneticAlgorithm(locationsToList(args[0]), costsToAdjacentArray(args[0]));
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter an initial population size (Recommendation of 1000): ");
		int popSize = scan.nextInt();
		System.out.print("Enter the number of generations to test (Recommendation of 50): ");
		int epochs = scan.nextInt();
		System.out.println("\n");
		route.shortestFit(popSize, epochs);
		System.out.println("\nComplete.");
		scan.close();
	}	
	
	
	/**
	 * Transfers the locations from the provided matrix to an Arraylist.
	 * In terms of the matrix itself, all the elements in column 0 will be added to the ArrayList. 
	 * @param textFileLocation: The location of the matrix file within the computer.
	 * @return locationList: The list containing all the locations from the provided matrix.
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	private static ArrayList<String> locationsToList(String textFileLocation) throws FileNotFoundException, IllegalArgumentException {
		ArrayList<String> locationList = new ArrayList<String>();
		File matrixFile = new File(textFileLocation);
		Scanner scan = new Scanner(matrixFile);
		scan.useDelimiter(",");							//Source studied: https://stackoverflow.com/questions/28766377/how-do-i-use-a-delimiter-with-scanner-usedelimiter-in-java
		while(scan.hasNext()) {							
			String token = scan.next().trim();
			if(!token.matches(".*\\d.*"))				//Source studied: https://stackoverflow.com/questions/18590901/check-if-a-string-contains-numbers-java
			{
				locationList.add(token);				//Only Strings are added to the locationList.
			}	
		}
		scan.close();
		return locationList;
	}
	
	
	/**
	 * Transfer the costs from the provided matrix to an Arraylist of an Arraylist. This simulates an adjacent array.
	 * In terms of the matrix itself, each row (excluding elements in column 0) will be added to an ArrayList that will be added to an ArrayList.
	 * @param textFileLocation: The location of the matrix file within the computer.
	 * @return adjArray: The ArrayList of ArrayList containing all the costs.
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	private static ArrayList<ArrayList<Integer>> costsToAdjacentArray(String textFileLocation) throws FileNotFoundException, IllegalArgumentException {
		ArrayList<Integer> costList = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> adjArray = new ArrayList<ArrayList<Integer>>();
		File matrixFile = new File(textFileLocation);
		Scanner scan = new Scanner(matrixFile);
		scan.useDelimiter(",");								////Source studied: https://stackoverflow.com/questions/28766377/how-do-i-use-a-delimiter-with-scanner-usedelimiter-in-java
		while(scan.hasNext()) {								
			String token = scan.next().trim();
			if(token.matches(".*\\d.*"))					//Source studied: https://stackoverflow.com/questions/18590901/check-if-a-string-contains-numbers-java
			{
				costList.add(Integer.parseInt(token));		//Only integers are added to the costList.
			}
		}
		int rowSize = (int) Math.sqrt(costList.size());  	//Determine how large each row in the adjacent array is.
		while(costList.size() != 0) {
			ArrayList<Integer> currRow = new ArrayList<Integer>();
			for(int i = 0; i < rowSize; i++) {
				currRow.add(costList.get(0));				//Add costs up to the maximum size (rowSize).
				costList.remove(0);							
			}
			adjArray.add(currRow);							//Add that row to the adjacent array.
		}
		scan.close();
		return adjArray;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Kingdom {
	Colony kingdomColony = new Colony();

    // TODO: You should add appropriate instance variables.

	//which is responsible for reading a text file and initializing the instance variables of the kingdomColony object. 
    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
		// TODO: Your code here
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int cityIndex = 0;
			// The cityIndex is added to the cities list of the kingdomColony object, representing the list of all cities in the kingdom.
			while ((line = br.readLine()) != null) {
				String[] values = line.split(" ");
				List<Integer> Neighbors = new ArrayList<>();	
				int i = 0;
while (i < values.length) {
    if (values[i].equals("1")) {
		/*
		 * Inside the inner while loop, each element of values is checked. If the element equals "1", 
		 * it means there is a connection to another city. In this case, the index (i) is added to the Neighbors list.
		 */
        Neighbors.add(i);
    }
    i++;
}
/*
 * The Neighbors list is added to the roadNetwork HashMap of the kingdomColony object, 
 * where the key is the cityIndex (the current city's index), and the value is the list of connected cities.
 */
				kingdomColony.roadNetwork.put(cityIndex, Neighbors);
				kingdomColony.cities.add(cityIndex);
				cityIndex++;
			}
			
			br.close();
		} catch (Exception e) {
			//If any exception occurs during the file reading and processing, it will be caught in the catch block, and the exception stack trace will be printed.
			e.printStackTrace();
		}
    }

	public List<Colony> getColonies() {
		List<Colony> colonies = new ArrayList<>();
		// TODO: DON'T READ THE .TXT FILE HERE!
		// Identify the colonies using the given input file.
		// TODO: Your code here

		// copy of a HashMap<Integer, List<Integer>> named roadNetwork into a new HashMap<Integer, List<Integer>> named undirectedRoadNetwork.
		//  This deep copy creates a new instance of the undirectedRoadNetwork while maintaining the same values as the original roadNetwork


		HashMap<Integer, List<Integer>> undirectedRoadNetwork = new HashMap<>();
Iterator<Map.Entry<Integer, List<Integer>>> entryIterator = kingdomColony.roadNetwork.entrySet().iterator();
while (entryIterator.hasNext()) {
    var entry = entryIterator.next();
    undirectedRoadNetwork.put(entry.getKey(), new ArrayList<>(entry.getValue()));
}


/*
 *  this code snippet iterates over the cities and connected cities in the kingdomColony object and
 *  populates the undirectedRoadNetwork map with an undirected representation of the road network.
 *  It ensures that road connections are recorded in both directions, allowing bidirectional traversal in the undirected road network.
 */
Iterator<Integer> cityIterator = kingdomColony.cities.iterator();
while (cityIterator.hasNext()) {
    Integer city = cityIterator.next();
    List<Integer> Neighbors = kingdomColony.roadNetwork.get(city);
    Iterator<Integer> connectedCityIterator = Neighbors.iterator();
    while (connectedCityIterator.hasNext()) {
        while (connectedCityIterator.hasNext()) {
			Integer connectedCity = connectedCityIterator.next();
			undirectedRoadNetwork.computeIfAbsent(connectedCity, k -> new ArrayList<>()).add(city);
		}
		
    }
}


	
		HashSet<Integer> visitedCities = new HashSet<>();
	
		int i = 0;
while (i < kingdomColony.cities.size()) {
    Integer city = kingdomColony.cities.get(i);
    if (!visitedCities.contains(city)) {
        Colony colony = new Colony();
        dfs(city, undirectedRoadNetwork, visitedCities, colony);
        colonies.add(colony);
    }
    i++;
}
/*
 * This code snippet iterates over the cities in the kingdomColony object and performs a depth-first search (DFS) to 
 * find connected components within the undirected road network. Here's a brief summary of what happens in this code:
 */

int j = 0;

int Colonysize = 0;
for (Colony colony : colonies) {
    Colonysize++;
}



while (j < Colonysize) {
    Colony colony = colonies.get(j);

	int size = 0;
	for (int iterator : colony.cities) {
    size++;
}

    int k = 0;
    while (k < size) {
        int city = colony.cities.get(k);
        colony.roadNetwork.put(city, kingdomColony.roadNetwork.get(city));
        k++;
    }
    j++;
}


		return colonies;
	}
	
	private void dfs(Integer city, HashMap<Integer, List<Integer>> undirectedRoadNetwork, HashSet<Integer> visitedCities, Colony colony) {

		/*
		 * In summary, the dfs() method performs a depth-first search to explore connected cities within the undirected road network,
		 *  marking visited cities, adding them to the current connected component (colony), and recursively visiting their adjacent cities.
		 */
		visitedCities.add(city);
		colony.cities.add(city);

		/*
		 * colony.cities.add(city) adds the current city to the cities list of the colony object, indicating that it belongs to the current connected component.
		 */
	
		List<Integer> Neighbors = undirectedRoadNetwork.get(city);
if (Neighbors == null) {
	/*
	 * If connectedCities is null, it means there are no connected cities for the current city, so the method returns.
	 */
    return;
}
Iterator<Integer> iterator = Neighbors.iterator();
while (iterator.hasNext()) {
    Integer connectedCity = iterator.next();
    boolean contains = false;
    
    for (Integer city2 : visitedCities) {
        if (city2.equals(connectedCity)) {
            contains = true;
            break;
        }
    }
    
    if (contains) {
        continue;
    }
    
    dfs(connectedCity, undirectedRoadNetwork, visitedCities, colony);
}


	}

	public void printColonies(List<Colony> discoveredColonies) {
		// Print the given list of discovered colonies conforming to the given output format.
		System.out.println("Discovered colonies are: ");

		
		for (int i = 0; i < discoveredColonies.size(); i++) {
			Colony colony = discoveredColonies.get(i);
			List<Integer> sortedCities = new ArrayList<>(colony.cities);
			Collections.sort(sortedCities);
			for (int j = 0; j < sortedCities.size(); j++) {
				sortedCities.set(j, sortedCities.get(j) + 1);
			}
			System.out.println("Colony " + (i + 1) + ": " + sortedCities);
		}
		
	}
}
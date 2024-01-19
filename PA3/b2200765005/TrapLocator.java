import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }



    public List<List<Integer>> revealTraps() {
        List<List<Integer>> traps = new ArrayList<>();
    
        int colonyIndex = 0;


    while (colonyIndex < colonies.size()) {
    Colony Iteratorcolony = colonies.get(colonyIndex);
    List<Integer> citiesInTrap = new ArrayList<>();

    int MaxNo = 0;
Iterator<Integer> iterator = Iteratorcolony.cities.iterator();


// findin max city 
//. This ensures that the subsequent code can correctly handle city numbers up to the maximum value encountered.
while (iterator.hasNext()) {
    int city = iterator.next();
    if (city > MaxNo) {
        MaxNo = city;
    }
}


    int cityNO = 0;
    while (cityNO < Iteratorcolony.cities.size()) {
        boolean[] passed = new boolean[MaxNo + 1];

        /*
         * passed is a boolean array that keeps track of whether a city has been visited before. It helps prevent revisiting the same city in a recursive call.
         */




        int city = Iteratorcolony.cities.get(cityNO);
        if (Iteratorcolony.roadNetwork.get(city) == null) {
            cityNO++;
            continue;
        }

      
        boolean[] stack = new boolean[MaxNo + 1];


        /*
         * 
         * citiesInTrap is a list that keeps track of the cities in the current trap (cycle).
         */
        if (TrapDetection( passed,  Iteratorcolony.roadNetwork, city, citiesInTrap,stack)) {
            break;
        }

        cityNO++;
    }
    colonyIndex++;
    traps.add(citiesInTrap);
    Collections.sort(citiesInTrap);
    
    
}

    
        return traps;
    }
  private boolean TrapDetection( boolean[] passed,  HashMap<Integer, List<Integer>> roadNetwork,int city, List<Integer> citiesInTrap, boolean [] stack) {


    /*
     * The purpose of the TrapDetection function is to perform a depth-first search (DFS) traversal of the road network
     * , starting from a given city, and detect cycles in the connected component containing that city.
     * 
     */
    Collections.sort(citiesInTrap);

    
    if (stack[city]) {
        citiesInTrap.add(city);
        return true;
    }
    if (passed[city]) {
        return false;
    }

    /*
     * . If the current city is already in the recursion stack, it means a cycle has been detected, and the function returns true. 
     * If the current city has been visited before, it means that the current path does not contain a cycle, 
     * and the function returns false. These conditions help the algorithm identify and handle cycles appropriately during the traversal process.
     */

    passed[city] = true;
    stack[city] = true;
    List<Integer> neighbors = roadNetwork.get(city);

    boolean isAnyCycle = false;

    if (neighbors != null) {
        int nei_no = 0;
        while (nei_no < neighbors.size()) {
            int Neighbor_city = neighbors.get(nei_no);
            if (TrapDetection( passed, roadNetwork,Neighbor_city,  citiesInTrap ,stack)) {
                isAnyCycle = true;
                boolean found = false;
                for (int city2 : citiesInTrap) {
                    if (city2 == Neighbor_city) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    citiesInTrap.add(Neighbor_city);
                }
            }
            nei_no++;
        }
    }
    

    stack[city] = false;
    return isAnyCycle;
}

    public void printTraps(List<List<Integer>> traps) {

        
        System.out.println("Danger exploration conclusions:");
    
        int k = 0;


    
    while (k < traps.size()) {
        System.out.print("Colony " + (k + 1) + ": ");
    
        List<Integer> citiesInTrap = traps.get(k);
        if (citiesInTrap.isEmpty()) {
            System.out.println("Safe");
        } else {
            System.out.print("Dangerous. Cities on the dangerous path: [");
    
            for (int m = 0; m < citiesInTrap.size(); m++) {
                if (m != 0) {
                    System.out.print(", ");
                }
                System.out.print(citiesInTrap.get(m) + 1);
            }
    
            System.out.println("]");
        }
    
        k++;
    }
    
}

    }    


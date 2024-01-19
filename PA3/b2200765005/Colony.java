import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Colony {
    // List of cities in the current colony
    
    public List<Integer> cities = new ArrayList<>();

    // roadNetwork is a map of lists which represents the network of the colony in the adjacency list format
    // E.g. roadNetwork[x] is a list of cities to which the city x has a road to.
    public HashMap<Integer, List<Integer>> roadNetwork = new HashMap<>();

    public Colony(List<Integer> cities, HashMap<Integer, List<Integer>> roadNetwork) {
        this.cities = cities;
        this.roadNetwork = roadNetwork;
    }

    public Colony(){
        
    }

    public void addCity(Integer city) {
        cities.add(city);
    }
   

    public Colony(List<Integer> cities) {
        this.cities = cities;
    }

    public List<Integer> getCities() {
        return cities;
    }

    public HashMap<Integer, List<Integer>> getRoadNetwork() {
        return roadNetwork;
    }

    public Colony[] values() {
        return null;
    }
}

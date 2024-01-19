import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // List of visits, read in the given order
    

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename) {
        try {
            File inputFile = new File(filename);
    
            // Create a DocumentBuilder object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
    
            // Parse the input file and create a Document object
            Document doc = builder.parse(inputFile);
    
            // Get the root element of the document
            Element root = doc.getDocumentElement();
    
            // Get the Locations element
            Element locationsElement = (Element) root.getElementsByTagName("Locations").item(0);
    
            // Get the Location elements and add them to the locationMap and locations list
            NodeList locationNodes = locationsElement.getElementsByTagName("Location");
            for (int i = 0; i < locationNodes.getLength(); i++) {
                Element locationElement = (Element) locationNodes.item(i);
                int id = Integer.parseInt(locationElement.getElementsByTagName("Id").item(0).getTextContent());
                String name = locationElement.getElementsByTagName("Name").item(0).getTextContent();
                Location location = new Location(name, id);
                locationMap.put(id, location);
                locations.add(location);
            }
    
            // Get the Trails element
            Element trailsElement = (Element) root.getElementsByTagName("Trails").item(0);
    
            // Get the Trail elements and add them to the trails list
            NodeList trailNodes = trailsElement.getElementsByTagName("Trail");
            for (int i = 0; i < trailNodes.getLength(); i++) {
                Element trailElement = (Element) trailNodes.item(i);
                int sourceId = Integer.parseInt(trailElement.getElementsByTagName("Source").item(0).getTextContent());
                int destId = Integer.parseInt(trailElement.getElementsByTagName("Destination").item(0).getTextContent());
                int danger = Integer.parseInt(trailElement.getElementsByTagName("Danger").item(0).getTextContent());
                Trail trail = new Trail(locationMap.get(sourceId), locationMap.get(destId), danger);
                trails.add(trail);
            }
    
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        
        // Step 1: Create a set of disjoint sets, one for each location.
        Map<Location, Integer> locationToSetId = new HashMap<>();
        for (Location loc : locations) {
            locationToSetId.put(loc, loc.getID());
        }

        // Step 2: Create a list of all the trails and sort them by ascending order of danger.
        List<Trail> allTrails = new ArrayList<>(trails);
        Collections.sort(allTrails, Comparator.comparing(Trail::getDanger));

        // Step 3: Iterate through the sorted trails, adding each trail to the MST if it doesn't create a cycle.
        for (Trail trail : allTrails) {
            Location source = trail.getSource();
            Location dest = trail.getDestination();
            int setId1 = locationToSetId.get(source);
            int setId2 = locationToSetId.get(dest);
            if (setId1 != setId2) {
                safestTrails.add(trail);
                for (Location loc : locationToSetId.keySet()) {
                    if (locationToSetId.get(loc) == setId2) {
                        locationToSetId.put(loc, setId1);
                    }
                }
            }
        }
        
        return safestTrails;

       
    
    }


  


     public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        System.out.println("Safest Trails:");
        int totalDanger = 0;
        for (Trail trail : safestTrails) {
            totalDanger += trail.getDanger();
            System.out.println(trail.getSource().getName()+ " to "+  trail.getDestination().getName()+ " with "+ trail.getDanger());
        }
        System.out.println("Total length: " + totalDanger);
    }

}
           



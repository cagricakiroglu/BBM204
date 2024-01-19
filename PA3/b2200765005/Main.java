import java.io.IOException;
import java.util.List;

//TODO:  ██████╗ ███████╗    ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗██╗██████╗ ██╗     ███████╗██╗
//TODO:  ██╔══██╗██╔════╝    ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██║██╔══██╗██║     ██╔════╝██║
//TODO:  ██████╔╝█████╗      ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗██║██████╔╝██║     █████╗  ██║
//TODO:  ██╔══██╗██╔══╝      ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██║██╔══██╗██║     ██╔══╝  ╚═╝
//TODO:  ██████╔╝███████╗    ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║██║██████╔╝███████╗███████╗██╗
//TODO:  ╚═════╝ ╚══════╝    ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚═╝╚═════╝ ╚══════╝╚══════╝╚═╝

//TODO:  ██████╗  ██████╗     ███╗   ██╗ ██████╗ ████████╗     ██████╗██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███████╗
//TODO:  ██╔══██╗██╔═══██╗    ████╗  ██║██╔═══██╗╚══██╔══╝    ██╔════╝██║  ██║██╔══██╗████╗  ██║██╔════╝ ██╔════╝
//TODO:  ██║  ██║██║   ██║    ██╔██╗ ██║██║   ██║   ██║       ██║     ███████║███████║██╔██╗ ██║██║  ███╗█████╗
//TODO:  ██║  ██║██║   ██║    ██║╚██╗██║██║   ██║   ██║       ██║     ██╔══██║██╔══██║██║╚██╗██║██║   ██║██╔══╝
//TODO:  ██████╔╝╚██████╔╝    ██║ ╚████║╚██████╔╝   ██║       ╚██████╗██║  ██║██║  ██║██║ ╚████║╚██████╔╝███████╗
//TODO:  ╚═════╝  ╚═════╝     ╚═╝  ╚═══╝ ╚═════╝    ╚═╝        ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚══════╝

//TODO:     ████████╗██╗  ██╗███████╗     ██████╗ ██╗██╗   ██╗███████╗███╗   ██╗     ██████╗ ██████╗ ██████╗ ███████╗██╗
//TODO:     ╚══██╔══╝██║  ██║██╔════╝    ██╔════╝ ██║██║   ██║██╔════╝████╗  ██║    ██╔════╝██╔═══██╗██╔══██╗██╔════╝██║
//TODO:        ██║   ███████║█████╗      ██║  ███╗██║██║   ██║█████╗  ██╔██╗ ██║    ██║     ██║   ██║██║  ██║█████╗  ██║
//TODO:        ██║   ██╔══██║██╔══╝      ██║   ██║██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║    ██║     ██║   ██║██║  ██║██╔══╝  ╚═╝
//TODO:        ██║   ██║  ██║███████╗    ╚██████╔╝██║ ╚████╔╝ ███████╗██║ ╚████║    ╚██████╗╚██████╔╝██████╔╝███████╗██╗
//TODO:        ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝     ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝
public class Main {
    public static void main(String[] args) {
        try {
            // Chapter 1
            missionHeader("The Path of the Warrior");
    
            TravelMap map = new TravelMap();
            map.initializeMap(args[0]);
            //map.initializeMap("sample_io\\input\\path_of_the_warrior_input_1.xml");
            List<Trail> safestTrails = map.getSafestTrails();
            map.printSafestTrails(safestTrails);
    
            System.out.println();
    
            // Chapter 2
            missionHeader("Finding Hope in the Darkest of Times");
    
            Kingdom kingdom = new Kingdom();
            kingdom.initializeKingdom(args[1]);
    
            //kingdom.initializeKingdom("sample_io\\input\\kingdom_input_1.txt");
            List<Colony> discoveredColonies = kingdom.getColonies();
            kingdom.printColonies(discoveredColonies);
    
            System.out.println();
    
            // Chapter 3
            missionHeader("Escaping the Time Trap Peril");
    
            TrapLocator locator = new TrapLocator(discoveredColonies);
            List<List<Integer>> traps = locator.revealTraps();
            locator.printTraps(traps);
    
            System.out.println();
        } catch (Exception e) {
            System.out.println("An error occurred while reading the input file: " + e.getMessage());
        }
    }
    
    public static void missionHeader(String title) {
        System.out.println(title);
        for (int i = 0; i < title.length(); i++) System.out.print("#");
        System.out.println();
    }
}

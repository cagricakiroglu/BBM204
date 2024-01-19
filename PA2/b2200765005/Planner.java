import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];
        Arrays.fill(maxWeight, -1.0);
        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        // YOUR CODE HERE
        int low = 0;
        int high = index - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midTime = taskArray[mid].getFinishTime();
            String indexstart=taskArray[index].getStartTime();

            if (midTime.compareTo(indexstart)<=0) {
                result = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for (int i = 0; i < taskArray.length; i++) {
            this.compatibility[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();
        System.out.println("Calculating max array\n"+"---------------------");

        calculateMaxWeight(taskArray.length-1);

        System.out.println("\nCalculating the dynamic solution\n"+"--------------------------------");

        solveDynamic(taskArray.length-1);

        System.out.println("\nDynamic Schedule\n"+"----------------");

        for(int i = planDynamic.size()-1;i>=0;i--){
            System.out.println("At "+planDynamic.get(i).getStartTime()+", "+planDynamic.get(i).getName()+".");
        }

        Collections.reverse(planDynamic);
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if (i < 0) {
            return;
        }
        
        System.out.println("Called solveDynamic(" + i + ")");
        
        calculateCompatibility();
        
        double maxWithI = taskArray[i].getWeight();
        int j = compatibility[i];
        
        if (j != -1) {
            maxWithI += maxWeight[j];
        }
        
        double maxWithoutI = (i > 0) ? maxWeight[i - 1] : 0;
        
        if (maxWithI > maxWithoutI) {
            planDynamic.add(taskArray[i]);
            
            if (j != -1) {
                solveDynamic(j);
            }
        } else {
            if (i > 0) {
                solveDynamic(i - 1);
            } else {
                planDynamic.add(taskArray[0]);
            }
        }
    }



    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {


        System.out.println("Called calculateMaxWeight("+i+")");



        if (i == 0) {
            double ifresult = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
            maxWeight[i] = Math.max(ifresult, calculateMaxWeight(i - 1));
            return maxWeight[i];
        }


        if (i == -1){
         return 0.0;
        }


        if (maxWeight[i] != -1.0) {
            return maxWeight[i];
       
        }
        double result2 = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
        maxWeight[i] = Math.max(result2 , calculateMaxWeight(i-1));
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {


        ArrayList<Task> sortedTasks = new ArrayList<>(Arrays.asList(taskArray));
    
        Collections.sort(sortedTasks);

    System.out.println("Greedy Schedule");

        System.out.println("---------------");

        // Greedy approach
        

        for (int j = 0; j < taskArray.length; j++) {
            if (j == 0) {
            planGreedy.add(taskArray[j]);
            } else {
                if (taskArray[j].getStartTime().compareTo(planGreedy.get(planGreedy.size() - 1).getFinishTime()) >= 0) {
                    planGreedy.add(taskArray[j]);
                    }
            }
            }

        //OUTPUT the plan
        for (Task task : planGreedy) {
            String starttime= task.getStartTime();
            String name= task.getName();
            System.out.println("At " +starttime + ", " + name + ".");
        }
        return planGreedy;
    }
    
}

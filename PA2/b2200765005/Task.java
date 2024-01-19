



public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // YOUR CODE HERE
        String[] parts = getStartTime().split(":");

        int startHour = Integer.parseInt(parts[0]);
        int endHour = (startHour + duration) % 24;
        
        String endHourString = String.format("%02d", endHour);
        return endHourString + ":" + parts[1];

    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {

        int factor = this.isUrgent() ? 2000: 1 ;

        double weight=  ( this.getImportance() * factor)/ this.getDuration();

        // YOUR CODE HERE
        return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
       
        // YOUR CODE HERE
        Task ta_sk=(Task) o ;
        return this.getFinishTime().compareTo(ta_sk.getFinishTime());
        
    }
}

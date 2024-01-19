import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class IMECEPathFinder {
    private int[][] grid;
    private int[][] scaledGrid;
    private int height;
    private int width;
    private int maxFlyingHeight;
    private double fuelCostPerUnit;
    private double climbingCostPerUnit;

    public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit) {
        grid = new int[rows][cols];
        scaledGrid = new int[rows][cols];
        this.height = rows;
        this.width = cols;
        this.maxFlyingHeight = maxFlyingHeight;
        this.fuelCostPerUnit = fuelCostPerUnit;
        this.climbingCostPerUnit = climbingCostPerUnit;

        try {
            // Read the input file and populate the grid
            Scanner scanner = new Scanner(new File(filename));
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }
            scanner.close();

            // Scale the grid values to the range of 0-255 for visualization
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int value = grid[i][j];
                    if (value < min) {
                        min = value;
                    }
                    if (value > max) {
                        max = value;
                    }
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int scaledValue = (int) ((grid[i][j] - min) * 255.0 / (max - min));
                    scaledGrid[i][j] = scaledValue;
                }
            }

            // Write the scaled grid to a grayscale map file
            PrintWriter writer = new PrintWriter("grayscaleMap.dat");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.print(scaledGrid[i][j]);
                    if (j != cols - 1) {
                        writer.print(" ");
                    }
                }
                if (i != rows - 1) {
                    writer.println();
                }
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drawGrayscaleMap(Graphics g) {
        // Iterate over the rows and columns of the scaledGrid
        for (int i = 0; i < scaledGrid.length; i++) {
            for (int j = 0; j < scaledGrid[0].length; j++) {
                // Get the grayscale value from the scaledGrid
                int value = scaledGrid[i][j];

                // Create a color with the grayscale value
                Color grayscaleColor = new Color(value, value, value);

                // Set the color of the graphics object
                g.setColor(grayscaleColor);

                // Draw a filled rectangle at the current position
                g.fillRect(j, i, 1, 1);
            }
        }
        // Draw a grayscale grid of the height values
    }

    private double calculateCost(Point start, Point end) {
        int endX = end.x;
        int endY = end.y;
        int endHeight = grid[endY][endX];
        int heightImpact = Math.max(0, endHeight - grid[start.y][start.x]);
        double distance = Math.sqrt(Math.pow(endX - start.x, 2) + Math.pow(endY - start.y, 2));
        double totalCost = (distance * fuelCostPerUnit) + (heightImpact * climbingCostPerUnit);
        return totalCost;
    }

    public List<Point> getMostEfficientPath(Point start, Point end) {
        List<Point> path = new ArrayList<>();
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(p -> p.cost));
    
        start.cost = 0;
        queue.add(start);
    
        double[][] minCost = new double[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(minCost[i], Double.POSITIVE_INFINITY);
        }
        minCost[start.y][start.x] = 0;
    
        Point[][] parent = new Point[height][width];
    
        while (!queue.isEmpty()) {
            Point current = queue.poll();
    
            if (current.equals(end)) {
                break;
            }
    
            exploreNeighbors(current, end, minCost, parent, queue);
        }
    
        if (minCost[end.y][end.x] == Double.POSITIVE_INFINITY) {
            return path;
        }
    
        buildPath(end, parent, path);
        return path;
    }
    
    private void exploreNeighbors(Point current, Point end, double[][] minCost, Point[][] parent, PriorityQueue<Point> queue) {
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
    
        for (int i = 0; i < dx.length; i++) {
            int newX = current.x + dx[i];
            int newY = current.y + dy[i];
    
            if (isValidPoint(newX, newY)) {
                Point neighbor = new Point(newX, newY);
                double cost = calculateCost(current, neighbor);
    
                if (current.cost + cost < minCost[newY][newX]) {
                    minCost[newY][newX] = current.cost + cost;
                    parent[newY][newX] = current;
                    neighbor.cost = minCost[newY][newX];
                    queue.add(neighbor);
                }
            }
        }
    }
    
    private boolean isValidPoint(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    private void buildPath(Point end, Point[][] parent, List<Point> path) {
        Point current = end;
        while (current != null) {
            path.add(current);
            current = parent[current.y][current.x];
        }
    
        Collections.reverse(path);
    }
    
    public double getMostEfficientPathCost(List<Point> path) {
        double totalCost = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            Point start = path.get(i);
            Point end = path.get(i + 1);
            totalCost += calculateCost(start, end);
        }

        return totalCost;
    }

    public void drawMostEfficientPath(Graphics g, List<Point> path) {
        g.setColor(Color.GREEN);

        for (int i = 0; i < path.size() - 1; i++) {
            Point current = path.get(i);
            Point next = path.get(i + 1);
            g.drawLine(current.x, current.y, next.x, next.y);
        }
    }

    private List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        if (x > 0) {
            neighbors.add(new Point(x - 1, y));
        }
        if (x < width - 1) {
            neighbors.add(new Point(x + 1, y));
        }
        if (y > 0) {
            neighbors.add(new Point(x, y - 1));
        }
        if (y < height - 1) {
            neighbors.add(new Point(x, y + 1));
        }

        return neighbors;
    }

    public List<Point> getLowestElevationEscapePath(Point start) {
        List<Point> pathPointsList = new ArrayList<>();
        pathPointsList.add(start);

        int numRows = grid.length;
        int numCols = grid[0].length;
        int currentCol = start.x;
        int currentRow = start.y;

        while (currentCol < numCols - 1) {
            int[] neighborElevations = getNeighborElevations(currentRow, currentCol + 1);

            int currentElevation = grid[currentRow][currentCol];
            int minElevationDiff = Integer.MAX_VALUE;
            Point nextPoint = null;

            for (int i = -1; i <= 1; i++) {
                int elevationDiff = Math.abs(neighborElevations[i + 1] - currentElevation);

                if (elevationDiff < minElevationDiff || (elevationDiff == minElevationDiff && i == 0)) {
                    minElevationDiff = elevationDiff;
                    nextPoint = new Point(currentCol + 1, currentRow + i);
                }
            }

            if (nextPoint != null) {
                pathPointsList.add(nextPoint);
                currentCol = nextPoint.x;
                currentRow = nextPoint.y;
            }
        }

        return pathPointsList;
    }

    private int[] getNeighborElevations(int row, int col) {
        int[] elevations = new int[3];
        int numRows = grid.length;

        for (int i = -1; i <= 1; i++) {
            int neighborRow = row + i;
            elevations[i + 1] = (neighborRow >= 0 && neighborRow < numRows) ? grid[neighborRow][col] : Integer.MAX_VALUE;
        }

        return elevations;
    }

    public int getLowestElevationEscapePathCost(List<Point> pathPointsList) {
        int totalChange = 0;

        for (int i = 0; i < pathPointsList.size() - 1; i++) {
            Point current = pathPointsList.get(i);
            Point next = pathPointsList.get(i + 1);
            int change = Math.abs(grid[current.y][current.x] - grid[next.y][next.x]);
            totalChange += change;
        }

        return totalChange;
    }

    public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList) {
        g.setColor(Color.RED);

        for (int i = 0; i < pathPointsList.size() - 1; i++) {
            Point current = pathPointsList.get(i);
            Point next = pathPointsList.get(i + 1);
            g.drawLine(current.x, current.y, next.x, next.y);
        }
    }
}

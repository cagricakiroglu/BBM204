import java.util.ArrayList;
import java.util.List;

public class Point {
    public int x;
    public int y;
    public double cost;
    public Point parent;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

    public Point getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    // You can add additional variables and methods if necessary.
}

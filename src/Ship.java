import java.util.ArrayList;

public class Ship {
	private ArrayList<Point> ArrayPoints = new ArrayList<Point>();
	private boolean isAlive;
	private ArrayList<Point> ArrayBorders = new ArrayList<Point>();
	private int type;
	private int health;
	
	private Ship(ArrayList<Point> points){
		setPoints(points);
		setType(points.size());
		health = getType();
	}
	public String toString(){
		for(Point p: ArrayPoints){
			System.out.println(p);
		}
		return Integer.toString(ArrayPoints.size());
	}
	public ArrayList<Point> getPoints() {
		return ArrayPoints;
	}
	public void setPoints(ArrayList<Point> arrayPoints) {
		ArrayPoints = arrayPoints;
		for(Point p:arrayPoints)
			p.setType(1);
	}
	
	public boolean isAlive() {
		health--;
		if (health == 0)
			return false;
		return true;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ArrayList<Point> getBorders() {
		return ArrayBorders;
	}
	public void setBorders(ArrayList<Point> arrayBorders) {
		ArrayBorders = arrayBorders;
	}
	public void createBorder(int x_min, int x_max,int y_min, int y_max){
		for (int j = x_min-1; j <= x_max+1; j++){
			for (int i = y_min-1; i <= y_max+1; i++){
				if (((j < x_min || j>x_max) || (i < y_min || i > y_max )) && ((i >= 0 && j >=0) && (i < 10 && j < 10 ))){
					Point p = new Point(j, i, 2);
					ArrayBorders.add(p);
				}
			}
		}
	}
	public static Ship Create(ArrayList<Point> points){
		Ship ship;
		int x_min = points.get(0).getX();
		int y_min = points.get(0).getY();
		int x_max = points.get(0).getX();
		int y_max = points.get(0).getY();
		for(Point p:points){
			if(p.getX() < x_min)
				x_min = p.getX();
			if(p.getX() > x_max)
				x_max = p.getX();
			if(p.getY() < y_min)
				y_min = p.getY();
			if(p.getY() > y_max)
				y_max = p.getY();
		}
		if((x_max - x_min) == 0 || (y_max - y_min) == 0){
			int area = (x_max - x_min) + (y_max - y_min) + 1;
			if (area == points.size()){
				ship = new Ship(points);
				ship.createBorder(x_min, x_max, y_min, y_max);
				return ship;
			}
		}
		return null;
	}
	
	
}

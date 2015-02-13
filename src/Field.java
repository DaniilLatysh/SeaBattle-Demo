import java.util.ArrayList;
import java.util.Random;

public class Field {
	private Point[][] points = new Point[10][10];
	ArrayList<Ship> ArrayShips = new ArrayList<Ship>();
	private int type1 = 0;
	private int type2 = 0;
	private int type3 = 0;
	private int type4 = 0;
	private int shipCount = 10;

	public Field() {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				points[i][j] = new Point(j, i);
			}
		}
	}

	public boolean addShip(Ship ship) {
		if (ship == null)
			return false;
		if (!checkCollision(ship))
			return false;
		if (checkCountAddBorders(ship))
			return true;
		return false;
	}

	public boolean isAllShipDead() {
		if (shipCount == 0)
			return true;
		return false;
	}

	private boolean checkCountAddBorders(Ship ship) {

		if (ship.getType() == 1 && type1 + 1 < 5) {
			ArrayShips.add(ship);
			type1++;
			setPoints(ship.getPoints(), 1);
			setPoints(ship.getBorders(), 2);
			return true;
		}
		if (ship.getType() == 2 && type2 + 1 < 4) {
			ArrayShips.add(ship);
			type2++;
			setPoints(ship.getPoints(), 1);
			setPoints(ship.getBorders(), 2);
			return true;
		}
		if (ship.getType() == 3 && type3 + 1 < 3) {
			ArrayShips.add(ship);
			type3++;
			setPoints(ship.getPoints(), 1);
			setPoints(ship.getBorders(), 2);
			return true;
		}
		if (ship.getType() == 4 && type4 + 1 < 2) {
			ArrayShips.add(ship);
			type4++;
			setPoints(ship.getPoints(), 1);
			setPoints(ship.getBorders(), 2);
			return true;
		}
		return false;
	}

	public Point getPoint(int x, int y) {
		return points[y][x];
	}

	public void generateShips() {
		int type = 4;
		Random rand = new Random();
		Ship ship;
		while (type > 0) {
			do {
				Point p;
				ArrayList<Point> shipPoints = new ArrayList<Point>();
				boolean direction = rand.nextBoolean(); // true - вправо false -
														// вниз
				do {
					p = (direction) ? new Point(rand.nextInt(10 - type),
							rand.nextInt(10)) : new Point(rand.nextInt(10),
							rand.nextInt(10 - type));
				} while (points[p.getY()][p.getX()].getType() > 0);
				if (direction) {
					for (int i = p.getX(); i < p.getX() + type; i++)
						shipPoints.add(new Point(i, p.getY()));
				} else {
					for (int i = p.getY(); i < p.getY() + type; i++)
						shipPoints.add(new Point(p.getX(), i));
				}
				ship = Ship.Create(shipPoints);
			} while (!checkCollision(ship));
			if (!checkCountAddBorders(ship))
				type--;
		}
	}

	private boolean checkCollision(Ship ship) {
		for (Point p : ship.getPoints()) {
			if (points[p.getY()][p.getX()].getType() == 2
					|| points[p.getY()][p.getX()].getType() == 1)
				return false;
		}
		return true;
	}

	public boolean isReady() {
		if (type1 == 4 && type2 == 3 && type3 == 2
				&& type4 == 1)
			return true;
		return false;
	}

	
	private void setPoints(ArrayList<Point> getPoints, int type) {
		for (Point p : getPoints) {
			points[p.getY()][p.getX()].setType(type);
		}
	}

	public ArrayList<Ship> getArrayShips() {
		return ArrayShips;
	}

	public ArrayList<Point> Attack(int x, int y) {
		ArrayList<Point> responsePoints = new ArrayList<Point>();
		if(x < 0 || x > 9 || y < 0 || y > 9){
			responsePoints.add(new Point(0, 0, -1));
			return responsePoints;
		}
		if (points[y][x].getType() == 3 || points[y][x].getType() == 4) { //наложение
			responsePoints.add(new Point(0, 0, -1));
			return responsePoints;
		}
		if (points[y][x].getType() == 0 || points[y][x].getType() == 2) { // барьер
			points[y][x].setType(3);
			responsePoints.add(points[y][x]);
		}
		if (points[y][x].getType() == 1) {
			for (Ship ship : ArrayShips)
				for (Point p : ship.getPoints()) {
					if (p.getX() == x && p.getY() == y) {
						p.setType(4);
						points[p.getY()][p.getX()].setType(4);
						responsePoints.add(p);
  						if (!ship.isAlive()) {
							shipCount--;
							setPoints(ship.getBorders(), 3);
							responsePoints.addAll(ship.getBorders());
						}
					}
				}
		}
		return responsePoints;
	}

	public ArrayList<Point> getShip(int x, int y) {
		for (Ship sh : ArrayShips) {
			for (Point p : sh.getPoints()) {
				if (p.getX() == x && p.getY() == y)
					return sh.getPoints();
			}
		}
		return null;
	}

}

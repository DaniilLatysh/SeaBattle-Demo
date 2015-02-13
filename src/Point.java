
public class Point {
	private int x, y;
	private int type;
	
	public Point(int x, int y) {
		setX(x);
		setY(y);
		setType(0);
	}
	public Point(int x, int y, int type) {
		setX(x);
		setY(y);
		setType(type);
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
	
	public Point plus(Point p){
		return new Point(p.getX()+this.x ,p.getY()+this.y);
	}

	public Point invers(){
		return new Point(-this.x, -this.y); 
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}

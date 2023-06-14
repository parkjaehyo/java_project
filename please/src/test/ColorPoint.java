package test;
class Point {
	private int x, y; // x와 y 좌표를 나타내는 private 멤버 변수

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
}



public class ColorPoint extends Point {
	//색상을 나타내는 private 멤버 변수
	private String color;
	
	// 부모 클래스인 Point의 생성자 호출하여 x,y 초기화
	public ColorPoint(int x, int y, String color) {
		super(x, y); 
		this.color = color; // color 변수 초기화
	}
 
    // 부모 클래스인 Point의 move 메서드 호출하여 좌표 이동
	public void setPoint(int x, int y) {
		super.move(x, y); 
	}

	// color 변수 변경
	public void setColor(String color) {
		this.color = color; 
	}

	// 객체의 색상과 좌표 출력
	public void show() {
		System.out.println(color + "색으로 " + "(" + getX() + "," + getY() + ")"); 
	}

	public static void main(String[] args) {
		ColorPoint cp = new ColorPoint(5, 5, "YELLOW");//ColorPoint 객체 생성
		cp.setPoint(10, 20); // 좌표 변경
		cp.setColor("GREEN"); // 색상 변경
		cp.show(); // 객체의 색상과 좌표 출력
	}
}
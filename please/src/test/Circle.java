package test;

public class Circle {
	//반지름
	private int radius;
	
	//반지름 생성자 
	public Circle(int radius) {
		this.radius=radius;
		
		
	}
	//반지름 반환
	public int getCircle() {
		
		return radius;
	}
	
}

class NamedCircle extends Circle {

	private String name;
 
	//반지름 전달, 이름 여기서 생성
	public NamedCircle(int radius,String name) {
		super(radius);
		this.name=name;
		
		
		
	}
	//출력
	public void show() {
		
		System.out.println(name+","+"반지름"+getCircle());
		
	} 
	
	//객체 생성
	public static void main(String[] args) {
		NamedCircle w= new NamedCircle(5,"waffle");
		w.show();
		
	}
}

	





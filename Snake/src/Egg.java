import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Rectangle;  
import java.util.Random; 

public class Egg {
	
	int row,col;
	int w = Yard.getBLOCK_SIZE();
	int h = Yard.getBLOCK_SIZE();
	
	private static Random r = new Random();	//ʳ���������λ��
	private Color color = Color.GREEN;	//ʳ���ʼ��ɫ
	
	public int getRow(){
		return row;
	}
	public void setRow(int row){
		this.row = row;
	}
	public int getCol(){
		return col;
	}
	public void setCol(int col){
		this.col = col;
	}
	public Egg(int row, int col){
		this.row = row;
		this.col = col;
	}
	public Egg(){
		this(r.nextInt(Yard.getROWS() - 2 ) + 2, r.nextInt(Yard.getCOLS()));
	}
	//�����趨ʳ���λ��
	public void reAppear(){
		this.row = r.nextInt(Yard.getROWS() - 2) + 2;
		this.col = r.nextInt(Yard.getCOLS());
	}
	public Rectangle getRect(){
		return new Rectangle(Yard.getBLOCK_SIZE() * col,
				Yard.getBLOCK_SIZE() * row,w,h);
	}
	//����ʳ��
	void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillRect(Yard.getBLOCK_SIZE() * col, Yard.getBLOCK_SIZE() * row, w, h);
		g.setColor(c);
		if(color == Color.green){
			color = color.RED;
		}
		else{
			color = color.GREEN;
		}
	}
}

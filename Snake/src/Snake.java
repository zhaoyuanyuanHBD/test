import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Rectangle;  
import java.awt.event.KeyEvent;

public class Snake {
	
	private Node head = null;	//头节点
	private int size = 0;		//节点个数
	private Node tail = null;	//尾节点
	
	private Node node = new Node(20,30, Direction.LEFT);	//初始蛇节点
	private Yard y;	//初始蛇位置
	
	public Snake(Yard y){
		head = node;
		tail = node;
		size = 1;
		this.y = y;
		
	}
	
	//节点添加到尾巴
	public void addToTail(){
		Node node = null;
		switch(tail.dir){
		case LEFT:
			node = new Node(tail.row, tail.col + 1, tail.dir);	//向左新建节点
			break;
		case RIGHT:  
            node = new Node(tail.row, tail.col - 1, tail.dir);  
            break;  
        case UP:  
            node = new Node(tail.row + 1, tail.col, tail.dir);  
            break;  
        case DOWN:  
            node = new Node(tail.row - 1, tail.col, tail.dir);  
            break;
		}
		
		//添加尾结点
		tail.next = node;
		node.prev = tail;
		tail = node;
		size++;
	}
	
	
	
	//添加头节点
	private void addToHead() {
		Node node = null;
		switch (head.dir) {  
        case LEFT:  
            node = new Node(head.row, head.col - 1, head.dir);  
            break;  
        case UP:  
            node = new Node(head.row - 1, head.col, head.dir);  
            break;  
        case RIGHT:  
            node = new Node(head.row, head.col + 1, head.dir);  
            break;
        case DOWN:  
            node = new Node(head.row + 1, head.col, head.dir);  
            break;  
        }
		node.next = head;
		head.prev = node;
		head = node;
		size++;
	}
	
	public void deleteFromTail(){
		if(size == 0){
			return ;
		}
		tail = tail.prev;
		tail.next = null;
	}
	
	//检查是否死亡
	public void checkDead(){
		if(head.row < 2 || head.col < 0 || head.row > Yard.getROWS() || 
				head.col > Yard.getCOLS()){
			y.stop();
		}
		
		for(Node n = head.next; n != null; n = n.next){
			if(head.row == n.row && head.col == n.col){
				y.stop();
			}
		}
	}
	
	//蛇移动
	private void move(){
		addToHead();	//把尾巴结点添加到头
		deleteFromTail();
		checkDead();
	}
	
	private class Node{
		int w = Yard.getBLOCK_SIZE();	//结点宽度
		int h = Yard.getBLOCK_SIZE();	//结点高度
		int row,col;	//结点位置
		Direction dir = Direction.LEFT;	//初始结点向左
		Node next = null;	//下一节
		Node prev = null;	//前一节
		
		Node(int row, int col, Direction dir){
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		//画出结点
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(col * Yard.getBLOCK_SIZE(), 
					row * Yard.getBLOCK_SIZE(), w, h);
			g.setColor(c);
		}
	}
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore() + 5);
		}
	}
	
	private Rectangle getRect(){
		return new Rectangle(Yard.getBLOCK_SIZE() * head.col,
				Yard.getBLOCK_SIZE() * head.row, head.w, head.h);
	}
	
	public void KeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			if(head.dir != Direction.RIGHT)
				head.dir = Direction.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Direction.LEFT)
				head.dir = Direction.RIGHT;
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Direction.DOWN)
				head.dir = Direction.UP;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Direction.UP)
				head.dir = Direction.DOWN;
			break;
		}
	}

	public void draw(Graphics g) {
		if(size <= 0){
			return ;
		}
		move();
		for(Node n = head; n != null; n = n.next){
			n.draw(g);
		}
	}
}

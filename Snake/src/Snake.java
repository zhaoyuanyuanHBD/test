import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Rectangle;  
import java.awt.event.KeyEvent;

public class Snake {
	
	private Node head = null;	//ͷ�ڵ�
	private int size = 0;		//�ڵ����
	private Node tail = null;	//β�ڵ�
	
	private Node node = new Node(20,30, Direction.LEFT);	//��ʼ�߽ڵ�
	private Yard y;	//��ʼ��λ��
	
	public Snake(Yard y){
		head = node;
		tail = node;
		size = 1;
		this.y = y;
		
	}
	
	//�ڵ���ӵ�β��
	public void addToTail(){
		Node node = null;
		switch(tail.dir){
		case LEFT:
			node = new Node(tail.row, tail.col + 1, tail.dir);	//�����½��ڵ�
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
		
		//���β���
		tail.next = node;
		node.prev = tail;
		tail = node;
		size++;
	}
	
	
	
	//���ͷ�ڵ�
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
	
	//����Ƿ�����
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
	
	//���ƶ�
	private void move(){
		addToHead();	//��β�ͽ����ӵ�ͷ
		deleteFromTail();
		checkDead();
	}
	
	private class Node{
		int w = Yard.getBLOCK_SIZE();	//�����
		int h = Yard.getBLOCK_SIZE();	//���߶�
		int row,col;	//���λ��
		Direction dir = Direction.LEFT;	//��ʼ�������
		Node next = null;	//��һ��
		Node prev = null;	//ǰһ��
		
		Node(int row, int col, Direction dir){
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		//�������
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

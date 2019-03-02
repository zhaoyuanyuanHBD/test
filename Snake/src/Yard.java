
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Frame;  
import java.awt.Graphics;  
import java.awt.Image;  
import java.awt.event.KeyAdapter;  
import java.awt.event.KeyEvent;  
import java.awt.event.KeyListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  

public class Yard extends Frame{
	
	//�����
	private static final int ROWS = 30;	//����
	private static final int COLS = 30;	//����
	private static final int BLOCK_SIZE = 15;	//���ӳߴ�
	
	private Font fontGameOver = new Font("����",Font.BOLD,50);	//����
	private int score = 0;	//����
	
	Image offScreenImage = null;	//����һ��λͼ����ֹ��˸
	
	Snake snake = new Snake(this);	//������
	Egg e = new Egg();	//����ʳ��
	
	PaintThread paintThread = new PaintThread();
	private boolean gameOver = false;	//�ж���Ϸ�Ƿ����
	
	public static int getROWS(){
		return ROWS;
	}
	
	public static int getCOLS(){
		return COLS;
	}
	
	public static int getBLOCK_SIZE(){
		return BLOCK_SIZE;
	}
	
	public int getScore(){
		return score;
	}
	
	//���ô���
	public void launch(){
		this.setLocation(300,300);	//���ڳ���λ��
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);	//���ڴ�С
		//��ӹر��¼�
		this.addWindowListener(new WindowAdapter(){			
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		this.setVisible(true);	//��ʾ����
		
		this.addKeyListener(new KeyMonitor());	//��������
		new Thread(paintThread).start();	//�����߳�
	}
	
	//���÷���
	public void setScore(int score){
		this.score = score;
	}
	
	//��ͼ
	public void paint(Graphics g){
		
		//���ñ�����ɫ
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		
		//�����ߵ���ɫ
		g.setColor(Color.BLACK);
		
		//����
		for(int i = 1;i < COLS; i++){
			g.drawLine(0, i * BLOCK_SIZE, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		//ǰ����������㣬�����������յ�
		for(int i = 1; i < ROWS; i++){
			g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, BLOCK_SIZE * ROWS);
		}
		
		g.setColor(Color.BLUE);
		g.drawString("Score:" + score, 10, 60);	//������
		
		if(gameOver){
			g.setFont(fontGameOver);
			g.drawString("��Ϸ����", 120, 180);
			//String �ı�������
			
			paintThread.pause();
		}
		
		g.setColor(c);
		
		snake.eat(e);
		snake.draw(g);	//����
		e.draw(g);		//��ʳ��
		
	}
	
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	public void stop(){
		gameOver = true;	//��Ϸ����
	}
	
	//˽����
	private class PaintThread implements Runnable{

		private boolean pause = false;
		private boolean running = true;
		
		public void pause() {
			this.pause = true;
		}

		public void run() {
			
			while(running){
				if(pause) continue;
				else repaint();
				
				try{
					Thread.sleep(200);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		public void reStart(){
			this.pause = false;
			snake = new Snake(Yard.this);
			gameOver = false;
		}
		
		public void gameOver(){
			running = false;
		}
	}
	
	private class KeyMonitor extends KeyAdapter{
		//��ֵ����
		
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2){
				paintThread.reStart();
			}
			snake.KeyPressed(e);
		}
	}
	
	public static void main(String[] args){
		new Yard().launch();
	}
}

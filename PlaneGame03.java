package PlaneGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
public class PlaneGame03 extends Frame {
	
	Image  bg=GameUtil.getImage("images/bg.jpg");
	Image  planeimg=GameUtil.getImage("images/plane.png");
	
     Plane plane=new Plane(planeimg,400,500);//�ɻ�������
     Shell[] shells=new Shell[50];
     Explode explode;
     Date starttime=new Date();
     Date endtime;
     int livetime;
	public void paint(Graphics g) {
		    Color c=g.getColor();
	    	g.drawImage(bg, 0, 0, null);
	    	//���ɻ�
	    	plane.drawSelf(g); 
	    	//�����е��ڵ�
	    	for(int i=0;i<shells.length;i++) {
	    		shells[i].draw(g);
	    		boolean peng=shells[i].getRect().intersects(plane.getRect());
	    		if(peng) {
	    			plane.live=false;
	    			if(explode==null) {
	    			explode=new Explode(plane.x,plane.y);
	    			}
	    			explode.draw(g);
	    			endtime=new Date();
	    		}
	    	  	if(!plane.live) {
	            	  g.setColor(Color.RED);
	            	  livetime=((int)endtime.getTime()-(int)starttime.getTime())/1000;
	            	  g.drawString("���ʱ�䣺"+livetime+"��", (int)plane.x, (int)plane.y);
	            }
	    	}
	         g.setColor(c);
	     }
	 //�������ʹ���ⲿ��ķ���������
	 class PaintThread extends Thread{
		 public void run() {
			 while(true) {
				 repaint();
				
				 try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
	 }
	 //���̼���
	 class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
		 
	 }
	 //��ʼ������
     public void LaunchFrame() {
    	 this.setTitle("�ɻ���Ϸ");
    	 this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
    	 this.setLocation(150,150);
    	 this.setVisible(true);
    	 this.addWindowListener(new WindowAdapter() {
    		 
    	    public void windowClosing(WindowEvent e) {
    	    	System.exit(0);
    	    }
    	 });
    	 new PaintThread().start();//�����߳�
    	 addKeyListener(new KeyMonitor());//���������Ӽ��̵ļ���
    	 //��ʼ���ڵ�
    	 for(int i=0;i< shells.length;i++) {
    		 shells[i]=new Shell();
    	 }
     }
   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
          PlaneGame03 pg=new PlaneGame03();
          pg.LaunchFrame();
   
	}
	//����˫���壬ʹ���ڲ���˸
	private Image offScreenImage =null;
	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(800,800);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

}

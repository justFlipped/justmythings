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
	
     Plane plane=new Plane(planeimg,400,500);//飞机的坐标
     Shell[] shells=new Shell[50];
     Explode explode;
     Date starttime=new Date();
     Date endtime;
     int livetime;
	public void paint(Graphics g) {
		    Color c=g.getColor();
	    	g.drawImage(bg, 0, 0, null);
	    	//画飞机
	    	plane.drawSelf(g); 
	    	//画所有的炮弹
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
	            	  g.drawString("存活时间："+livetime+"秒", (int)plane.x, (int)plane.y);
	            }
	    	}
	         g.setColor(c);
	     }
	 //可以随便使用外部类的方法和属性
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
	 //键盘监听
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
	 //初始化窗口
     public void LaunchFrame() {
    	 this.setTitle("飞机游戏");
    	 this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
    	 this.setLocation(150,150);
    	 this.setVisible(true);
    	 this.addWindowListener(new WindowAdapter() {
    		 
    	    public void windowClosing(WindowEvent e) {
    	    	System.exit(0);
    	    }
    	 });
    	 new PaintThread().start();//启动线程
    	 addKeyListener(new KeyMonitor());//给窗口怎加键盘的监听
    	 //初始化炮弹
    	 for(int i=0;i< shells.length;i++) {
    		 shells[i]=new Shell();
    	 }
     }
   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
          PlaneGame03 pg=new PlaneGame03();
          pg.LaunchFrame();
   
	}
	//加入双缓冲，使窗口不闪烁
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

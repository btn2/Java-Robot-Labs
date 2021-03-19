import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import java.net.URL;

public class RobotVision extends Frame
{
   private Image karelImageN;
   private Image karelImageS;
   private Image karelImageE;
   private Image karelImageW;
   
   private static int worldHeight;
   private static int worldWidth = 640;
   private static final int leftEdge = 20;
   private static int bottomEdge;
   private static final int wallSize = 4;
   private ViewCanvas worldView;
   private int delay;
   private ControlThread controlThread;

   static 
   {
      worldHeight = 480;
      bottomEdge = worldHeight - 20;
   }

   class ControlThread extends Thread
   {
      private Dialog frame;
      private Button stop;
      private Panel controls;
      private Scrollbar speed;
      private Button restore;
      private boolean started;
      
      ControlThread()
      {
         frame = new Dialog(new Frame("empty"), "Control");
         stop = new Button("Resume");
         controls = new Panel();
         speed = new Scrollbar(0);
         restore = new Button("Restore");
         started = false;
      }
   
      private class SpeedListener implements AdjustmentListener
      {
         public void adjustmentValueChanged(AdjustmentEvent e)
         {
            delay = speed.getValue();
            World.setDelay(100 - delay);
            worldView.repaint();
         }
         SpeedListener()
         {
         }
      }
      private class StopListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            if(e.getActionCommand().equals("Stop"))
            {
               stop.setLabel("Resume");
               World.stop();
            } 
            else
            {
               stop.setLabel("Stop");
               if(started)
               {
                  World.resume();
               } 
               else
               {
                  started = true;
                  World.startThreads();
               }
            }
         }
         StopListener()
         {
         }
      }
      public void run()
      {
         SpeedListener speedListener = new SpeedListener();
         speed.addAdjustmentListener(speedListener);
         controls.setSize(300, 50);
         controls.setLayout(new GridLayout(1, 4));
         speed.setValues(0, 0, 0, 101);
         speed.setSize(300, speed.getSize().height);
         speed.setValue(100 - World.delay());
         stop.addActionListener(new StopListener());
         controls.add(stop);
         controls.add(new Label("Set Speed", 2));
         controls.add(speed);
         frame.add(controls);
         frame.setLocation(500, 400);
         frame.setSize(300, 50);
         //frame.setVisible(true);
      }
      public final void doStop()
      {
         frame.setVisible(false);
         stop();
      }
   }
         
   class ViewCanvas extends Canvas
   {
      private Image offScreen;
      private Graphics osg;
      private boolean resized;
      int scale;
      ViewCanvas()
      {
         offScreen = null;
         osg = null;
         resized = false;
      }
   
      public synchronized void paint(Graphics g)
      {
         int streets = World.numberOfStreets();
         int avenues = World.numberOfAvenues();
         scale = (RobotVision.worldHeight - 40) / streets;
         setBackground(Color.white);
         g.setColor(Color.red);
         for(int i = 1; i <= streets; i++)
            g.drawLine(20 + scale / 2, RobotVision.bottomEdge - i * scale, 20 + avenues * scale, RobotVision.bottomEdge - i * scale);
         for(int i = 1; i <= avenues; i++)
            g.drawLine(20 + i * scale, RobotVision.bottomEdge - scale / 2, 20 + i * scale, RobotVision.bottomEdge - streets * scale);
         g.setColor(Color.black);
         g.fillRect((20 + scale / 2) - 4, RobotVision.bottomEdge - streets * scale, 4, streets * scale - scale / 2);
         g.fillRect(20 + scale / 2, RobotVision.bottomEdge - scale / 2, avenues * scale, 4);
         World.IntPair p;
         for(Enumeration e = World.ewWalls(); e.hasMoreElements(); drawHWall(p.street(), p.avenue(), g))
            p = (World.IntPair)e.nextElement();
         for(Enumeration e = World.nsWalls(); e.hasMoreElements(); drawVWall(p.street(), p.avenue(), g))
            p = (World.IntPair)e.nextElement();
         World.BeeperCell ppp;
         for(Enumeration e = World.beepers(); e.hasMoreElements(); drawBeeper(ppp.street(), ppp.avenue(), ppp.number(), g, g.getColor()))
            ppp = (World.BeeperCell)e.nextElement();
         Robot pp;
         //*********OBERLE************add custom images for different robots
         for(Enumeration e = World.robots(); e.hasMoreElements(); drawRobot(pp, g, g.getColor()))// drawRobot(pp.street(), pp.avenue(), pp.direction(), pp.running(), g, g.getColor()))
            pp = (Robot)e.nextElement();
      }
      
      public final synchronized void update(Graphics g)
      {
         setSize(getSize());
         int newHeight = getSize().height - 20;
         int newWidth = getSize().width;
         resized = RobotVision.worldHeight != newHeight || RobotVision.worldWidth != newWidth;
         RobotVision.worldHeight = newHeight;
         RobotVision.worldWidth = newWidth;
         RobotVision.bottomEdge = RobotVision.worldHeight - 20;
         if(resized)
         {
            offScreen = null;
            resized = false;
         }
         if(resized || offScreen == null)
         {
            offScreen = createImage(RobotVision.worldWidth, RobotVision.worldHeight);
            resized = false;
         }
         if(offScreen != null)
         {
            if(osg != null)
               osg.dispose();
            osg = offScreen.getGraphics();
            paint(osg);
            g.drawImage(offScreen, 0, 0, null);
            offScreen.flush();
            offScreen = null;
         }
      }
      
      private void drawHWall(int s, int a, Graphics g)
      {
         g.fillRect((20 + a * scale) - scale / 2, RobotVision.bottomEdge - s * scale - scale / 2, scale, 4);
      }
      
      private void drawVWall(int s, int a, Graphics g)
      {
         g.fillRect(20 + a * scale + scale / 2, RobotVision.bottomEdge - s * scale - scale / 2, 4, scale);
      }
      
      private synchronized void drawBeeper(int s, int a, int howMany, Graphics g, Color c)
      {
         Color oldColor = g.getColor();
         g.setColor(c);
         g.fillOval((20 + a * scale) - scale / 4, RobotVision.bottomEdge - s * scale - scale / 4, scale / 2, scale / 2);
         g.setColor(Color.white);
         String name = howMany <= 0 ? "oo" : String.valueOf(howMany);
         g.drawString(name, (20 + a * scale) - scale / 8, (RobotVision.bottomEdge - s * scale) + scale / 8);
         g.setColor(oldColor);
      }
      
   	//*********OBERLE************add custom images for different robots
      private synchronized void drawRobot(Robot arg, Graphics g, Color c)
       //private synchronized void drawRobot(int s, int a, int facing, boolean running, Graphics g, Color c)
      {
         int s = arg.street();
         int a = arg.avenue();
         int facing = arg.direction();
         boolean running = arg.running();
         String name = arg.imageName();
         URL u1;         
         switch(facing)
         {
            case 3: // '\003'
               if(!name.equals("karel"))
               {
                  u1 = RobotVision.class.getResource(name+"n.gif");
                  if(u1 == null)
                  {
                     System.out.println("Invalid image name - using default");
                     name = "karel";
                     u1 = RobotVision.class.getResource(name+"n.gif");
                  }
                  karelImageN = Toolkit.getDefaultToolkit().getImage(u1);
               }
               g.drawImage(karelImageN, (20 + a * scale) - 14, RobotVision.bottomEdge - s * scale - 12, null);
               break;
            case 0: // '\0'
               if(!name.equals("karel"))
               {
                  u1 = RobotVision.class.getResource(name+"e.gif");
                  if(u1 == null)
                  {
                     System.out.println("Invalid image name - using default");
                     name = "karel";
                     u1 = RobotVision.class.getResource(name+"e.gif");
                  }
                  karelImageE = Toolkit.getDefaultToolkit().getImage(u1);
               }
               g.drawImage(karelImageE, (20 + a * scale) - 12, RobotVision.bottomEdge - s * scale - 14, null);
               break;
            case 1: // '\001'
               if(!name.equals("karel"))
               {
                  u1 = RobotVision.class.getResource(name+"s.gif");
                  if(u1 == null)
                  {
                     System.out.println("Invalid image name - using default");
                     name = "karel";
                     u1 = RobotVision.class.getResource(name+"s.gif");
                  }
                  karelImageS = Toolkit.getDefaultToolkit().getImage(u1);
               }
               g.drawImage(karelImageS, (20 + a * scale) - 14, RobotVision.bottomEdge - s * scale - 12, null);
               break;
            case 2: // '\002'
               if(!name.equals("karel"))
               {
                  u1 = RobotVision.class.getResource(name+"w.gif");
                  if(u1 == null)
                  {
                     System.out.println("Invalid image name - using default");
                     name = "karel";
                     u1 = RobotVision.class.getResource(name+"w.gif");
                  }
                  karelImageW = Toolkit.getDefaultToolkit().getImage(u1);
               }
               g.drawImage(karelImageW, (20 + a * scale) - 12, RobotVision.bottomEdge - s * scale - 14, null);
               break;
         }
      }
   }
 //******************************************************************/  
	
   private class CloseListener extends WindowAdapter
   {
      public void windowClosing(WindowEvent e)
      {
         e.getWindow().setVisible(false);
         controlThread.doStop();
         World.stop();
         System.exit(0);
      }
      CloseListener()
      {
      }
   }
   
   public RobotVision()
   {
      super("JKarel");
      worldView = new ViewCanvas();
      delay = 100;
      controlThread = new ControlThread();
      setSize(worldWidth, worldHeight + 20);
      add("Center", worldView);
      addWindowListener(new CloseListener());
      setBackground(Color.white);
      repaint();
      setVisible(true);
      controlThread.start();
   
      URL u1 = RobotVision.class.getResource("kareln.gif");
      karelImageN = Toolkit.getDefaultToolkit().getImage(u1);
      URL u2 = RobotVision.class.getResource("karels.gif");
      karelImageS = Toolkit.getDefaultToolkit().getImage(u2);
      URL u3 = RobotVision.class.getResource("karele.gif");
      karelImageE = Toolkit.getDefaultToolkit().getImage(u3);
      URL u4 = RobotVision.class.getResource("karelw.gif");
      karelImageW = Toolkit.getDefaultToolkit().getImage(u4);
   
      MediaTracker track = new MediaTracker(this);
      track.addImage(karelImageN, 0);
      track.addImage(karelImageS, 1);
      track.addImage(karelImageE, 2);
      track.addImage(karelImageW, 3);
      try
      {
         track.waitForID(0);
         track.waitForID(1);
         track.waitForID(2);
         track.waitForID(3);
      }
      catch(InterruptedException _ex) { 
      }
   }
   public void reset()
   {
      controlThread.stop.setLabel("Resume");
      World.stop();
      worldView.repaint();
   }
   public synchronized void prepareToDraw(Robot.StateObject so)
   {
      worldView.repaint();
   }
   public synchronized void repaint()
   {
      worldView.repaint();
   }
}
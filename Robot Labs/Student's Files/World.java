import java.io.*;
import java.util.*;

public class World
{
   public static final int NORTH = 3;
   public static final int WEST = 2;
   public static final int SOUTH = 1;
   public static final int EAST = 0;
   public static final int INFINITY = -1;
      
   private static RobotVision view = new RobotVision();
   private static Vector threadVector = new Vector();
   private static Hashtable beepers = new Hashtable();
   private static Hashtable ewWalls = new Hashtable();
   private static Hashtable nsWalls = new Hashtable();
   private static final Integer foo = new Integer(1);
   private static Vector robots = new Vector();
   private static RobotObserver myObserver = new RobotObserver();
   private static int delay = 1;
   private static int streets = 10;
   private static int avenues = 10;
   private static ThreadGroup threads = new ThreadGroup("RobotThread");
   private static boolean tracing = true;
   private static Hashtable checkpointBeepers = null;
   //********BRIAN*********logging state
   private static boolean logging = true;
   /****************************************/   
   
   	
   public World()
   {
   }
   	
   public static void pause(int seconds)
   {
      try
      {
         Thread.sleep(seconds * 1000);
      }
      catch (Exception e)
      {
      }
   }  
      
   public static void setSpeed(int x)
   {
      int y = 1;
      for(int z = 10; z > x; z--)
         y += 10;
      setDelay(y);
   }
      
   private static class BeeperEnum implements Enumeration
   {
      public boolean hasMoreElements()
      {
         return k.hasMoreElements();
      }
      public Object nextElement()
      {
         IntPair p = (IntPair)k.nextElement();
         Integer val = (Integer)v.nextElement();
         return new BeeperCell(p.s, p.a, val.intValue());
      }
      private Enumeration k;
      private Enumeration v;
      BeeperEnum()
      {
         k = World.beepers.keys();
         v = World.beepers.elements();
      }
   }
      
   static class BeeperCell
   {
      public int street()
      {
         return street;
      }
      public int avenue()
      {
         return avenue;
      }
      public int number()
      {
         return number;
      }
      private int street;
      private int avenue;
      private int number;
      public BeeperCell(int s, int a, int n)
      {
         street = s;
         avenue = a;
         number = n;
      }
   }
      
   static class IntPair
   {
      public boolean equals(Object o)
      {
         if(!(o instanceof IntPair))
            return false;
         else
            return s == ((IntPair)o).s && a == ((IntPair)o).a;
      }
      public String toString()
      {
         return "<" + s + "," + a + ">";
      }
      public int hashCode()
      {
         return a + 1001 * s;
      }
      public int street()
      {
         return s;
      }
      public int avenue()
      {
         return a;
      }
      private int s;
      private int a;
      IntPair(int s, int a)
      {
         this.s = s;
         this.a = a;
      }
   }
      
   static class RobotObserver implements Observer
   {
      public synchronized void update(Observable o, Object s)
      {
         if(World.tracing && logging)
            System.out.println(o);
         World.view.prepareToDraw((Robot.StateObject)s);
      }
      RobotObserver()
      {
      }
   }   
      
   public static synchronized void placeBeeper(int Avenue, int Street)
   {
      placeBeepers(Avenue, Street, 1);
   }
      
   public static synchronized void placeBeepers(int Avenue, int Street, int howMany)
   {
      IntPair where = new IntPair(Street, Avenue);
      Integer p = (Integer)beepers.get(where);
      if(p == null)
      {
         if(howMany > 0 || howMany == -1)
            beepers.put(where, new Integer(howMany));
      } 
      else
      {
         if(p.intValue() == -1)
            return;
         beepers.remove(where);
         int newval = p.intValue() + howMany;
         if(newval > 0)
            beepers.put(where, new Integer(newval));
      }
   }
      
   public static synchronized boolean decreaseBeeperIfPossible(int Street, int Avenue)
   {
      boolean result = false;
      if(checkBeeper(Street, Avenue))
      {
         result = true;
         placeBeepers(Avenue, Street, -1);
      }
      return result;
   }
      
   public static synchronized boolean checkBeeper(int Street, int Avenue)
   {
      IntPair p = new IntPair(Street, Avenue);
      return beepers.get(p) != null;
   }
      
   public static void addRobot(Robot k)
   {
      robots.addElement(k);
      k.addObserver(myObserver);
      if(tracing && logging)
         System.out.println(k);
      view.repaint();
   } 
   
      //*****BRIAN********return the vector of robots currently present in the world
   public static Vector getRobots(){
      return robots;
   }
      //*******************************************************/

      
   public static synchronized boolean checkRobot(Robot k, int street, int avenue)
   {
      for(Enumeration e = robots.elements(); e.hasMoreElements();)
      {
         Robot o = (Robot)e.nextElement();
         if(o != k && o.areYouHere(street, avenue))
            return true;
      }
      return false;
   }
      
   	//*****OBERLE********return the robot type that you are on
   public static synchronized Robot getRobot(Robot k, int street, int avenue)
   {
      for(Enumeration e = robots.elements(); e.hasMoreElements();)
      {
         Robot o = (Robot)e.nextElement();
         if(o != k && o.areYouHere(street, avenue))
            return o;
      }
      return null;
   }
   	//*******************************************************/
   	
   public static Enumeration ewWalls()
   {
      return ewWalls.keys();
   }
      
   public static Enumeration nsWalls()
   {
      return nsWalls.keys();
   }
      
   public static Enumeration robots()
   {
      return robots.elements();
   }
      
   public static Enumeration beepers()
   {
      return new BeeperEnum();
   }
      
   public static final boolean checkEWWall(int NorthOfStreet, int atAvenue)
   {
      if(NorthOfStreet == 0)
      {
         return true;
      } 
      else
      {
         IntPair p = new IntPair(NorthOfStreet, atAvenue);
         return ewWalls.get(p) != null;
      }
   }
      
   public static final boolean checkNSWall(int atStreet, int EastOfAvenue)
   {
      if(EastOfAvenue == 0)
      {
         return true;
      } 
      else
      {
         IntPair p = new IntPair(atStreet, EastOfAvenue);
         return nsWalls.get(p) != null;
      }
   }
      
   public static final void placeEWWall(int NorthOfStreet, int atAvenue, int lengthTowardEast)
   {
      for(int i = 0; i < lengthTowardEast; i++)
         ewWalls.put(new IntPair(NorthOfStreet, atAvenue + i), foo);
   }
      
   public static final void placeNSWall(int atStreet, int EastOfAvenue, int lengthTowardNorth)
   {
      for(int i = 0; i < lengthTowardNorth; i++)
         nsWalls.put(new IntPair(atStreet + i, EastOfAvenue), foo);
   }
      
   public static final void saveWorld(String filename)
   {
      IntPair p;
      try
      {
         BufferedWriter w = new BufferedWriter(new FileWriter(filename));
         for(Enumeration e = beepers.keys(); e.hasMoreElements(); w.newLine())
         {
            p = (IntPair)e.nextElement();
            w.write("beepers " + p.s + " " + p.a + " " + beepers.get(p));
         }
         for(Enumeration e = ewWalls.keys(); e.hasMoreElements(); w.newLine())
         {
            p = (IntPair)e.nextElement();
            w.write("eastwestwalls " + p.s + " " + p.a + " " + p.a);
         }
         for(Enumeration e = nsWalls.keys(); e.hasMoreElements(); w.newLine())
         {
            p = (IntPair)e.nextElement();
            w.write("northsouthwalls " + p.a + " " + p.s + " " + p.s);
         }
         w.close();
      }
      catch(IOException _ex)
      {
         System.out.println("Can't save world.");
      }
   }
      
   public static final void showWorld()
   {
      Enumeration e = beepers.keys();
      System.out.println();
      IntPair p;
      int b;
      for(; e.hasMoreElements(); System.out.println((b < 0 ? "infinite number of" : String.valueOf(b)) + " beepers at " + p.s + " street and " + p.a + " avenue"))
      {
         p = (IntPair)e.nextElement();
         b = ((Integer)beepers.get(p)).intValue();
      }
      for(e = ewWalls.keys(); e.hasMoreElements(); System.out.println("east west wall above " + p.s + " street crossing " + p.a + " avenue"))
         p = (IntPair)e.nextElement();
      for(e = nsWalls.keys(); e.hasMoreElements(); System.out.println("north south wall east of " + p.a + " avenue crossing " + p.s + " street"))
         p = (IntPair)e.nextElement();
      System.out.println();
   }
      
   public static final void readWorld(String filename)
   {
      if(filename.indexOf(".") == -1)
      {
         readWorld("worlds\\" + filename + ".wld");
         return;
      }
      try
      {
         FileInputStream f = new FileInputStream(filename);
         InputStreamReader r = new InputStreamReader(f);
         int size = f.available();
         char buf[] = new char[size];
         r.read(buf, 0, size);
         for(StringTokenizer t = new StringTokenizer(new String(buf)); t.hasMoreTokens();)
         {
            String token = t.nextToken();
            if(token.equals("beepers"))
            {
               int s = Integer.parseInt(t.nextToken());
               int a = Integer.parseInt(t.nextToken());
               int n = Integer.parseInt(t.nextToken());
               placeBeepers(a, s, n);
            } 
            else
               if(token.equals("eastwestwalls"))
               {
                  int s = Integer.parseInt(t.nextToken());
                  int a = Integer.parseInt(t.nextToken());
                  int n = Integer.parseInt(t.nextToken());
                  placeEWWall(s, a, (n - a) + 1);
               } 
               else
                  if(token.equals("northsouthwalls"))
                  {
                     int a = Integer.parseInt(t.nextToken());
                     int s = Integer.parseInt(t.nextToken());
                     int n = Integer.parseInt(t.nextToken());
                     placeNSWall(s, a, (n - s) + 1);
                  }
         }
         r.close();
      }
      catch(IOException _ex)
      {
         System.out.println("Can't read world.");
      }
   }
      
   public static final void reset()
   {
      beepers.clear();
      ewWalls.clear();
      nsWalls.clear();
      for(Enumeration e = robots.elements(); e.hasMoreElements(); ((Robot)e.nextElement()).deleteObserver(myObserver));
      robots.setSize(0);
   }
      
   public static final void setDelay(int d)
   {
      if(d < 0)
         d = 0;
      delay = d;
   }
      
   public static final int delay()
   {
      return delay;
   }
      
   public static final void resume()
   {
      threads.resume();
   }
      
   public static final void stop()
   {
      threads.suspend();
   }
      
   public static final void setSize(int numberOfAvenues, int numberOfStreets)
   {
      streets = numberOfStreets;
      avenues = numberOfAvenues;
   }
      
   public static final void setTrace(boolean t)
   {
      tracing = t;
   }
      
   public static final void setupThread(Runnable r)
   {
      Thread t = new Thread(threads, r);
      threadVector.addElement(t);
   }
      
   public static final void startThreads()
   {
      for(Enumeration e = threadVector.elements(); e.hasMoreElements(); ((Thread)e.nextElement()).start());
   }
   
   //********BRIAN*******enable robot status log to console
   public static void enableLogging(){
      logging = true;
   }
   
   //********BRIAN*******disable robot status log to console
   public static void disableLogging(){
      logging = false;
   }
   //************************************************/
      
   public static final int numberOfStreets()
   {
      return streets;
   }
      
   public static final int numberOfAvenues()
   {
      return avenues;
   }
      
   	//************OBERLE*************return Panel so a keylistener can be added
   public static RobotVision getView()
   {
      return view;
   }
   	//**************---********************************************************/
   
   //************OBERLE*************return # beepers in the world
   public static int numBeepers()
   {
      return beepers.size();
   }
      //**********************************************************/
}

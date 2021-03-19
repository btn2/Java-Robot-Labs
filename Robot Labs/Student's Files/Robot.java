import java.io.*;
import java.util.Observable;
import java.util.Vector;
public class Robot extends Observable
{
   private Vector senders;
   private int nextSender;
   private static final boolean on = true;
   private static final boolean off = false;
   private int loc[];
   private int beepers;
   private int direction;
   private int moves;
   private boolean state;
   private int idNumber;
   private static final int threshhold = 10;
   private static int numberOfRobots = 0;
   private StateObject initialState;
   //********OBERLE*********add custom images
   private String imageName;
   //****************************************/
   public Robot(int avenue, int street, int direction, int beepers)
   {
      senders = new Vector();
      nextSender = 0;
      loc = new int[4];
      moves = 0;
      state = true;
      loc[3] = street;
      loc[1] = 0;
      loc[0] = avenue;
      loc[2] = 0;
      this.direction = direction % 4;
      this.beepers = beepers;
      validate();
      idNumber = incrementRobots();
      state = true;
      imageName="karel";
      initialState = new StateObject(-1);
      World.addRobot(this);
      sleep();
   }
     	
   public Robot()
   {
      this(1, 1, World.EAST, 0);
   }
   
	 //********OBERLE*********add custom images   
   public Robot(int avenue, int street, int direction, int beepers, String id)
   {
      senders = new Vector();
      nextSender = 0;
      loc = new int[4];
      moves = 0;
      state = true;
      loc[3] = street;
      loc[1] = 0;
      loc[0] = avenue;
      loc[2] = 0;
      this.direction = direction % 4;
      this.beepers = beepers;
      validate();
      idNumber = incrementRobots();
      state = true;
      imageName=id;
      initialState = new StateObject(-1);
      World.addRobot(this);
      sleep();
   }
     	
   public Robot(String id)
   {
      this(1, 1, World.EAST, 0, id);
   }

   public String imageName()
   {
      return imageName;
   }
   
   public void setImageName(String id)
   {
      imageName = id;
   }
	//****************************************************/		
   public boolean leftIsClear()
   {
      boolean answer;
      turnLeft();
      answer = frontIsClear();
      turnLeft();
      turnLeft();
      turnLeft();
      return answer;
   }
   	
   public boolean rightIsClear()
   {
      boolean answer;
      turnLeft();
      turnLeft();
      turnLeft();
      answer = frontIsClear();
      turnLeft();
      return answer;
   }	
   
   public boolean frontIsClear()
   {
      switch(direction())
      {
         default:
            break;
         case 3: // '\003'
            if(World.checkEWWall(street(), avenue()))
               return false;
            break;
         case 0: // '\0'
            if(World.checkNSWall(street(), avenue()))
               return false;
            break;
         case 1: // '\001'
            if(World.checkEWWall(street() - 1, avenue()))
               return false;
            break;
         case 2: // '\002'
            if(World.checkNSWall(street(), avenue() - 1))
               return false;
            break;
      }
      return true;
   }
   
   //********OBERLE*******name change: use to be nextToABeeper (misleading)
   public boolean onABeeper()
   {
      sleep();
      return World.checkBeeper(street(), avenue());
   }
   
   //********OBERLE*******name change: use to be nextToARobot misleading)
   public boolean onARobot()
   {
      sleep();
      return World.checkRobot(this, street(), avenue());
   }
   
	//*****OBERLE********return the robot type that you are on
   public Robot getARobot()
   {
      sleep();
      return World.getRobot(this, street(), avenue());
   }
	//*******************************************************

//************OBERLE*****teleport to location (stree, avanue)
   public void teleport(int street, int avenue)
   {
      loc[3] = street;
      loc[1] = 0;
      loc[0] = avenue;
      loc[2] = 0;
      moves++;
      if(moves > 10)
         normalize();
      validate();
      StateObject s = new StateObject(0);
      setChanged();
      notifyObservers(s);
      sleep();
   }
//************************************************/
	
   public boolean facingNorth()
   {
      return direction() == 3;
   }
   
   public boolean facingSouth()
   {
      return direction() == 1;
   }
   
   public boolean facingEast()
   {
      return direction() == 0;
   }
   
   public boolean facingWest()
   {
      return direction() == 2;
   }
   
   //********OBERLE*******name change: use to be anyBeepersInBeeperBag (too long)
   public boolean hasBeepers()
   {
      return beepers() > 0 || beepers() == -1;
   }
   
   public static interface ConnectStrategy
   {
      public abstract void action(Robot ur_robot, Robot ur_robot1, BufferedReader bufferedreader);
   }
   
   static interface Action
   {
      public static final int move = 0;
      public static final int turnLeft = 1;
      public static final int pickBeeper = 2;
      public static final int putBeeper = 3;
      public static final int turnOff = 4;
      public static final int initial = -1;
   }
   
   final class StateObject implements Serializable
   {
      public int street()
      {
         return street;
      }
      public int avenue()
      {
         return avenue;
      }
      public int direction()
      {
         return direction;
      }
      public int beepers()
      {
         return beepers;
      }
      public int lastAction()
      {
         return lastAction;
      }
      
      private int street;
      private int avenue;
      private int direction;
      private int beepers;
      private int lastAction;
       
      public StateObject(int lastAction)
      {
         street = loc[3] - loc[1];
         avenue = loc[0] - loc[2];
         direction = Robot.this.direction;
         beepers = Robot.this.beepers;
         this.lastAction = lastAction;
      }
   }
   
   public void turnLeft()
   {
      if(state)
      {
         direction--;
         if(direction < 0)
            direction += 4;
         direction %= 4;
         StateObject s = new StateObject(1);
         setChanged();
         notifyObservers(s);
         sleep();
      }
   }
   
   public void move()
   {
      if(state)
      {
         boolean crashed = false;
         normalize();
         switch(direction)
         {
            case 3: // '\003'
               if(World.checkEWWall(loc[3], loc[0]))
                  crashed = crash("Tried to walk through an East West wall");
               break;
            case 0: // '\0'
               if(World.checkNSWall(loc[3], loc[0]))
                  crashed = crash("Tried to walk through a North South wall");
               break;
            case 1: // '\001'
               if(World.checkEWWall(loc[3] - 1, loc[0]))
                  crashed = crash("Tried to walk through an East West wall");
               break;
            case 2: // '\002'
               if(World.checkNSWall(loc[3], loc[0] - 1))
                  crashed = crash("Tried to walk through a North South wall");
               break;
         }
         if(!crashed)
         {
            loc[direction]++;
            moves++;
            if(moves > 10)
               normalize();
            validate();
            StateObject s = new StateObject(0);
            setChanged();
            notifyObservers(s);
            sleep();
         }
      }
   }
   
   public void pickBeeper()
   {
      if(state)
      {
         normalize();
         boolean crashed = false;
         if(!World.checkBeeper(loc[3], loc[0]))
            crashed = crash("No beepers to pick");
         if(!crashed)
         {
            if(beepers != -1)
               beepers++;
            World.placeBeepers(loc[0], loc[3], -1);
            StateObject s = new StateObject(2);
            setChanged();
            notifyObservers(s);
            sleep();
         }
      }
   }
   
   public void putBeeper()
   {
      if(state)
      {
         normalize();
         if(beepers == 0)
            crash("No beepers to put.");
         if(beepers != -1)
            beepers--;
         if(!validate())
         {
            if(beepers != -1)
               beepers++;
         } 
         else
         {
            World.placeBeepers(loc[0], loc[3], 1);
            StateObject s = new StateObject(3);
            setChanged();
            notifyObservers(s);
         }
         sleep();
      }
   }
   
   public void restoreInitialState()
   {
      loc[3] = initialState.street;
      loc[1] = 0;
      loc[0] = initialState.avenue;
      loc[2] = 0;
      direction = initialState.direction;
      beepers = initialState.beepers;
      state = true;
      showState("Restoring ");
      setChanged();
      notifyObservers(initialState);
      sleep();
   }
   
   public final String toString()
   {
      normalize();
      return "RobotID " + idNumber + " at (street: " + loc[3] + ") (avenue: " + loc[0] + ") (beepers: " + (beepers < 0 ? "infinite" : String.valueOf(beepers)) + ") ( direction: " + direction(direction) + (state ? ") on" : ") off");
   }
   
   private String direction(int d)
   {
      switch(d)
      {
         case 0: // '\0'
            return "East";
         
         case 1: // '\001'
            return "South";
         
         case 2: // '\002'
            return "West";
         
         case 3: // '\003'
            return "North";
      }
      return "ERROR";
   }
   
   protected void sleep()
   {
      try
      {
         Thread.sleep(10 * World.delay());
      }
      catch(InterruptedException _ex) { 
      }
   }
   
   public final void showState(String s)
   {
      normalize();
      System.out.println(s + this);

   }
   
   public final boolean areYouHere(int street, int avenue)
   {
      normalize();
      return loc[3] == street && loc[0] == avenue;
   }
   
   private boolean validate()
   {
      normalize();
      if(beepers < -1)
         return crash("Robot has negative beepers") ^ true;
      if(loc[3] < 1)
         return crash("Robot tried to move through South boundary wall") ^ true;
      if(loc[0] < 1)
         return crash("Robot tried to move through West boundary wall") ^ true;
      else
         return true;
   }
   
   private boolean crash(String s)
   {
      state = false;
      showState("Error shutoff: ");
      System.out.println(s);
      pauseExit();
      return true;
   }
   
   private void pauseExit()
   {
      try
      {
         System.in.read();
      }
      catch(IOException _ex) { 
      }
      System.exit(0);
   }
   
   private void normalize()
   {
      moves = 0;
      loc[3] -= loc[1];
      loc[1] = 0;
      loc[0] -= loc[2];
      loc[2] = 0;
   }
   
   protected final int beepers()
   {
      return beepers;
   }
   
   public final int direction()
   {
      return direction;
   }
   
   public final int street()
   {
      return loc[3] - loc[1];
   }
   
   public final int avenue()
   {
      return loc[0] - loc[2];
   }
   
   public final boolean running()
   {
      return state;
   }
   
   private static synchronized int incrementRobots()
   {
      return numberOfRobots++;
   }
}
// Author: Brian
public class Lab04
{
   public static void main(String[]arg)
   {
      
      // Setup the map
      World.readWorld("tower1");
      World.setSize(10, 10);
      World.setSpeed(10);
      
      // Create an instance of a RoboRunner
      RoboRunner karel = new RoboRunner();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnRight();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.turnLeft();
      karel.run();
      karel.pickBeeper();
   }
}
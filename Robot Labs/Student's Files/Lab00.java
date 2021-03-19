// Author: Brian
public class Lab00
{
   public static void main(String[]arg)
   { 
   
      // Setup the world
      World.readWorld("begin1");
      World.setSize(10, 10);
      World.setSpeed(7);
   
      // Create instance of Robot
      Robot karel = new Robot();
   
      // karel commands 
      karel.move();
      karel.move();
      karel.move();
      karel.move();
      karel.turnLeft();
      karel.move();
      karel.move();
      karel.move();
      karel.turnLeft();
      karel.move();
      karel.move();
      karel.move();
      karel.pickBeeper();
      
      // Output on completion 
      System.out.println("Finished");
   }
}
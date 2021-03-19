// Author: Brian
public class Lab05
{
   public static void main(String[]arg)
   {
      
      // Setup the map
      World.readWorld("bigmaze1");
      World.setSize(17, 16);
      World.setSpeed(20);
      
      // Create an instance of a RoboRunner
      RoboRunner karel = new RoboRunner();
	  
	  // Complete the maze
      karel.completeMaze();
   }
}
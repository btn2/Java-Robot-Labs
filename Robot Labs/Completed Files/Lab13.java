// Author: Brian
public class Lab13
{
   public static void main(String[]arg)
   {
      
      // Setup the map
      World.readWorld("bigmaze1");
      World.setSize(17, 16);
      World.setSpeed(10);
      
      // Create an instance of a BackTracker
      BackTracker karel = new BackTracker();
      karel.completeMaze();
      karel.backtrack();
   }
}
// Author: Brian
import java.util.*;
public class Lab11
{
   public static void main(String[]arg)
   {
      Scanner input = new Scanner(System.in);
      
      // Setup the map
      
      System.out.println("Enter the world");
      String world = input.nextLine();
      
      System.out.println("Enter the avenue size");
      int xSize = input.nextInt(); 
      
      System.out.println("Enter the street size");
      int ySize = input.nextInt();
      World.setSize(xSize, ySize);
      World.setSpeed(10);
      World.readWorld(world);
      
      System.out.println("Enter the # of RoboRunners to create");
      int n = input.nextInt();
      RoboRunner[] robots = new RoboRunner[n];
      for (int i = 0 ; i < n ; i++){
         System.out.println("Enter the avenue position of RoboRunner " + i+1);
         int x = input.nextInt();
         System.out.println("Enter the street position of RoboRunner " + i+1);
         int y = input.nextInt();
         robots[i] = new RoboRunner(x, y, World.EAST, 0);
      }
      for (int i = 0 ; i < n ; i++){
         robots[i].completeMaze();
      }  
      for (int i = 0 ; i < n ; i++){
         System.out.println("Robot " + i+1 + " took " + robots[i].getCount() + " moves");
      }
      
   }
}
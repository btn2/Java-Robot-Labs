// Author: Brian
import java.util.*;
public class Lab14
{
   public static void main(String[]arg)
   {
      Scanner input = new Scanner(System.in);
      
      // Setup the map
      
      System.out.println("Enter the world");
      String world = input.nextLine();
      
      System.out.println("Enter the avenue size");
      int xSize = getInt("Invalid input! Enter the avenue size");   
      
      System.out.println("Enter the street size");
      int ySize = getInt("Invalid input! Enter the street size");      
      World.setSize(xSize, ySize);
      World.setSpeed(10);
      World.readWorld(world);
      
	  // Setup the RoboRunners
      System.out.println("Enter the # of RoboRunners to create");
      int n = getInt("Invalid input! Enter the # of RoboRunners to create");
      RoboRunner[] robots = new RoboRunner[n];
      for (int i = 0 ; i < n ; i++){
         System.out.println("Enter the avenue position of RoboRunner " + i+1);
         int x = getInt("Invalid input! Enter the avenue position of RoboRunner " + i+1);
         System.out.println("Enter the street position of RoboRunner " + i+1);
         int y = getInt("Invalid input! Enter the street position of RoboRunner " + i+1);
         robots[i] = new RoboRunner(x, y, World.EAST, 0);
      }
      for (int i = 0 ; i < n ; i++){
         robots[i].completeMaze();
      }  
      for (int i = 0 ; i < n ; i++){
         System.out.println("Robot " + i+1 + " took " + robots[i].getCount() + " moves");
      }
      
   }
   
   // Receive the integer input via try-catch
   public static int getInt(String err){
      Scanner input = new Scanner(System.in);
      int out = 0;
      boolean pass = false;
      while(!pass){
         try{
            out = input.nextInt();
            pass = true;
         } catch(InputMismatchException e){
            System.out.println(err);
            input.nextLine();
         }
      }
      return out;
   }
}
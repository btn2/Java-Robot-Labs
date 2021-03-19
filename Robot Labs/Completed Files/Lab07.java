// Author: Brian
import java.util.*;
public class Lab07
{
   public static void main(String[]arg)
   {
      
      // Setup the map
      World.readWorld("empty1");
      World.setSize(10, 10);
      World.setSpeed(10);
      
      // Create an instance of an Athlete
      Athlete karel = new Athlete(1,1,World.EAST,World.INFINITY);
      Scanner input = new Scanner(System.in);
	  
      System.out.println("How many beepers would you like to drop?");
      int n = input.nextInt();
      for (int i = 0 ; i < 8 ; i++){
         karel.move();
         for (int j = 0 ; j < n ; j++){
            karel.putBeeper();
         }
      }
   }
}
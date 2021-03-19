// Author: Brian
import java.util.*;
public class Lab09
{
   public static void main(String[]arg)
   {
      Scanner input = new Scanner(System.in);
      
      // Setup the map
      System.out.println("What world would you like to load in?");
      World.readWorld(input.nextLine());
      World.disableLogging();
      World.setSize(10, 10);
      World.setSpeed(10);
      
      int total = 0;        
      for (int i = 0 ; i < 10 ; i++){
         total += execute(new Tracker(1,1+i,World.EAST,0));
      }   
      
      System.out.println("There are a total of " + total + " beepers on the map.");
   }
   
   // Run the method on the Tracker object
   public static int execute(Tracker tracker){
      while(tracker.frontIsClear()){
         tracker.move();
      }  
      tracker.turnAround();
      tracker.run(8);
      return tracker.getCount();
   }
}
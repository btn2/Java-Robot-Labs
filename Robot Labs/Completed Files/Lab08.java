// Author: Brian
import java.util.*;
public class Lab08
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
      
      // Create an instance of an Athlete
      Athlete karel = new Athlete();
      
      String in = "";
      System.out.println("What would you like to do?");
	  
	  // Continue to parse inputs until user inputs "stop"
      while(!(in = input.nextLine().toLowerCase()).equals("stop")){
         System.out.println("What would you like to do?");
         if (in.toLowerCase().equals("turn left")){
            karel.turnLeft();
         }
         else if (in.toLowerCase().equals("turn right")){
            karel.turnRight();
         }
         else if (in.toLowerCase().equals("move")){
            karel.move();
         }
         else if (in.toLowerCase().equals("turn around")){
            karel.turnAround();
         }
         else if(in.toLowerCase().equals("pick beeper")){
            karel.pickBeeper();
         }
         else if(in.toLowerCase().equals("put beeper")){
            karel.putBeeper();
         }
         else{
            System.out.println("Didn't understand that, try again!");
         }
         
      }
      System.exit(0);
   }
}
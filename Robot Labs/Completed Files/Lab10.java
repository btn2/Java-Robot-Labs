// Author: Brian
import java.util.*;
public class Lab10
{
   public static void main(String[]arg)
   {
      Scanner input = new Scanner(System.in);
      
      // Setup the map
      System.out.println("What world would you like to load in?");
      World.readWorld(input.nextLine());
      World.disableLogging();
      World.setSize(15, 15);
      World.setSpeed(30);
      
	  // Create the Detector robot
      Detector karel = new Detector();
      
      int direction = getDirection(karel);
      while(direction > 0){ // 1 is left, 2 is front, 3 is right
         if (direction == 1){
            karel.turnLeft();
            karel.move();
         } else if (direction == 2){
            karel.move();
         } else if (direction == 3){
            karel.turnRight();
            karel.move();
         }
         direction = getDirection(karel);
      }
      System.out.println(karel.getCount());
   }
   
   // Get the direction of the path
   public static int getDirection(Detector karel){ // 1 is left, 2 is front, 3 is right
      if (karel.frontIsClear() && karel.frontHasBeeper()){
         return 2;
      } else if (karel.leftIsClear() && karel.leftHasBeeper()){
         return 1;
      } else if(karel.rightIsClear() && karel.rightHasBeeper()){
         return 3;
      } 
      return 0;
      
   }
}
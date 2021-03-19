// Author: Brian
import java.util.*;
public class Lab12
{
   public static void main(String[]arg)
   {
      Scanner input = new Scanner(System.in);
      
      // Setup the map
      System.out.println("What world would you like to load in?");
      World.readWorld(input.nextLine());
      World.disableLogging();
      World.setSize(15, 15);
      World.setSpeed(10);
      
      Detector karel = new Detector();
      
      boolean[][] map = new boolean[15][15];
      map[0][0] = true;
      
      int x = 0;
      int y = 0;
      int compass = 0; // 0 is right, 1 is up, 2 is left, 3 is down
      
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
         x = karel.avenue()-1;
         y = karel.street()-1;
         map[x][y] = true;
         direction = getDirection(karel);
      }
      for (int i = map.length-1 ; i >= 0 ; i--){
         for (int j = 0 ; j < map[0].length ; j++){
            System.out.print(map[j][i]?" O ":" . ");
         }
         System.out.println();
      }
   }
      
   // Get the appropriate direction to turn
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
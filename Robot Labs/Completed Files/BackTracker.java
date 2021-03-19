// Author: Brian
import java.util.*;
public class BackTracker extends RoboRunner
{
   private ArrayList<Integer> steps; // 0 is left, 1 is move, 2 is right
   public BackTracker(){
      super(1, 1, World.EAST, 0);
      steps = new ArrayList<Integer>();
   }
   
   public BackTracker(int avenue, int street, int direction, int beepers){
      super(avenue, street, direction, beepers);
      steps = new ArrayList<Integer>();
   }

   // Change to keep track of steps
   @Override   
   public void completeMaze(){
      while(!onABeeper()){
         if (frontIsClear()){
            move();
            steps.add(1);
         }
         while (rightIsClear()){
            turnRight();
            move();
            steps.add(2);
            steps.add(1);
         }
         if (!frontIsClear() && !rightIsClear()){
            turnLeft();
            steps.add(0);
         }
      }
   }
   
   // Retrace steps
   public void backtrack(){
      turnAround();
      for (int i = steps.size() - 1 ; i >= 0 ; i--){
         if (steps.get(i) == 0){
            turnRight();
         } else if (steps.get(i) == 1){
            move();
         }
         else{ // steps.get(i) == 2
            turnLeft();
         }
      }
   }
}

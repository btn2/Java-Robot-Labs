// Author: Brian
public class RoboRunner extends Runner
{
   private int count;
   private int left;
   private int right;
   public RoboRunner(){
      super(1, 1, World.EAST, 0);
      count = 0;
   }
   
   public RoboRunner(int avenue, int street, int direction, int beepers){
      super(avenue, street, direction, beepers);
      count = 0;
   }

   @Override
   public void run()
   {
      while(frontIsClear()){
         move();
         count++;
      }
   }
   
   // Algorithm to complete the maze, stop when beeper is detected
   public void completeMaze(){
      while(!onABeeper()){
         if (frontIsClear()){
            move();
            count++;
         }
         while (rightIsClear()){
            turnRight();
            move();
            count++;
         }
         if (!frontIsClear() && !rightIsClear()){
            turnLeft();
         }
      }
   }
   
   public int getCount(){
      return count;
   }
}

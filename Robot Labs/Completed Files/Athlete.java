// Author: Brian
public class Athlete extends Robot{
   public Athlete(){
      super(1, 1, World.EAST, 0);
   }
   
   public Athlete(int avenue, int street, int direction, int beepers){
      super(avenue, street, direction, beepers);
   }
   
   public void turnRight(){
      turnLeft();
      turnLeft();
      turnLeft();
   }
   
   public void turnAround(){
      turnLeft();
      turnLeft();
   }
}
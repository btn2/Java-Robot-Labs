// Author: Brian
public class Detector extends Runner{
   private int count;
   
   public Detector(int a, int s, int d, int b){
      super(a, s, d, b);
      count = 1;
   }
   
   public Detector(){
      super(1, 1, World.EAST, 0);
      count = 1;
   }
   
   public void move(){
      super.move();
      count++;
   }
   
   // 3 Methods below check for beeper in that direction
   
   public boolean leftHasBeeper(){
      turnLeft();
      super.move();
      boolean out = onABeeper();
      turnAround();
      super.move();
      turnLeft();
      return out;
   }
   
   public boolean rightHasBeeper(){
      turnRight();
      super.move();
      boolean out = onABeeper();
      turnAround();
      super.move();
      turnRight();
      return out;
   }
   
   public boolean frontHasBeeper(){
      super.move();
      boolean out = onABeeper();
      turnAround();
      super.move();
      turnAround();
      return out;
   }
   
   public int getCount(){
      return count;
   }
      
   public void setCount(int x){
      count = x;
   }
}
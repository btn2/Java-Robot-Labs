// Author: Brian
public class Runner extends Athlete{
   public Runner(){
      super(1, 1, World.EAST, 0);
   }
   
   public Runner(int avenue, int street, int direction, int beepers){
      super(avenue, street, direction, beepers);
   }

   // Moves x times
   public void run(int x){
      for (int i = 0 ; i < x ; i++){
         move();
      }
   }
}
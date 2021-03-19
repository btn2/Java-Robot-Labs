// Author: Brian
public class Lab06
{
   public static void main(String[]arg)
   {
      
      // Setup the map
      World.readWorld("cleaner1");
      World.setSize(10, 10);
      World.setSpeed(20);
      
      // Loop 10 times 
      for (int i = 0 ; i < 10 ; i++){
         clean(new Runner(1,1+i,World.EAST,0)); // Pass Runner objects directly into the clean() method

      }   
   }
   
   public static void clean(Runner runner){
      while(runner.frontIsClear()){
         while(runner.onABeeper()){
            runner.pickBeeper();
         }
         runner.move();
      }  
      while(runner.onABeeper()){
         runner.pickBeeper();
      }
   
      runner.turnAround();
      runner.run(8);
      while(runner.hasBeepers()){
         runner.putBeeper();
      }
   }
}
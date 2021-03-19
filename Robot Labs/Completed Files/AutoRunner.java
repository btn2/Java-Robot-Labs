// Author: Brian
public class AutoRunner extends RoboRunner implements Runnable{
   private int num;
   
   public AutoRunner(int avenue, int street, int direction, int beepers, int roboNumber){
      super(avenue, street, direction, beepers);
      num = roboNumber;
   }
   
   // Abstract method from Runnable interface, also overrides the RoboRunner's run() method
   @Override
   public void run(){
      completeMaze();
      System.out.println("Robot " + num + " took " + getCount() + " moves");
   }

}


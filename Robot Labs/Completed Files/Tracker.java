// Author: Brian
public class Tracker extends Runner{
   private int count;
   private int street;
   private int avenue;
   
   public Tracker(int a, int s, int d, int b){
      super(a, s, d, b);
      count = 0;
      street = s;
      avenue = a;
   }
   
   // Looks to see if there are beepers to add to its count
   public void move(){
      super.move();
      avenue++;
      int temp = 0;
      while(onABeeper()){
         pickBeeper();
         temp++;
      }   
      count += temp;
      while(hasBeepers()){
         putBeeper();
      }
      if (temp > 0){
         System.out.println("There " + (temp == 1 ? "is 1 beeper" : "are " + temp + " beepers") + " at avenue " + avenue + ", street " + street);
      }
   }
   
   public void run(int x){
      for (int i = 0 ; i < x ; i++){
         super.move();
      }
   }
   
   public int getCount(){
      return count;
   }
   
   public int getStreet(){
      return street;
   }
   
   public int getAvenue(){
      return avenue;
   }
   
   public void setCount(int x){
      count = x;
   }
}
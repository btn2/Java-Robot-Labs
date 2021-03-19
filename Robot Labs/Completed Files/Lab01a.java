// Author: Brian
public class Lab01a
{
 public static void main(String[]arg)
 {
    
    // Setup the map
    World.readWorld("beginner");
    World.setSize(10, 10);
    World.setSpeed(7);
    
    // Create an instance of a Robot
    Robot karel = new Robot();
    karel.move();
    karel.move();
    karel.move();
    karel.pickBeeper();
    karel.turnLeft();
    karel.move();
    karel.move();
    karel.move();
    karel.move();
    karel.pickBeeper();
    karel.turnLeft();
    karel.turnLeft();
    karel.turnLeft();
    karel.move();
    karel.move();
    karel.move();
    karel.putBeeper();
    karel.putBeeper();

 }
}
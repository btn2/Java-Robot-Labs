// Author: Brian
public class Lab01b
{
 public static void main(String[]arg)
 {
    
    // Setup the map
    World.readWorld("beginner");
    World.setSize(10, 10);
    World.setSpeed(7);
    
    // Create an instance of a Robot using the 4-arg constructor
    Robot karel = new Robot(7,5,World.WEST,0);
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
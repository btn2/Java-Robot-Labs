// Author: Brian
public class Lab02
{
 public static void main(String[]arg)
 {
    
    // Setup the map
    World.readWorld("maze1");
    World.setSize(10, 10);
    World.setSpeed(4);

    // Create an instance of an Athlete
    Athlete karel = new Athlete();
    karel.move();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.turnRight();
    karel.move();
    karel.turnRight();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.move();
    karel.turnRight();
    karel.move();
    karel.turnRight();
    karel.move();
    karel.move();
    karel.turnLeft();
    karel.move();
    karel.turnRight();
    karel.move();
    karel.move();
    karel.pickBeeper();

 }
}
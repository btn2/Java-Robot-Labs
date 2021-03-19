// Author: Brian
public class Lab03
{
 public static void main(String[]arg)
 {
    
    // Setup the map
    World.readWorld("micromouse1");
    World.setSize(10, 10);
    World.setSpeed(3);
    
    // Create an instance of a Runner
    Runner karel = new Runner();
    karel.run(7);
    karel.turnLeft();
    karel.run(1);
    karel.turnRight();
    karel.run(1);
    karel.turnLeft();
    karel.run(7);
    karel.turnLeft();
    karel.run(2);
    karel.turnLeft();
    karel.run(1);
    karel.turnRight();
    karel.run(2);
    karel.turnRight();
    karel.run(2);
    karel.pickBeeper();
 }
}
/* Name: Alex Hale
 * ID: 260672475
 * Date: 18/3/2016 */

import java.util.Arrays;
import java.awt.Color;
import becker.robots.*;

public class RobotsMoveLights {
  final static int LIGHT_STREET = 1;
  final static int LIGHT_AVENUE = 1;
  final static int CITY_SIZE = 10;
  
  public static void getLoot(Robot robot) {
    Intersection initialPoint = robot.getIntersection();   // store the robot's initial position
    
    // create a while loop that will run while the robot is still searching
    // exit only when the robot has reached its initial position AND has found all three flashers
    while (robot.countThingsInBackpack() < 3 || robot.getIntersection() != initialPoint) {
      // turn 180 degrees to set up for the checking of which direction to go next
      robot.turnLeft();
      robot.turnLeft();
      
      boolean aligned = false; // create a variable to store whether the correct direction has been found
      
      // FIRST, turn left once to check if right is clear. If so, exit loop. If not, loop through again.
      // SECOND, turn left once to check if front is clear. If so, exit loop. If not, loop through again.
      // THIRD, turn left once to check if left is clear. If so, exit loop. If not, loop through again.
      // FOURTH, we know that only the rear is clear. Turn left once to face that direction.
      while (!aligned) {
        robot.turnLeft();
        aligned = robot.frontIsClear();
      }
      
      // move one space forward in the direction we chose above
      robot.move();
      
      // if less than three flashers have been picked up so far, enter this section
      if (robot.countThingsInBackpack() < 3) {
        // if there's a flasher on the given intersection, pick it up
        if (robot.canPickThing()) {
          robot.pickThing();
        }    
        // if 3 flashers have been picked up, change colour
        if (robot.countThingsInBackpack() == 3) {
          robot.setColor(Color.MAGENTA);
        }
      }
    }
  }
  
  public static void main(String[] args) {
    MazeCity montreal = new MazeCity(CITY_SIZE, CITY_SIZE);
    
    Robot asimo = new Robot(montreal, LIGHT_STREET, LIGHT_AVENUE - 1, Direction.EAST);
    
    new Flasher(montreal, 8, 8,    true);
    new Flasher(montreal, 3, 4,    true);
    new Flasher(montreal, 2, 6,    true);
    
    getLoot(asimo);  
  }
  
}

import java.util.*;

/*
 * The area in which pathfinding will occur
 * 
 * +--------------------------+
 * |                . <- current pos
 * |  x <-end                 |
 * |                          | length
 * |           # <-obstacle   |
 * |                          |
 * |         o <-start        |
 * +--------------------------+ 
 *           width
 */

public class Area {

    // ----------------------------------------AREA-ATTRIBUTES:---------------------------------------
    int length, width, area;
    private ArrayList<Object> obstacleList = new ArrayList<Object>();
    private Object start, end;

    // -----------------------------------------CONSTRUCTORS:-----------------------------------------

    Area() {
    }

    Area(int length, int width, Object start, Object end) {
        this.length = length;
        this.width = width;
        area = length * width;
        this.start = start;
        this.end = end;
    }

    // --------------------------------------GETTERS-AND-SETTERS:-------------------------------------
    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
    }

    public void setEnd(Object end) {
        this.end = end;
    }

    public int getObstacleListLength() {
        return obstacleList.size();
    }

    // -------------------------METHODS-TO-MODIFY-THE-LIST-OF-OBSTACLES:-------------------------

    // ADD AN OBSTACLE TO THE OBSTACLE LIST:
    public void addObstacle(Object obj) {

        // checking if object is already in obstacle list:
        boolean valid = true;
        for (int i = 0; i < obstacleList.size(); i++) {
            if (obj.cmp(start) || obj.cmp(end) || obj.cmp(obstacleList.get(i))) {
                valid = false;
            }
        }

        if (valid) { // if object is not in obstacle list, add it
            obstacleList.add(obj);
            System.out.println("Obstacle added successfully!");
        } else { // if object is already in the obstacle list:
            System.out.println("Unable to add Obstacle, that location may already be occupied");
        }
    }

    // REMOVE AN OBSTACLE FROM THE OBSTACLE LIST:
    public void removeObstacle(int loc) {
        obstacleList.remove(loc - 1);
    }

    // EDIT AN OBSTACLE FROM THE OBSTACLE LIST:
    public void editObstacle(int loc, Object obj) {
        obstacleList.set(loc - 1, obj);
    }

    // PRINT THE LIST OF OBSTACLES:
    public void printObstacleList() {
        for (int i = 1; i <= obstacleList.size(); i++) {
            System.out.print("Obstacle number " + i + ": ");
            System.out.println(obstacleList.get(i - 1));
            System.out.println();
        }
    }

    // --------------------------METHODS-TO-PRINT-OUT-AREA-ILLUSTRATION:-------------------------

    // PRINT OUT A LABELED EXAMPLE AREA:
    public void printExample() {
        System.out.println(" The area in which pathfinding will occur:");
        System.out.println();
        System.out.println(" +--------------------------+");
        System.out.println(" |                . <- current pos");
        System.out.println(" |  x <-end                 | ");
        System.out.println(" |                          | length");
        System.out.println(" |           # <-obstacle   |");
        System.out.println(" |                          |");
        System.out.println(" |         o <-start        |");
        System.out.println(" +--------------------------+ ");
        System.out.println("           width");
    }

    // PRINT OUT AN ILLUSTRATION OF THE CURRENT AREA:
    public void illustrate(int currentX, int currentY) {
        // ILLUSTRATING THE AREA
        for (int a = -1; a <= length; a++) {

            // print left corners:
            if (a == -1 || a == length)
                System.out.print("+");

            // print left sides:
            else
                System.out.print("|");

            for (int b = 0; b <= width; b++) {

                boolean space = true; // if a space should be added (no other special char belongs at this location)

                // print right corners:
                if ((a == -1 || a == length) && (b == width)) {
                    System.out.println("+");
                    space = false;
                }

                // print right side:
                else if (!(a == -1 || a == length) && (b == width)) {
                    System.out.println("|");
                    space = false;
                }

                // print top and bottom sides:
                else if (a == -1 || a == length) {
                    System.out.print("-");
                    space = false;
                }

                // print start:
                else if (a == start.getY() && b == start.getX()) {
                    System.out.print("o");
                    space = false;
                }

                // print end:
                else if (a == end.getY() && b == end.getX()) {
                    System.out.print("x");
                    space = false;
                }

                // if an obstacle is in this location, print obstacle:
                for (int i = 0; i < obstacleList.size(); i++) {
                    if (a == obstacleList.get(i).getY() && b == obstacleList.get(i).getX()) {
                        System.out.print("#");
                        space = false;
                    }
                }

                // print current position:
                if ((a == currentY && b == currentX) && space) {
                    System.out.print(".");
                    space = false;
                }

                // if location is empty, print space:
                if (space)
                    System.out.print(" ");
            }
        }
    }

    // --------------------------------------OTHER-METHODS:--------------------------------------

    // METHOD TO RETURN LIST OF VALID MOVES FROM A GIVEN POSITION:
    public ArrayList<Character> lookAround(Object currentPos) {

        // create a list to be returned:
        ArrayList<Character> moveable = new ArrayList<Character>();

        // NOTE: NESW refers to north, east, south & west
        // cardinal directions will always be checked in this order

        // CHECK NESW FOR GOAL, IF GOAL IS FOUND RETURN:
        if (currentPos.getX() == end.getX() && currentPos.getY() - 1 == end.getY()) {
            moveable.add('N');
            return moveable;
        } else if (currentPos.getX() + 1 == end.getX() && currentPos.getY() == end.getY()) {
            moveable.add('E');
            return moveable;
        } else if (currentPos.getX() == end.getX() && currentPos.getY() + 1 == end.getY()) {
            moveable.add('S');
            return moveable;
        } else if (currentPos.getX() - 1 == end.getX() && currentPos.getY() == end.getY()) {
            moveable.add('W');
            return moveable;
        }

        // check NESW for area limits and obstacles:
        boolean Npass = true, Epass = true, Spass = true, Wpass = true;
        if (currentPos.getY() == 0) {
            Npass = false;
        }
        if (currentPos.getX() == width - 1) {
            Epass = false;
        }
        if (currentPos.getY() == length - 1) {
            Spass = false;
        }
        if (currentPos.getX() == 0) {
            Wpass = false;
        }

        // check NESW for obstacles:
        for (int i = 0; i < obstacleList.size(); i++) {
            if (currentPos.getX() == obstacleList.get(i).getX()
                    && currentPos.getY() - 1 == obstacleList.get(i).getY()) {
                Npass = false;
            } else if (currentPos.getX() + 1 == obstacleList.get(i).getX()
                    && currentPos.getY() == obstacleList.get(i).getY()) {
                Epass = false;
            } else if (currentPos.getX() == obstacleList.get(i).getX()
                    && currentPos.getY() + 1 == obstacleList.get(i).getY()) {
                Spass = false;
            } else if (currentPos.getX() - 1 == obstacleList.get(i).getX()
                    && currentPos.getY() == obstacleList.get(i).getY()) {
                Wpass = false;
            }
        }

        // ADD VALID MOVES TO LIST:
        if (Npass)
            moveable.add('N');
        if (Epass)
            moveable.add('E');
        if (Spass)
            moveable.add('S');
        if (Wpass)
            moveable.add('W');

        // RETURN LIST:
        return moveable;
    }

    // METHOD TO CHECK IF AT GOAL:
    boolean atGoal(Object loc) {

        if (loc.cmp(end))
            // if location given is the same location as the end, return true:
            return true;
        else
            // else return false:
            return false;
    }
}
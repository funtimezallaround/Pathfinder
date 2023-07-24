import java.util.*;

public class Brain {

    // variables for pathfinding:
    private Object currentPos = new Object(); // the current pos that AIden is at
    ArrayList<String> path = new ArrayList<String>(); // the path taken from start to end
    String move; // the last move taken (UP, DOWN, LEFT, RIGHT)
    int maxF; // the largest possible f value
    boolean atGoal; // at goal or not

    // INITIALISING START AND END:
    Object start = new Object();
    Object end = new Object();

    // RANDOM NUMBER GENERATOR:
    public int random(int range) {
        // generate a random number between 0 and a given range:
        return (int) Math.round(Math.random() * range);
    }

    // SEARCH FOR THE END IN A GIVEN AREA:
    public void search(Area area) {

        // set the start and move AIden there:
        start.equals(area.getStart());
        currentPos.equals(start);

        // initialise vars:
        move = "";
        atGoal = false;
        maxF = area.length * area.width;
        ArrayList<Character> moveable = new ArrayList<Character>();

        // begin searching:
        do {
            // get dierctions that AIden can move to (ie not blocked off)
            moveable = area.lookAround(currentPos);

            // heuristic implementation:
            // choose a valid direction at random and move 1 step in that direction
            // repeat until goal is reached

            // choose random direction:
            switch (moveable.get(this.random(moveable.size() - 1))) {
                case 'N':
                    move = "UP";
                    break;
                case 'E':
                    move = "RIGHT";
                    break;
                case 'S':
                    move = "DOWN";
                    break;
                case 'W':
                    move = "LEFT";
                    break;
            }

            // move the current pos & add move to the path taken:
            currentPos.move(move);
            path.add(move);

            // check if at goal:
            atGoal = area.atGoal(currentPos);

            // print out the area with the updated current pos:
            area.illustrate(currentPos.getX(), currentPos.getY());

        } while (!atGoal); // loop until at goal

        // give feedback to user:
        System.out.println("Found a path from the start to the end.");
    }

    // FIND THE END BY FOLLOWING THE PATH TAKEN BY THE SEARCH METHOD:
    private void findEnd() {
        end.equals(start);
        for (String s : path) {
            end.move(s);
        }
    }

    // METHOD TO FIND AN OPTIMAL PATH FROM START TO END:
    public void FindBetterPath(Area area) {

        // give user feedback on current job being processed:
        System.out.println("Optimising path...");

        // CREATE NECESSARY VARS:

        // open and closed list for pathfinding, & moveable list of possible moves that
        // can be made:
        ArrayList<Object> openList = new ArrayList<Object>();
        ArrayList<Object> closedList = new ArrayList<Object>();
        ArrayList<Character> moveable = new ArrayList<Character>();

        // INITIALISATION REQUIRED FOR PATHFINDING:
        atGoal = false;
        this.findEnd();
        currentPos.equals(start);
        openList.add(start);

        // IF START AND END ARE =, RETURN
        if (end.cmp(start)) {
            System.out.println("Already at the end");
            return;
        }

        // BEGIN PATHFINDING:
        do {
            // get list of valid moves:
            moveable = area.lookAround(currentPos);

            // create object to add to open list:
            Object obj = new Object();

            // ADD NEW OBJECT TO OPEN LIST FOR EVERY VALID MOVE AVAILABLE:
            for (char c : moveable) {
                switch (c) {
                    case 'N':
                        obj = new Object(currentPos.getX(), currentPos.getY() - 1);
                        break;
                    case 'E':
                        obj = new Object(currentPos.getX() + 1, currentPos.getY());
                        break;
                    case 'S':
                        obj = new Object(currentPos.getX(), currentPos.getY() + 1);
                        break;
                    case 'W':
                        obj = new Object(currentPos.getX() - 1, currentPos.getY());
                        break;
                }

                // check if the new object created is in the closed list:
                boolean valid = true;
                for (Object object : closedList) {
                    if (obj.cmp(object)) {
                        // if the new object created is indeed in the closed list, it has already been
                        // considered, and can be ignored this time around
                        valid = false;
                    }
                }

                // if the new object created is not in the closed list:
                // evaluate it and add it to the open list
                if (valid) {
                    eval(obj, currentPos);
                    openList.add(obj);
                }
            }

            // IF THE CURRENT POSITION IS IN THE OPEN LIST, REMOVE IT:
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).cmp(currentPos)) {
                    openList.remove(i);
                    i--;
                }
            }

            // FIND THE LOWEST f COST AND ITS LOCATION IN THE LIST:

            // initialisation of necessary vars:
            int lowestFloc = 0;
            int lowestF = maxF;

            // finding the lowest f cost
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f < lowestF) {

                    // if f cost is lower than the current lowest f cost, update the lowest f cost:
                    lowestF = openList.get(i).f;
                    // and also update the location of the lowest f cost:
                    lowestFloc = i;

                } else if (openList.get(i).f == lowestF && openList.get(i).g < openList.get(lowestFloc).g) {
                    // if the f cost is the same, but the g cost is lower, update the location of
                    // the lowest f cost
                    lowestFloc = i;
                }
            }

            // CHECK IF AT GOAL
            atGoal = area.atGoal(currentPos);

            if (atGoal) {
                // if at goal, set the end parent to the current parent:
                end.parent = new Object();
                end.parent.equals(currentPos.parent);
            } else {
                // if not at goal:
                // 1. consider the location with the next best f cost
                currentPos = openList.get(lowestFloc);

                // 2. add this location to the closed list, and remove it from the open list
                closedList.add(currentPos);
                openList.remove(lowestFloc);
            }

        } while (!atGoal); // loop until at goal

        // ONCE OPTIMAL PATH IS FOUND, TRACE AND PRINT IT:
        path = this.tracePath();
        System.out.println("Optimised path:");
        System.out.println(path);

    }

    // EVALUATE OBJECT:
    private void eval(Object obj, Object currentPos) {

        // create and set object's parent:
        obj.parent = new Object();
        obj.parent.equals(currentPos);

        // set object's g, f and h cost:
        obj.g = Math.abs(obj.getX() - start.getX()) + Math.abs(obj.getY() - start.getY());
        obj.h = Math.abs(obj.getX() - end.getX()) + Math.abs(obj.getY() - end.getY());
        obj.f = obj.g + obj.h;

        // g cost is the distance from the start
        // h cost is the distance from the end
        // f cost is the total cost
    }

    // TRACE THE PATH FROM THE START TO THE END:
    private ArrayList<String> tracePath() {

        // creation of necessary lists:
        ArrayList<String> tempPath = new ArrayList<String>(); // temp path will hold the reverse of the optimal
        ArrayList<String> path = new ArrayList<String>(); // path will hold the optimal path
        ArrayList<Object> reversePath = new ArrayList<Object>(); // reverse path will hold the locations in the optimal
                                                                 // path

        // move the current posistion to the end:
        currentPos.equals(end);

        // TRACE THE PATH FROM THE END TO THE START:
        do {
            // necessary initialisations:
            reversePath.add(currentPos);
            String direction = "";

            // set the direction based on the current position from its parent:
            if (currentPos.parent.getX() == currentPos.getX()
                    && currentPos.parent.getY() == currentPos.getY() - 1) {
                direction = "DOWN";
            } else if (currentPos.parent.getX() == currentPos.getX() + 1
                    && currentPos.parent.getY() == currentPos.getY()) {
                direction = "LEFT";
            } else if (currentPos.parent.getX() == currentPos.getX()
                    && currentPos.parent.getY() == currentPos.getY() + 1) {
                direction = "UP";
            } else if (currentPos.parent.getX() == currentPos.getX() - 1
                    && currentPos.parent.getY() == currentPos.getY()) {
                direction = "RIGHT";
            }

            // add the direction to a temporary list & move current position to the parent:
            tempPath.add(direction);
            currentPos.equals(currentPos.parent);

        } while (currentPos.cmp(start) == false); // loop until at start

        // fill in the path list with the path taken in the correct order:
        for (int i = tempPath.size() - 1; i >= 0; i--) {
            path.add(tempPath.get(i));
        }

        return path;
    }
}
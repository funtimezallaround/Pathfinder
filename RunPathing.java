import java.util.*;

public class RunPathing {
    static Scanner s = new Scanner(System.in);
    static Area area = new Area();

    public static void main(String[] args) {
        System.out.println("Welcome to the Pathfinding Project");

        // INITIALISATION OF THE AREA:

        // necessary variables:
        int length = 0, width = 0;
        boolean accept = true;

        // GETTING LENGTH AND WIDTH OF AREA:
        do {
            do {
                accept = true;
                try {
                    System.out.println("Please Enter the Essential Parameters:");
                    System.out.print("Length of Area: ");
                    length = s.nextInt();
                    System.out.print("Width of Area: ");
                    width = s.nextInt();

                } catch (Exception ex) {
                    System.out.println("Exception occured: " + ex);
                    accept = false;
                    s.next();
                }
            } while (accept == false);
            boolean valid = true;
            do {
                valid = true;
                try {
                    area = new Area(length, width, new Object(-1, -1), new Object(-1, -1));
                    area.illustrate(-1, -1);

                    // ASKING USER IF THEY ARE SATISFIED WITH THE PARAMETERS GIVEN:
                    System.out.println("Continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int in = s.nextInt();
                    if (in == 2) {
                        accept = false;
                    } else if (in != 1 && in != 2) {
                        valid = false;
                    }

                } catch (Exception ex) {
                    System.out.println("Exception occured: " + ex);
                    valid = false;
                    s.next();
                }
            } while (valid == false);
        } while (accept == false);

        // GETTING COORDINATES OF THE START AND END POSITIONS
        do {
            accept = true;

            area.setStart(getObj("Start"));
            area.setEnd(getObj("End"));
            boolean valid = true;
            do {
                valid = true;
                try {
                    area.illustrate(-1, -1);

                    // ASKING USER IF THEY ARE SATISFIED WITH THE PARAMETERS GIVEN:
                    System.out.println("Continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int in = s.nextInt();
                    if (in == 2) {
                        accept = false;
                    } else if (in != 1 && in != 2) {
                        valid = false;
                    }

                } catch (Exception ex) {
                    System.out.println("Exception occured: " + ex);
                    valid = false;
                    s.next();
                }
            } while (valid == false);
        } while (accept == false);

        int menuOpt = -1;
        do {
            try {
                // PRINTING OUT MENU:
                System.out.println(" Obstacle Menu");
                System.out.println("---------------");
                System.out.println("*please keep in mind that a possible path from start to end MUST exist*");
                System.out.println("[0] Print labeled example area");
                System.out.println("[1] Add Obstacle");
                System.out.println("[2] Edit Obstacle");
                System.out.println("[3] Delete Obstacle");
                System.out.println("[4] Print Obstacle List");
                System.out.println("[5] Illustrate Area");
                System.out.println("[6] Begin Pathfinding");
                menuOpt = s.nextInt();
            } catch (Exception ex) {
                System.out.println("Exception occured: " + ex);
                s.next();
            }
            switch (menuOpt) {
                case 0:// Print labeled example area
                    System.out.println("Labeled example area:");
                    area.printExample();
                    System.out.println("\n");
                    break;
                case 1: // Add Obstacle:
                    area.addObstacle(getObj("Obstacle"));
                    break;

                case 2: // Edit Obstacle
                    if (area.getObstacleListLength() > 0) { // IF OBSTACLE LIST IS NOT EMPTY:
                        int loc = -1;
                        area.printObstacleList();
                        try {
                            // GET INDEX OF OBSTACLE TO BE EDITED:
                            System.out.print("Please enter the index of the Obstacle to be edited: ");
                            loc = s.nextInt();
                        } catch (Exception ex) {
                            System.out.println("Exception occured: " + ex);
                        }

                        if (loc <= 0 || loc > area.getObstacleListLength()) { // IF INDEX IS INVALID:
                            System.out.println("Location out of bounds");
                        } else { // IF INDEX IS VALID:
                            area.editObstacle(loc, getObj("new Obstacle"));

                        }
                    } else { // IF OBSTACLE LIST IS EMPTY:
                        System.out.println("No obstacles currently exist");
                    }
                    break;

                case 3: // Delete Obstacle
                    if (area.getObstacleListLength() > 0) { // IF OBSTACLE LIST IS NOT EMPTY:
                        int loc = -1;
                        area.printObstacleList();
                        try {
                            // GET INDEX OF OBSTACLE TO BE DELETED:
                            System.out.print("Please enter the index of the Obstacle to be deleted: ");
                            loc = s.nextInt();
                        } catch (Exception ex) {
                            System.out.println("Exception occured: " + ex);
                        }

                        if (loc <= 0 || loc > area.getObstacleListLength()) { // IF INDEX IS INVALID:
                            System.out.println("Location out of bounds");
                        } else { // IF INDEX IS VALID:
                            area.removeObstacle(loc);
                        }
                    } else { // IF OBSTACLE LIST IS EMPTY:
                        System.out.println("No obstacles currently exist");
                    }
                    break;

                case 4: // Print Obstacle List
                    area.printObstacleList();
                    break;

                case 5: // Illustrate Area
                    area.illustrate(-1, -1);
                    break;

                case 6: // Begin Pathfinding
                    System.out.println("Beginning Pathfinding...");
                    System.out.println("This may take a while");
                    break;

                default:
                    System.out.println("Sorry, that was not an option");
                    break;

            }
        } while (menuOpt != 6);

        Brain AIden = new Brain();
        // TELL AIDEN TO SEARCH FOR THE END AND ONCE ITS FOUND, TELL IT TO FIND THE BEST
        // PATH:
        AIden.search(area);
        AIden.FindBetterPath(area);

        s.close();
    }

    private static Object getObj(String objName) {
        // A METHOD FOR GETTING USER TO CREATE NEW OBJECT BY GIVING ITS X AND Y VALUES
        // THIS METHOD IS USED FOR START, END AND ANY OBSTACLES

        // INITIALISE VARS:
        int x = -1, y = -1;
        boolean valid = true;
        do {
            valid = true;
            try {
                // GET X VALUE FROM USER:
                System.out.println("Please enter the " + objName + " x and y positions in Area:");
                System.out.print("x: ");
                x = s.nextInt();

                if (x < 0 || x >= area.width) { // IF INVALID:
                    valid = false;
                    System.out.println("Invalid x position");
                }
            } catch (Exception ex) {
                // IF EXCEPTION OCCURS, VALUE PROVIDED IS INVALID:
                valid = false;
                System.out.println("Exception occured: " + ex);
                s.next();
            }
            // LOOP UNTIL VALID VALUE IS GIVEN
        } while (valid == false);

        do {
            valid = true;

            try {
                // GET Y VALUE FROM USER:
                System.out.println("Please enter the " + objName + " x and y positions in Area:");
                System.out.print("y: ");
                y = s.nextInt();

                if (y < 0 || y >= area.length) { // IF INVALID:
                    valid = false;
                    System.out.println("Invalid y position");
                }
            } catch (Exception ex) {
                // IF EXCEPTION OCCURS, VALUE PROVIDED IS INVALID:
                valid = false;
                System.out.println("Exception occured: " + ex);
                s.next();
            }
            // LOOP UNTIL VALID VALUE IS GIVEN
        } while (valid == false);

        // RETURN A NEW OBJECT WITH THE GIVEN X AND Y VALUES:
        return new Object(x, y);
    }
}

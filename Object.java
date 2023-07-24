public class Object implements Comparable<Object> {

    // OBJECT ATTRIBUTES:

    private int x_pos, y_pos; // x and y pos in area

    public int g, h, f; // g f and h are used to calculate the cost of a move for FindBetterPath
    public Object parent;// parent object is used to trace back the optimal path from the end to the start

    // CONSTRUCTORS:
    public Object() {
    }

    public Object(int x, int y) {
        x_pos = x;
        y_pos = y;
    }

    // GETTERS
    public int getX() {
        return x_pos;
    }

    public int getY() {
        return y_pos;
    }

    @Override
    public String toString() {

        return ("(" + x_pos + ", " + y_pos + ")");
    }

    // METHOD TO MOVE THE POSITION OF THIS OBJECT BY A FACTOR OF 1 IN ANY CARDINAL
    // DIRECTION
    public void move(String direction) {
        switch (direction) {
            case "UP":
                y_pos--;
                break;
            case "RIGHT":
                x_pos++;
                break;
            case "DOWN":
                y_pos++;
                break;
            case "LEFT":
                x_pos--;
                break;
        }
    }

    // METHOD TO COMPARE 2 OBJECTS AND RETURN TRUE OF FALSE
    public boolean cmp(Object obj) {
        if (this.getX() == obj.getX() && this.getY() == obj.getY()) {
            return true;
        }
        return false;
    }

    // METHOD TO EQUATE ONE OBJECT TO ANOTHER
    public void equals(Object obj) {
        try {
            this.x_pos = obj.getX();
            this.y_pos = obj.getY();
            this.parent = obj.parent;
        } catch (Exception ex) {
            // IN CASE EXCEPTION OCCURS DO NOTHING
            // EXCEPTION MAY OCCUR DUE TO parent BEING NULL
        }
    }

    // OVERRIDE THE compareTo METHOD TO BE COMPATIBLE WITH OBJECT CLASS
    @Override
    public int compareTo(Object other) {
        return this.f - other.f;
    }
}
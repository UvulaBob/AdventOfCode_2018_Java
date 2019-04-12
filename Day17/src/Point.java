import static java.lang.Math.abs;

class Point implements Comparable<Point>{
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Point p) {
        if ( x == p.x && y == p.y) {
            return 0;
        }

        if ((y < p.y) || (y == p.y && x < p.x)) {
            return -1;
        }
        return 1;
    }

    Point getBelowPoint () {
        return new Point(x, y + 1);
    }

    Point getBelowPointInDirection (ScanDirection direction) {
        int xModifier = -1;
        if (direction == ScanDirection.RIGHT) {
            xModifier = 1;
        }
        return new Point(x + xModifier, y + 1);
    }

    boolean isAdjacent(Point p){
        return ((x == p.x && (abs(y - p.y)) == 1) || (y == p.y && (abs(x - p.x)) == 1));
    }

    public String toString(){
        return x + "," + y;
    }

    Point copy() {
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            return (x == p.x && y == p.y);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}
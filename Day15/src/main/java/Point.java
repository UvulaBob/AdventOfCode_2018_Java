import java.util.ArrayList;

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

    boolean isAdjacent(Point p){
        return ((x == p.x && (abs(y - p.y)) == 1) || (y == p.y && (abs(x - p.x)) == 1));
    }

    public String toString(){
        return x + "," + y;
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
public class Star {
    String name;
    int a;
    int b;
    int c;
    int d;

    boolean isCloseTo(Star otherStar) {
        int diffA = Math.abs(this.a - otherStar.a);
        int diffB = Math.abs(this.b - otherStar.b);
        int diffC = Math.abs(this.c - otherStar.c);
        int diffD = Math.abs(this.d - otherStar.d);

        return diffA + diffB + diffC + diffD <= 3;

    }

    public String toString() {
        //return String.format("(%s,%s,%s,%s)", a, b, c, d);
        return name;
    }
}

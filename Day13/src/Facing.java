enum Facing {
    NORTH, EAST, SOUTH, WEST;

    static Facing turnLeft(Facing f)
    {
        int index = f.ordinal();
        int nextIndex = index + 3;
        Facing[] facings = Facing.values();
        nextIndex %= facings.length;
        return facings[nextIndex];
    }

    static Facing turnRight(Facing f)
    {
        int index = f.ordinal();
        int nextIndex = index + 1;
        Facing[] facings = Facing.values();
        nextIndex %= facings.length;
        return facings[nextIndex];
    }

}

public enum TurnDirection {
    LEFT, STRAIGHT, RIGHT;

    public static TurnDirection getNextDirection(TurnDirection t)
    {
        int index = t.ordinal();
        int nextIndex = index + 1;
        TurnDirection[] turnDirections = TurnDirection.values();
        nextIndex %= turnDirections.length;
        return turnDirections[nextIndex];
    }
}

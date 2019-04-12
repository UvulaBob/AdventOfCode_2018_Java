enum Track{
    INTERSECTION("+"),
    LEFT_CORNER("\\"),
    RIGHT_CORNER("/");

    private String value;
    Track(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }
}
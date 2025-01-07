public enum Names
{
    ALENA("Алёна"), ELENA("Елена"), KATYA("Екатерина");
    private String name;
    private Names(String name) { this.name = name; }
    public String getName()
    {
        return name;
    }
}
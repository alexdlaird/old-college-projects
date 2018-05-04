package roadsscholar;

/**
 * A city has a name and an intersection that meets at the city.
 * 
 * @author Kevin Bender
 * @author Alex Laird
 * @version 0.1
 */
public class City
{
    /** The intersection the city is at.*/
    private int intersection;
    /** The name of the city at this entry.*/
    private String name;

    /**
     * Construct a new city at a given intersection with a given name.
     *
     * @param intersection The intersection the city is at.
     * @param name The name of the city.
     */
    public City(int intersection, String name)
    {
        this.intersection = intersection;
        this.name = name;
    }

    /**
     * Retrieve the intersection the city is at.
     *
     * @return The intersection the city is at.
     */
    public int getIntersection()
    {
        return intersection;
    }

    /**
     * Retrieve the city name for this entry.
     *
     * @return The city name for this entry.
     */
    public String getName()
    {
        return name;
    }
}

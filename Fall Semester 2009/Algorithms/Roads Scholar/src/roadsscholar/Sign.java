package roadsscholar;

import java.util.Vector;

/**
 * A sign has a head (intersection met before the sign), tail (intersection met
 * after the sign), distance (distance between the last intersection and the
 * sign), cities (a list of all cities on the sign), and city distances (a list
 * corresponding to cities that contains the distances from the sign to each
 * city).
 * 
 * @author Kevin Bender
 * @author Alex Laird
 * @version 0.1
 */
public class Sign
{
    /** The last intersection before this sign.*/
    private int head;
    /** The next intersection after this sign.*/
    private int tail;
    /** The distance between the last intersection and this sign.*/
    private double distance;
    /** The cities on the sign.*/
    private Vector<City> cities;
    /** A vector corresponding to the cities vector, keeping track of distances
     from this sign to each city.*/
    private Vector<Integer> cityDistances;

    /**
     * Construct a sign and assign the distance from the last intersection.
     *
     * @param distance
     */
    public Sign(double distance)
    {
        this.distance = distance;
    }

    /**
     * Set the pointer to the last intersection.
     *
     * @param start The last intersection.
     */
    public void setHead(int head)
    {
        this.head = head;
    }

    /**
     * Retrieve the last intersection.
     *
     * @return The last intersection.
     */
    public int getHead()
    {
        return head;
    }

    /**
     * Set the pointer to the next intersection.
     *
     * @param end The next intersection.
     */
    public void setTail(int tail)
    {
        this.tail = tail;
    }

    /**
     * Retrieve the next intersection.
     *
     * @return The next intersection.
     */
    public int getTail()
    {
        return tail;
    }

    /**
     * Retrieve the distance from the last intersection.
     *
     * @return The distance from the last intersection.
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * Add a new entry to the sign.  This entry will be placed in the proper
     * place on the sign according to mileage (closest to farthest) from the
     * sign.
     * 
     * The double distance passed in will be rounded to the nearest
     * whole mile. Additionally, the distance between the last intersection
     * and the sign will be compensated for in added city distances.
     *
     * @param city The city to be added to the sign.
     * @param distance The distance from this sign to the city.
     */
    public void addCityToSign(City city, double distance)
    {
        // set the rounded city distance, less the distance between the last
        // intersection and the sign
        int rounded = (int) (Math.round(distance) - Math.round(this.distance));

        // construct the city vectors if they have not already been initialized
        if(cities == null)
        {
            cities = new Vector<City>();
            cityDistances = new Vector<Integer>();
        }

        for(int i = 0; i <= cities.size(); ++i)
        {
            // the distance was not less than any other distance, so place the
            // city at the end of the sign
            if(i == cities.size())
            {
                cities.add(city);
                cityDistances.add(rounded);
                break;
            }
            // if the new city is closer than this city, insert before that city
            else if(rounded < cityDistances.get(i))
            {
                cities.insertElementAt(city, i);
                cityDistances.insertElementAt(rounded, i);
                break;
            }
        }
    }

    /**
     * Builds a string for the output of a sign. Each city on the sign is on
     * its own line. The city name is padded with spaces to take up twenty
     * characters. The city name and spaces is followed by the distance from the
     * sign to the city.
     *
     * If the sign has not been initialized with any cities, null is returned.
     *
     * @return The string representation of the sign.
     */
    @Override
    public String toString()
    {
        String string = null;
        
        if(cities != null)
        {
            // initialize the line
            string = "";

            for(int i = 0; i < cities.size(); ++i)
            {
                String cityName = cities.get(i).getName();
                // get the name of the city
                string += cityName;
                // pad spaces so the city name and spaces fill twenty characters
                for(int j = cityName.length(); j < 20; ++j)
                {
                    string += " ";
                }
                // appended the padded city name along with the distance
                string += cityDistances.get(i);
                if(i != cities.size() - 1)
                {
                    string += "\n";
                }
            }
        }

        return string;
    }
}

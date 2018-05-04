package roadsscholar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * The driver class contains the main method and all execution calls to helper
 * methods to find the solution to the Roads Scholar problem.  The
 * Floyd-Warshall algorithm is used to solve the single-source shortest path
 * problem, and it is implemented in the drive() method.  The signs are then
 * constructed with the appropriate cities and distances in the buildSigns()
 * method.
 *
 * @author Kevin Bender
 * @author Alex Laird
 * @version 0.1
 */
public class Driver
{
    /** The number formatter when outputting the adjacency matrix.*/
    private final NumberFormat ADJACENCY_FORMAT = new DecimalFormat("00.00");
    /** The data file.*/
    private final String INPUT = "data.txt";
    /** The input reader for the data file.*/
    private BufferedReader in;
    /** The number of intersections.*/
    private int intersectionCount;
    /** The cities on the map.*/
    private City[] cities;
    /** The predecessor matrix for the Floyd-Warshall algorithm.*/
    private int[][] predecessor;
    /** The adjacency matrix for the Floyd-Warshall algorithm.*/
    private double[][] adjacency;
    /** The signs to be placed on the map.*/
    private Sign[] signs;

    /**
     * Constructs the map of roads, intersections, and cities.
     *
     * @param in The reader for the input file.
     */
    private void buildMap(BufferedReader in)
    {
        /** The scanner used for parsing the input file.*/
        Scanner scan = new Scanner(in);

        // initialize the intersection count
        intersectionCount = scan.nextInt();
        /** The number of roads.*/
        int roadCount = scan.nextInt();
        /** The number of cities.*/
        int cityCount = scan.nextInt();

        predecessor = new int[intersectionCount][intersectionCount];
        adjacency = new double[intersectionCount][intersectionCount];
        // fill predecessor and adjacency matrices with initial values
        for(int i = 0; i < intersectionCount; ++i)
        {
            for(int j = 0; j < intersectionCount; ++j)
            {
                predecessor[i][j] = -1;
                adjacency[i][j] = Double.MAX_VALUE;
            }
        }

        // fill the predecessor and adjacency matrices with their respective
        // roads at known locations
        for(int i = 0; i < roadCount; ++i)
        {
            int start = scan.nextInt();
            int end = scan.nextInt();
            double distance = scan.nextDouble();

            predecessor[start][end] = start;
            predecessor[end][start] = end;
            adjacency[start][end] = distance;
            adjacency[end][start] = distance;
        }

        // initialize the cities
        cities = new City[cityCount];

        // give cities pointers to their respective intersections
        for(int i = 0; i < cityCount; ++i)
        {
            cities[i] = new City(scan.nextInt(), scan.next());
        }

        /** The number of signs to be placed.*/
        int signCount = scan.nextInt();

        // initialize the signs
        signs = new Sign[signCount];

        // create an object and initialize known details for each sign
        for(int i = 0; i < signCount; ++i)
        {
            int head = scan.nextInt();
            int tail = scan.nextInt();
            double distance = scan.nextDouble();

            signs[i] = new Sign(distance);
            signs[i].setHead(head);
            signs[i].setTail(tail);
        }
    }

    /**
     * Performs the Floyd-Warshall algorithm on the matrices that implement the
     * given roads and intersections.
     */
    private void drive()
    {
        for(int k = 0; k < intersectionCount; ++k)
        {
            for(int i = 0; i < intersectionCount; ++i)
            {
                for(int j = 0; j < intersectionCount; ++j)
                {
                    if(i != j)
                    {
                        // fill the predecessor and adjacency matrices with the
                        // shortest paths at the given points
                        if(adjacency[i][k] + adjacency[k][j] < adjacency[i][j])
                        {
                            adjacency[i][j] = adjacency[i][k] + adjacency[k][j];
                            predecessor[i][j] = predecessor[k][j];
                        }
                    }
                }
            }
        }
    }

    /**
     * Build each of the specified signs, adding a city to the sign if the sign
     * is located along the shortest path to that city.
     */
    private void buildSigns()
    {
        for(int i = 0; i < signs.length; ++i)
        {
            // for city, trace it back to the head intersection of the sign to
            // see if the current sign actually lies on that path
            for(int j = 0; j < cities.length; ++j)
            {
                int intersection = cities[j].getIntersection();
                while(intersection > 0 && intersection < intersectionCount)
                {
                    // if the sign is found along the walk back, add the city
                    // to the sign
                    if(intersection == signs[i].getTail())
                    {
                        signs[i].addCityToSign(cities[j],
                                adjacency[signs[i].getHead()]
                                [cities[j].getIntersection()]);
                        break;
                    }
                    intersection = predecessor[signs[i].getHead()]
                            [intersection];
                }
            }
        }
    }

    /**
     * Output each of the constructed signs.
     */
    private void output()
    {
        // output each of the signs
        for(int i = 0; i < signs.length; ++i)
        {
            System.out.println(signs[i]);
            if(i != signs.length - 1)
            {
                System.out.println();
            }
        }
    }

    /**
     * Output the predecessor matrix.
     */
    private void outputPredecessor()
    {
        for(int i = 0; i < predecessor.length; ++i)
        {
            for(int j = 0; j < predecessor.length; ++j)
            {
                System.out.print(predecessor[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Output the adjacency matrix.
     */
    private void outputAdjacency()
    {
        for(int i = 0; i < adjacency.length; ++i)
        {
            for(int j = 0; j < adjacency.length; ++j)
            {
                if(adjacency[i][j] == Double.MAX_VALUE)
                {
                    System.out.print("inf ");
                }
                else
                {
                    System.out.print(ADJACENCY_FORMAT.format(
                            adjacency[i][j]) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Calls the execution methods.
     */
    public void run()
    {
        try
        {
            in = new BufferedReader(new FileReader(INPUT));

            // build the map from the input from
            buildMap(in);

            // run the algorithm to determine sign data
            drive();

            // construct the specified signs
            buildSigns();

            // output all the constructed signs
            output();
        }
        catch(FileNotFoundException c) {}
        catch(IOException c) {}
    }

    /**
     * Responsible for executing the application, then immedietly calling the
     * non-static run().
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args)
    {
        // get out of static land
        Driver roadsScholar = new Driver();
        roadsScholar.run();
    }
}
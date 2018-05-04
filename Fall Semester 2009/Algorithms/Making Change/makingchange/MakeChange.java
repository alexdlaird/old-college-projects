package makingchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Parsing input from a file, this program will solve numerous problem sets for
 * the minimal coin usage given a particular set of denominations. The input
 * can have multiple problem sets, each parsed line by line. The first line
 * must contain the number of denominations to be used, and it should be
 * followed by that number of integers (each on a new line).
 *
 * Following that, an integer should be given that is the number of values that
 * need to be solved. That number of integer values should then given. This
 * procedure can be repeated then multiple times.
 *
 * Double spacing should never be used, one value per line, all values must be
 * in integer form.
 *
 * @author Nathan Flack
 * @author Caleb Mays
 * @author Alex Laird
 * @file MakeChange.java
 * @version 0.1
 */
public class MakeChange
{
    /** The number formatter for prettier output.*/
    private final NumberFormat numFormat = new DecimalFormat("###,###");
    /** The file to be used for input.*/
    private final String inputName = "input.txt";
    /** The input reader for the file given by the inputName variable.*/
    private BufferedReader input;

    /** The array to store all denominations in a problem set.*/
    private int[] denom;
    /** The array to store all the values in a problem set.*/
    private int[] values;
    /** The table to store the solutions. This table is used for lookup, if a
     * method implementing lookup is being used, otherwise it is only used for
     * storage for lookup in the output.*/
    private int[][] table;

    /** The counter for the set number.*/
    private int setNum = 1;
    
    /**
     * Parses the input file. Stores the something number of denominations in
     * the denom array and k number of values in the first dimension of the
     * two-dimensional values array.
     *
     * @param input The reader which reads from the input file.
     * @param start The first line read from the set of data.
     * @throws FileNotFoundException The specified file could not be found.
     * @throws NumberFormatException A string was forced into a number variable.
     * @throws IOException An unknown error occured in Input/Output operations.
     */
    private void parseInputFile(BufferedReader input, String start)
            throws FileNotFoundException, NumberFormatException, IOException
    {
        // retrieve the denominations and store them in an array
        int n = Integer.parseInt(start);
        if(n < 0)
        {
            throw new NumberFormatException();
        }
        denom = new int[n];
        for(int i = 0; i < n; ++i)
        {
            denom[i] = Integer.parseInt(input.readLine());
            if(denom[i] < 0)
            {
                throw new NumberFormatException();
            }
        }

        // retrieve the values and store them in an array
        int k = Integer.parseInt(input.readLine());
        if(k < 0)
        {
            throw new NumberFormatException();
        }
        values = new int[k];
        for(int i = 0; i < k; ++i)
        {
            values[i] = Integer.parseInt(input.readLine());
            if(values[i] < 0)
            {
                throw new NumberFormatException();
            }
        }
    }

    /**
     * Execute the bottom-up algorithm to find the optimal solution of
     * coins using the denominations specified.
     *
     * @param n The number of cents to be divide among the denominations.
     * @throws OutOfMemoryErrror Thrown when the CPU runs out of memory to use.
     * @return The total number of coins used to solve n optimally.
     */
    private int bottomUp(int n)
            throws OutOfMemoryError
    {
        // solve each coin problem from 1 to n
        for(int i = 1; i <= n; ++i)
        {
            // solve each denomination for i
            for(int j = 0; j < denom.length; ++j)
            {
                // declare the temp total for comparison to our table lookup
                int tempTotal;
                // subtract the current denomination off i
                int next = i - denom[j];
                
                // the denomination is larger than the current number
                if(next < 0)
                {
                    break;
                }
                else
                {
                    // find the total using this denomination
                    tempTotal = table[next][denom.length];

                    // store in table if this total is less than the total
                    // currently stored in the table
                    if(tempTotal < table[i][denom.length] || j == 0)
                    {
                        for(int k = 0; k < denom.length + 1; ++k)
                        {
                            table[i][k] = table[next][k];
                        }
                        // increment the denomination just found and total
                        ++table[i][j];
                        ++table[i][denom.length];
                    }
                }
            }
        }

        return table[n][denom.length];
    }

    /**
     * Execute the resursive algorithm with memoization to find the optimal
     * solution of coins using the denominations specified.
     *
     * @param n The number of cents to be divide among the denominations.
     * @param d The index of the current denomination.
     * @param dRecur True if recursing through the denominations, false if
     * recursing through the values.
     * @throws StackOverflowError Since this method is recursive, there is a
     * the potential that the stack will quickly be used up. At that point,
     * the StackOverflowError will be thrown.
     * @return The total number of coins used to solve n optimally.
     */
    private int recursiveMemo(int n, int d, boolean dRecur)
            throws StackOverflowError
    {
        // recurse up once zero is reached to begin building the table
        if(n < 0)
        {
            return 0;
        }
        // skip if recursing over through denominations
        if(!dRecur)
        {
            // recurse to the next value down the list
            recursiveMemo(n - 1, d, false);
        }

        // at a valid value that needs to be solved and/or looked up
        if(n > 0)
        {
            // break out of the recursion once last denomination is reached
            if(d < 0)
            {
                return table[n][denom.length];
            }

            // recurse to the next denomination
            recursiveMemo(n, d - 1, true);
            // subtract the current denomination off n
            int next = n - denom[d];

            // the denomination is larger than the next number
            if(next < 0)
            {
                return table[n][denom.length];
            }
            
            // find the total using this denomination and a lookup
            int tempTotal = table[next][denom.length];

            // store in table if this total is less than the total currently
            // stored in the table
            if(tempTotal < table[n][denom.length] || d == 0)
            {
                for(int k = 0; k < denom.length + 1; ++k)
                {
                    table[n][k] = table[next][k];
                }
                // increment the denomination just found and total
                ++table[n][d];
                ++table[n][denom.length];
            }
        }

        return table[n][denom.length];
    }

    /**
     * Execute the recursive algorithm without memoization to find the optimal
     * solution of coins using the denominations specified. This is horribly
     * inefficient in any situation and becomes unbearable much beyond a value
     * of twenty and unbearable with values of triple digits and beyond.
     *
     * The only line that is different within this method is the line that
     * declares and sets tempTotal; instead of doing a table lookup, it
     * performs a recursion to solve the next number down.
     *
     * @param n The number of cents to be divide among the denominations.
     * @param d The index of the current denomination.
     * @param dRecur True if recursing through the denominations, false if
     * recursing through the values.
     * @throws StackOverflowError Since this method is recursive, there is a
     * the potential that the stack will quickly be used up. At that point,
     * the StackOverflowError will be thrown.
     * @return The total number of coins used to solve n optimally.
     */
    private int recursiveNoMemo(int n, int d, boolean dRecur)
            throws StackOverflowError
    {
        // recurse up once zero is reached to begin building the table
        if(n < 0)
        {
            return 0;
        }
        // skip if recursing over through denominations
        if(!dRecur)
        {
            // recurse to the next value down the list
            recursiveNoMemo(n - 1, d, false);
        }

        // at a valid value that needs to be solved and/or looked up
        if(n > 0)
        {
            // break out of the recursion once last denomination is reached
            if(d < 0)
            {
                return table[n][denom.length];
            }

            // recurse to the next denomination
            recursiveNoMemo(n, d - 1, true);
            // subtract the current denomination off n
            int next = n - denom[d];

            // the denomination is larger than the next number
            if(next < 0)
            {
                return table[n][denom.length];
            }
            
            // find the optimal total down from this point
            int tempTotal = recursiveNoMemo(next, denom.length - 1, false);

            // store in table if this total is less than the total currently
            // stored in the table
            if(tempTotal < table[n][denom.length] || d == 0)
            {
                for(int k = 0; k < denom.length + 1; ++k)
                {
                    table[n][k] = table[next][k];
                }
                // increment the denomination just found and total
                ++table[n][d];
                ++table[n][denom.length];
            }
        }

        return table[n][denom.length];
    }

    /**
     * Displays the results to the output stream, listing the denominations
     * from biggest to smallest.
     *
     * @param totalTime The total time for the problem.
     * @param last True if this is the last problem in the set, false otherwise.
     */
    private void output(long totalTime, boolean last)
    {
        boolean firstPrint = false;
        System.out.println("---");
        System.out.print(numFormat.format(table.length - 1) + " cents = ");

        // print out each denomination and how many of each coin was used
        for(int i = denom.length - 1; i >= 0; --i)
        {
            if(table[table.length - 1][i] > 0)
            {
                firstPrint = true;
                System.out.print(denom[i] + ":" + table[table.length - 1][i]
                        + " ");
            }
        }

        // validate that at least some denomination was output
        if(firstPrint)
        {
            System.out.println();
        }
        else if(table.length == 1)
        {
            System.out.println("No coins are used for the zero case.");
        }
        else
        {
            System.out.println("Error: No solution was found. Ensure you have "
                    + "pennies as a minimum denomination.");
        }

        // output the total time used
        System.out.println("Total Time: " + numFormat.format(totalTime) + " ns");

        // output the footer if the last item in a set
        if(last)
        {
            System.out.println("---");
        }
    }

    /**
     * Calls the execution methods.
     */
    private void run()
    {
        try
        {
            // instantiate the input reader and the first line
            input = new BufferedReader(new FileReader(inputName));
            String start;
            
            // continue grabbing problem sets until the end of the file
            while((start = input.readLine()) != null)
            {
                System.out.println("--Problem Set #" + setNum + "--");
                boolean last = false;

                // parse the problem set from the file
                parseInputFile(input, start);

                // solve the problem set
                for(int i = 0; i < values.length; ++i)
                {
                    // set the last flag
                    if(i == values.length - 1)
                    {
                        last = true;
                    }

                    table = new int[values[i] + 1][denom.length + 1];
                    long startTime = System.nanoTime();
                    bottomUp(values[i]);
                    //recursiveMemo(values[i], denom.length - 1, false);
                    //recursiveNoMemo(values[i], denom.length - 1, false);
                    long endTime = System.nanoTime();
                    long totalTime = endTime - startTime;
                    output(totalTime, last);
                }

                ++setNum;
                System.out.println();
            }

            // close our connection to the file
            input.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("--Error: File Not Found--");
            System.out.println("The file " + inputName + " could not be found. "
                    + "If this is not the correct filename,\nyou may change "
                    + "the name by changing the String variable "
                    + "inputName.\n");
        }
        catch(NumberFormatException e)
        {
            System.out.println("--Error: Invalid Number Format--");
            System.out.println("The file specified, " + inputName + ", "
                    + "contains elements that are not positive integers.\nThe "
                    + "parseInputFile method only knows how to parse files "
                    + "containing positive integer\nvalues that can be "
                    + "manipulated mathematically.\nThis may occur if the file "
                    + "contains any elements other than integers or,\nmore "
                    + "specifically, if the number of expected denominations "
                    + "or values specified\nexceeds the number of "
                    + "denominations or values that follow.");
        }
        catch(OutOfMemoryError e)
        {
            System.out.println("---Error: Out Of Memory---");
            System.out.println("The value given to the bottom up method is too "
                    + "large to be solved on this\ncomputer. Sorry, there's no "
                    + "way around this except to try running this\nvalue again "
                    + "on a computer with more memory.");
        }
        catch(StackOverflowError e)
        {
            System.out.println("---Error: Stack Overflow---");
            System.out.println("Recursion using memoization may overflow when "
                    + "attempting to run on very large\nnumbers; if this is"
                    + "the case, try using bottom up to solve. Recursion using"
                    + "\nno memoization will likely overflow on anything over "
                    + "twenty.\nIt's a horribly inefficient method and was "
                    + "implemented for your amusement,\nso you can laugh at "
                    + "how lousy it is.");
        }
        catch(IOException e)
        {
            System.out.println("--Error: Unknown Input/Ouput Fault--");
            System.out.println("An unknown error has occured when trying to "
                    + "read from the file " + inputName + ".");
        }
    }

    /**
     * Responsible for executing the application, then immedietly calls the
     * non-static run().
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args)
    {
        // get out of static land
        MakeChange makeChange = new MakeChange();
        makeChange.run();
    }
}

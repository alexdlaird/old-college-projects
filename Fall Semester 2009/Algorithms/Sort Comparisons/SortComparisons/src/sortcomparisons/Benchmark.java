package sortcomparisons;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Handles the array generation, sorting calls, and benchmark timings and outputs
 * for the program.  The tests have already been set up, so to refine the results
 * received, simply change the various setSize() parameters, sort function calls,
 * or setMaxValue parameters of the various tests.
 *
 * @author Alex Laird
 * @version 1.0
 */
public class Benchmark
{
    // object delcarations
    private static final Random random = new Random();
    private static final NumberFormat formatter = new DecimalFormat("###,###");
    private static final InsertionSort insertionSort = new InsertionSort();
    private static final QuickSort quickSort = new QuickSort();

    // the size of the array
    private static int size = 10;
    // the maximum size of a generated array
    private static int maxSize = 500;
    // random numbers will be generated between 0 and maxValue
    private static int maxValue = size * 10;

    // the test set counter (incremented after every test set completes)
    private static int testSetCounter = 1;

    /**
     * Outputs the header for the current test set.
     *
     * @param summary The summary to display for the test set.
     */
    private static void outputTestSetHeader(String summary)
    {
        System.out.println("---TEST SET #" + testSetCounter + "--------------------------------");
        System.out.println("Summary: " + summary + "\n");
    }

    /**
     * Outputs the footer for the current test set.
     *
     * * @param conclusions Conclusions to be displayed. If no conclusions are to be displayed, make this parameter null.
     */
    private static void outputTestSetFooter(String conclusions)
    {
        System.out.println("---TEST SET #" + testSetCounter + " COMPLETED----------------------");
        if(conclusions != null)
        {
            System.out.println("Conclusions: " + conclusions);
        }
        System.out.println("----------------------------------------------\n\n\n");
    }

    /**
     * Outputs the contents of an array.
     *
     * @param array The array to be output.
     */
    private static void outputArray(int[] array)
    {
        for(int i = 0; i < array.length; ++i)
        {
            if(i < array.length - 1)
            {
                System.out.print(array[i] + ", ");
            }
            else
            {
                System.out.print(array[i]);
            }
        }
    }

    /**
     * Outputs benchmark header information regarding the array to be sorted.
     *
     * @param array The unsorted array.
     * @param testCounter The current test number being run.
     * @param method A string briefly describing the sort method used (QuickSort, whether it was pre-sorted, etc.).
     * @param arrayOutput True if the contents of the array should be output, false otherwise.
     */
    private static void outputTestHeader(int[] array, int testCounter,  String method, boolean arrayOutput)
    {
        System.out.println("----------------------------");
        System.out.println("Test " + testSetCounter + "." + testCounter);
        System.out.println("Array Size: " + formatter.format(array.length));
        if(arrayOutput)
        {
            System.out.print("Array Elements Pre-Sort: [");
            outputArray(array);
            System.out.println("]");
        }
        System.out.println("Method Used: " + method);
        System.out.println("Start Time: " + formatter.format(System.nanoTime()) + " ns");
    }

    /**
     * Outputs the benchmark footer information regarding the recently sorted array.
     * 
     * @param array The array that was sorted.
     * @param startTime The time in milliseconds when the sort started.
     * @param endTime The time in milliseconds when the sort ended.
     * @param arrayOutput True if the contents of the array should be output, false otherwise.
     */
    private static void outputTestFooter(int[] array, long startTime, long endTime, boolean arrayOutput)
    {
        System.out.println("End Time: " + formatter.format(endTime) + " ns");
        System.out.println("Total Runtime: " + formatter.format(endTime - startTime) + " ns");
        System.out.println("Time Per Element: " + formatter.format((endTime - startTime) / array.length) + " ns");
        if(arrayOutput)
        {
            System.out.print("Array Elements Post-Sort: [");
            outputArray(array);
            System.out.println("]");
        }
        System.out.println("----------------------------\n");
    }

    /**
     * Sets the variable that defines the maximum value of an element in an array
     * to a random integer between 0 and max.
     *
     * @param max The maximum value an element may be.
     */
    private static void randomizeMaxValue(int max)
    {
        maxValue = random.nextInt(max);
    }

    /**
     * Sets the variable that defines the maximum value of an element in an array
     * to max.
     * 
     * @param max The new maximum value for a value in a generated array.
     */
    private static void setMaxValue(int max)
    {
        maxValue = max;
    }

    /**
     * Sets the variable that defines the maximum sizes of a generated array size.
     *
     * @param size The new maximum size of a generated array size.
     */
    private static void setMaxSize(int size)
    {
        maxSize = size;
    }

    /**
     * Sets the variable that defines the array size to a random integer between
     * 0 and maxSize.
     */
    private static void randomizeSize()
    {
        size = random.nextInt(maxSize);
    }

    /**
     * Sets the variable that defines the array size to a random integer between
     * 0 and max.
     * 
     * @param max The maximum size a generated array may be.
     */
    private static void randomizeSize(int max)
    {
        size = random.nextInt(max);
    }

    /**
     * Sets the variable that defines size of a generated array to newSize.
     *
     * @param newSize The new size of a generated array.
     */
    private static void setSize(int newSize)
    {
        size = newSize;
    }

    /**
     * Generates an array of size pseudo-random numbers between 0 and maxValue.
     * 
     * @return An array of size pseudo-random numbers.
     */
    private static int[] generateRandomizedArray()
    {
        // declare an array for the elements
        int[] array = new int[size];

        for(int i = 0; i < size; ++i)
        {
            array[i] = random.nextInt(maxValue);
        }

        return array;
    }

    /**
     * Generates an array of size psueod-random numbers between 0 and maxValue
     * and returns the array sorted using QuickSort.
     *
     * @return An array of size psueo-random numbers.
     */
    private static int[] generateSortedArray()
    {
        // instantiate a randomized array
        int[] array = generateRandomizedArray();

        // sort the array
        quickSort.sortUsingFor(array, 0, array.length - 1);

        return array;
    }

    /**
     * Copy the contents of the passed in array to a new array and return that array.
     * 
     * @param array The array to be copied.
     * @return The new array.
     */
    private static int[] copyArray(int[] array)
    {
        int[] newArray = new int[array.length];
        
        for(int i = 0; i < array.length; ++i)
        {
            newArray[i] = array[i];
        }

        return newArray;
    }

    /**
     * Verification of sorting algorithms using arrays of size 10. Should run through
     * all methods in InsertionSort and QuickSort. Displays their before and after
     * output to ensure they are sorting correctly.
     */
    public static void sortVerification()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;
        
        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;

        outputTestSetHeader("Verification of sorting algorithms using arrays of size 10");
        setSize(10);
        setMaxValue(1000);

        testOne = generateRandomizedArray();
        testTwo = copyArray(testOne);
        testThree = copyArray(testOne);
        testFour = copyArray(testOne);
        testFive = copyArray(testOne);

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "InsertionSort on randomized array", true);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, true);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort on randomized array using for loop and standard partition", true);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, true);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort on randomized array using while loop and standard partition", true);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, true);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "QuickSort on randomized array using for loop and randomized partition", true);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, true);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort on randomized array using while loop and randomized partition", true);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, true);
        ++testCounter;
        /*****************************/

        outputTestSetFooter(null);
        ++testSetCounter;
    }

    /**
     * Compare InsertionSort and QuickSort times; note when InsertionSort becomes faster than QuickSort.
     */
    public static void compareInsertionWithQuick()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;

        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;
        int[] testSix = null;
        int[] testSeven = null;
        int[] testEight = null;
        int[] testNine = null;
        int[] testTen = null;
        int[] testEleven = null;
        int[] testTwelve = null;
        int[] testThirteen = null;
        int[] testFourteen = null;
        int[] testFifteen = null;
        int[] testSixteen = null;
        int[] testSeventeen = null;
        int[] testEighteen = null;
        int[] testNineteen = null;
        int[] testTwenty = null;
        int[] testTwentyOne = null;
        int[] testTwentyTwo = null;
        int[] testTwentyThree = null;
        int[] testTwentyFour = null;
        
        outputTestSetHeader("Compare InsertionSort and QuickSort times; note when InsertionSort becomes faster than QuickSort");
        setSize(5);
        setMaxValue(1000);

        testOne = generateRandomizedArray();
        testTwo = copyArray(testOne);
        testThree = copyArray(testOne);

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "InsertionSort on randomized array of size " + testOne.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort using for loop on randomized array of size " + testTwo.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort using for loop on randomized array of size " + testThree.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(7);
        testFour = generateRandomizedArray();
        testFive = copyArray(testFour);
        testSix = copyArray(testFour);

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "InsertionSort on randomized array of size " + testFour.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testFour);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort using for loop on randomized array of size " + testFive.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSix, testCounter, "QuickSort using for loop on randomized array of size " + testSix.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSix, 0, testSix.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(10);
        testSeven = generateRandomizedArray();
        testEight = copyArray(testSeven);
        testNine = copyArray(testSeven);

        /***TEST**********************/
        outputTestHeader(testSeven, testCounter, "InsertionSort on randomized array of size " + testSeven.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testSeven);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEight, testCounter, "QuickSort using for loop on randomized array of size " + testEight.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testNine, testCounter, "QuickSort using for loop on randomized array of size " + testNine.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(13);
        testTen = generateRandomizedArray();
        testEleven = copyArray(testTen);
        testTwelve = copyArray(testTen);

        /***TEST**********************/
        outputTestHeader(testTen, testCounter, "InsertionSort on randomized array of size " + testTen.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testTen);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEleven, testCounter, "QuickSort using for loop on randomized array of size " + testEleven.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testEleven, 0, testEleven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwelve, testCounter, "QuickSort using for loop on randomized array of size " + testTwelve.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(15);
        testThirteen = generateRandomizedArray();
        testFourteen = copyArray(testThirteen);
        testFifteen = copyArray(testThirteen);

        /***TEST**********************/
        outputTestHeader(testThirteen, testCounter, "InsertionSort on randomized array of size " + testThirteen.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testThirteen);
        endTime = System.nanoTime();

        outputTestFooter(testThirteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFourteen, testCounter, "QuickSort using for loop on randomized array of size " + testFourteen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testFourteen, 0, testFourteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFourteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFifteen, testCounter, "QuickSort using for loop on randomized array of size " + testFifteen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testFifteen, 0, testFifteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFifteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(20);
        testSixteen = generateRandomizedArray();
        testSeventeen = copyArray(testSixteen);
        testEighteen = copyArray(testSixteen);

        /***TEST**********************/
        outputTestHeader(testSixteen, testCounter, "InsertionSort on randomized array of size " + testSixteen.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testSixteen);
        endTime = System.nanoTime();

        outputTestFooter(testSixteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSeventeen, testCounter, "QuickSort using for loop on randomized array of size " + testSeventeen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeventeen, 0, testSeventeen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeventeen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEighteen, testCounter, "QuickSort using for loop on randomized array of size " + testEighteen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testEighteen, 0, testEighteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEighteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(30);
        testNineteen = generateRandomizedArray();
        testTwenty = copyArray(testNineteen);
        testTwentyOne = copyArray(testNineteen);

        /***TEST**********************/
        outputTestHeader(testNineteen, testCounter, "InsertionSort on randomized array of size " + testSixteen.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testNineteen);
        endTime = System.nanoTime();

        outputTestFooter(testNineteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwenty, testCounter, "QuickSort using for loop on randomized array of size " + testSeventeen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwenty, 0, testTwenty.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwenty, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyOne, testCounter, "QuickSort using for loop on randomized array of size " + testEighteen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwentyOne, 0, testTwentyOne.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(50);
        testTwentyTwo = generateRandomizedArray();
        testTwentyThree = copyArray(testTwentyTwo);
        testTwentyFour = copyArray(testTwentyTwo);

        /***TEST**********************/
        outputTestHeader(testTwentyTwo, testCounter, "InsertionSort on randomized array of size " + testSixteen.length, false);
        startTime = System.nanoTime();
        insertionSort.sort(testTwentyTwo);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyThree, testCounter, "QuickSort using for loop on randomized array of size " + testSeventeen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwentyThree, 0, testTwentyThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyFour, testCounter, "QuickSort using for loop on randomized array of size " + testEighteen.length + " using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwentyFour, 0, testTwentyFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        outputTestSetFooter("For arrays of sizes less than 10 or 15 (and sometimes up to 60 or 70), use InsertionSort. Beyond that, depending\n             on the processor, QuickSort will likely finish fastest. Furthermore, the larger the array with QuickSort,\n             the smaller the time per element seems to get.");
        ++testSetCounter;
    }

    /**
     * Comparison of QuickSort using a for loop and using a while loop on randomized arrays of size 10, 1,000, and  50,000 elements.
     */
    public static void compareQuickForWithQuickWhile()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;

        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;
        int[] testSix = null;
        int[] testSeven = null;
        int[] testEight = null;
        int[] testNine = null;
        int[] testTen = null;
        int[] testEleven = null;
        int[] testTwelve = null;

        outputTestSetHeader("Comparison of QuickSort using a for loop and using a while loop on randomized arrays of size 10, 1,000, and  50,000 elements");
        setSize(10);
        setMaxValue(1000);

        testOne = generateRandomizedArray();
        testTwo = copyArray(testOne);

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "QuickSort using for loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testOne, 0, testOne.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort using while loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(1000);
        testThree = generateRandomizedArray();
        testFour = copyArray(testThree);

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort  using for loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "QuickSort using while loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(50000);
        testFive = generateRandomizedArray();
        testSix = copyArray(testFive);

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort using for loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSix, testCounter, "QuickSort using while loop on randomized array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testSix, 0, testSix.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(10);
        testSeven = generateSortedArray();
        testEight = copyArray(testSeven);

        /***TEST**********************/
        outputTestHeader(testSeven, testCounter, "QuickSort using for loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeven, 0, testSeven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEight, testCounter, "QuickSort using while loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(1000);
        testNine = generateSortedArray();
        testTen = copyArray(testNine);

        /***TEST**********************/
        outputTestHeader(testNine, testCounter, "QuickSort using for loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTen, testCounter, "QuickSort using while loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testTen, 0, testTen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(50000);
        testEleven = generateSortedArray();
        testTwelve = copyArray(testEleven);

        /***TEST**********************/
        outputTestHeader(testEleven, testCounter, "QuickSort using for loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testEleven, 0, testEleven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwelve, testCounter, "QuickSort using while loop on pre-sorted array", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        outputTestSetFooter("With the exception of smaller array sizes, the while loop, a double-pointer partition method, beats out the for loop, a single-pointer partition method.");
        ++testSetCounter;
    }

    /**
     * Comparison of various partitioning methods for QuickSort.
     */
    public static void compareQuickPartitions()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;

        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;
        int[] testSix = null;
        int[] testSeven = null;
        int[] testEight = null;
        int[] testNine = null;
        int[] testTen = null;
        int[] testEleven = null;
        int[] testTwelve = null;
        int[] testThirteen = null;
        int[] testFourteen = null;
        int[] testFifteen = null;
        int[] testSixteen = null;
        int[] testSeventeen = null;
        int[] testEighteen = null;
        int[] testNineteen = null;
        int[] testTwenty = null;
        int[] testTwentyOne = null;
        int[] testTwentyTwo = null;
        int[] testTwentyThree = null;
        int[] testTwentyFour = null;
        
        outputTestSetHeader("Comparison of various partitioning methods for QuickSort");
        setSize(50000);
        setMaxValue(1000);

        testOne = generateRandomizedArray();
        testTwo = copyArray(testOne);
        testThree = copyArray(testOne);
        testFour = copyArray(testOne);

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testOne, 0, testOne.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        testFive = generateSortedArray();
        testSix = copyArray(testFive);
        testSeven = copyArray(testFive);
        testEight = copyArray(testFive);

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSix, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testSix, 0, testSix.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSeven, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testSeven, 0, testSeven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEight, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(1000);

        testNine = generateRandomizedArray();
        testTen = copyArray(testNine);
        testEleven = copyArray(testNine);
        testTwelve = copyArray(testNine);

        /***TEST**********************/
        outputTestHeader(testNine, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTen, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testTen, 0, testTen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEleven, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testEleven, 0, testEleven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwelve, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        testThirteen = generateSortedArray();
        testFourteen = copyArray(testThirteen);
        testFifteen = copyArray(testThirteen);
        testSixteen = copyArray(testThirteen);

        /***TEST**********************/
        outputTestHeader(testThirteen, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testThirteen, 0, testThirteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThirteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFourteen, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testFourteen, 0, testFourteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFourteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFifteen, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFifteen, 0, testFifteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFifteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSixteen, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testSixteen, 0, testSixteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSixteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(10000);

        testSeventeen = generateRandomizedArray();
        testEighteen = copyArray(testSeventeen);
        testNineteen = copyArray(testSeventeen);
        testTwenty = copyArray(testSeventeen);

        /***TEST**********************/
        outputTestHeader(testSeventeen, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeventeen, 0, testSeventeen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeventeen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST #18**********************/
        outputTestHeader(testEighteen, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testEighteen, 0, testEighteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEighteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testNineteen, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testNineteen, 0, testNineteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNineteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST***********************/
        outputTestHeader(testTwenty, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTwenty, 0, testTwenty.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwenty, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        testTwentyOne = generateSortedArray();
        testTwentyTwo = copyArray(testTwentyOne);
        testTwentyThree = copyArray(testTwentyOne);
        testTwentyFour = copyArray(testTwentyOne);

        /***TEST**********************/
        outputTestHeader(testTwentyOne, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwentyOne, 0, testTwentyOne.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyTwo, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testTwentyTwo, 0, testTwentyTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyThree, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testTwentyThree, 0, testTwentyThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwentyFour, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTwentyFour, 0, testTwentyFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwentyFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        outputTestSetFooter("Randomized partitioning, with the exception of randomized partition with a for loop on smaller array sizes,\n             is generally slower on unsorted arrays. Once the array sizes get to be in the thousands, a while loop using standardized\n             partitioning proves to be the most efficient on unsorted arrays. On pre-sorted arrays of any size, randomized\n             partitioning is almost always the most efficient.");
        ++testSetCounter;
    }

    /**
     * Comparison of times using InsertionSort and  QuickSort on changing data with unchanging size
     */
    public static void compareSpeedDifferencesOnChangingData()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;

        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;
        int[] testSix = null;
        int[] testSeven = null;
        int[] testEight = null;
        int[] testNine = null;
        int[] testTen = null;
        int[] testEleven = null;
        int[] testTwelve = null;
        int[] testThirteen = null;
        int[] testFourteen = null;
        int[] testFifteen = null;
        int[] testSixteen = null;
        int[] testSeventeen = null;
        int[] testEighteen = null;
        int[] testNineteen = null;
        int[] testTwenty = null;

        outputTestSetHeader("Comparison of times using InsertionSort and  QuickSort on changing data with unchanging size");
        setSize(50);
        setMaxValue(1000);

        /***TEST**********************/
        testOne = generateRandomizedArray();
        outputTestHeader(testOne, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testTwo = generateRandomizedArray();
        outputTestHeader(testTwo, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testThree = generateRandomizedArray();
        outputTestHeader(testThree, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testFour = generateRandomizedArray();
        outputTestHeader(testFour, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testFive = generateRandomizedArray();
        outputTestHeader(testFive, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(100);

        /***TEST**********************/
        testSix = generateRandomizedArray();
        outputTestHeader(testSix, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testSix);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testSeven = generateRandomizedArray();
        outputTestHeader(testSeven, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeven, 0, testSeven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testEight = generateRandomizedArray();
        outputTestHeader(testThree, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testNine = generateRandomizedArray();
        outputTestHeader(testNine, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testTen = generateRandomizedArray();
        outputTestHeader(testTen, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTen, 0, testTen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(1000);

        /***TEST**********************/
        testEleven = generateRandomizedArray();
        outputTestHeader(testEleven, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testEleven);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testTwelve = generateRandomizedArray();
        outputTestHeader(testTwelve, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testThirteen = generateRandomizedArray();
        outputTestHeader(testThirteen, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThirteen, 0, testThirteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThirteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testFourteen = generateRandomizedArray();
        outputTestHeader(testFourteen, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFourteen, 0, testFourteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFourteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testFifteen = generateRandomizedArray();
        outputTestHeader(testFifteen, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFifteen, 0, testFifteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFifteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(5000);

        /***TEST**********************/
        testSixteen = generateRandomizedArray();
        outputTestHeader(testSixteen, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testSixteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testSeventeen = generateRandomizedArray();
        outputTestHeader(testSeventeen, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeventeen, 0, testSeventeen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeventeen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testEighteen = generateRandomizedArray();
        outputTestHeader(testEighteen, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testEighteen, 0, testEighteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEighteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testNineteen = generateRandomizedArray();
        outputTestHeader(testNineteen, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testNineteen, 0, testNineteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNineteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        testTwenty = generateRandomizedArray();
        outputTestHeader(testTwenty, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTwenty, 0, testTwenty.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwenty, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        outputTestSetFooter("As the array sizes increase, the difference in speed fluctuations also increases more significantly.");
        ++testSetCounter;
    }

    /**
     * Comparison speeds of InsertionSort and QuickSort on both unsorted and pre-sorted arrays.
     */
    public static void comparePreSortedWithUnsorted()
    {
        // declare test variables
        long startTime;
        long endTime;
        int testCounter = 1;

        // declare test arrays
        int[] testOne = null;
        int[] testTwo = null;
        int[] testThree = null;
        int[] testFour = null;
        int[] testFive = null;
        int[] testSix = null;
        int[] testSeven = null;
        int[] testEight = null;
        int[] testNine = null;
        int[] testTen = null;
        int[] testEleven = null;
        int[] testTwelve = null;
        int[] testThirteen = null;
        int[] testFourteen = null;
        int[] testFifteen = null;

        outputTestSetHeader("Comparison speeds of InsertionSort and QuickSort on both unsorted and pre-sorted arrays");
        setSize(10000);
        setMaxValue(1000);

        testOne = generateRandomizedArray();
        testTwo = copyArray(testOne);
        testThree = copyArray(testOne);
        testFour = copyArray(testOne);
        testFive = copyArray(testOne);

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        // use the same arrays again, now that they're sorted

        /***TEST**********************/
        outputTestHeader(testOne, testCounter, "InsertionSort on pre-sorted array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testOne);
        endTime = System.nanoTime();

        outputTestFooter(testOne, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwo, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwo, 0, testTwo.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwo, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThree, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThree, 0, testThree.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThree, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFour, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFour, 0, testFour.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFour, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFive, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFive, 0, testFive.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFive, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(50000);

        testSix = generateRandomizedArray();
        testSeven = copyArray(testSix);
        testEight = copyArray(testSix);
        testNine = copyArray(testSix);
        testTen = copyArray(testSix);

        /***TEST**********************/
        outputTestHeader(testSix, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testSix);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSeven, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeven, 0, testSeven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEight, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testNine, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTen, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTen, 0, testTen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        // use the same arrays again, now that they're sorted

        /***TEST**********************/
        outputTestHeader(testSix, testCounter, "InsertionSort on pre-sorted array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testSix);
        endTime = System.nanoTime();

        outputTestFooter(testSix, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testSeven, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testSeven, 0, testSeven.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testSeven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testEight, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testEight, 0, testEight.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testEight, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testNine, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testNine, 0, testNine.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testNine, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTen, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testTen, 0, testTen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        setSize(1000);

        testEleven = generateRandomizedArray();
        testTwelve = copyArray(testEleven);
        testThirteen = copyArray(testEleven);
        testFourteen = copyArray(testEleven);
        testFifteen = copyArray(testEleven);

        /***TEST**********************/
        outputTestHeader(testEleven, testCounter, "InsertionSort on randomized array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testEleven);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwelve, testCounter, "QuickSort using for loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThirteen, testCounter, "QuickSort using for loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThirteen, 0, testThirteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThirteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFourteen, testCounter, "QuickSort using while loop on randomized array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFourteen, 0, testFourteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFourteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFifteen, testCounter, "QuickSort using while loop on randomized array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFifteen, 0, testFifteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFifteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        // use the same arrays again, now that they're sorted

        /***TEST**********************/
        outputTestHeader(testEleven, testCounter, "InsertionSort on pre-sorted array", false);
        startTime = System.nanoTime();
        insertionSort.sort(testEleven);
        endTime = System.nanoTime();

        outputTestFooter(testEleven, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testTwelve, testCounter, "QuickSort using for loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingFor(testTwelve, 0, testTwelve.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testTwelve, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testThirteen, testCounter, "QuickSort using for loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingFor(testThirteen, 0, testThirteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testThirteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFourteen, testCounter, "QuickSort using while loop on pre-sorted array using standard partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortUsingWhile(testFourteen, 0, testFourteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFourteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        /***TEST**********************/
        outputTestHeader(testFifteen, testCounter, "QuickSort using while loop on pre-sorted array using randomized partitioning", false);
        startTime = System.nanoTime();
        quickSort.sortRandomizedPartitionUsingWhile(testFifteen, 0, testFifteen.length - 1);
        endTime = System.nanoTime();

        outputTestFooter(testFifteen, startTime, endTime, false);
        ++testCounter;
        /*****************************/

        outputTestSetFooter("On pre-sorted arrays, InsertionSort is always the most efficient, especially once the array\n             sizes reach the thousands, since it is O(n).");
        ++testSetCounter;
    }

    /**
     * The main method, which calls test methods that performs the array
     * generation, sorting callings, and benchmark time markings and output calls
     * of the program.
     *
     * @param args The command-line arguments, which are unused in this program.
     */
    public static void main(String[] args)
    {
        sortVerification();
        compareInsertionWithQuick();
        compareQuickForWithQuickWhile();
        compareQuickPartitions();
        compareSpeedDifferencesOnChangingData();
        comparePreSortedWithUnsorted();
    }
}

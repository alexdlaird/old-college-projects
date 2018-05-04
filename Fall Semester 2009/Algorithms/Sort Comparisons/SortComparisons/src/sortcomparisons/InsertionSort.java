package sortcomparisons;

/**
 * Handles InsertionSort and all of its methods.
 *
 * @author Alex Laird
 * @version 1.0
 */
public class InsertionSort
{
    /**
     * Performs a looping InsertionSort algorithm on an array.
     *
     * @param array
     */
    public void sort(int[] array)
    {
        for(int j = 0; j < array.length; ++j)
        {
            int key = array[j];
            int i = j - 1;

            while(i >= 0 && array[i] > key)
            {
                array[i + 1] = array[i];
                i = i - 1;
            }

            array[i + 1] = key;
        }
    }
}

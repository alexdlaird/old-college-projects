#ifndef MAKE_CHANGE_H
#define	MAKE_CHANGE_H

// system includes
#include <string>
#include <vector>

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
 * @author Alex Laird
 * @version 0.1
 */
class MakeChange
{
    private:
        /** The vector to store all denominations in a problem set.*/
        int denomLength;
        int* denom;
        /** The vector to store all the values in a problem set.*/
        int* values;
        int valuesLength;
        /** The table to store the solutions. This table is used for lookup, if
         * a method implementing lookup is being used, otherwise it is only
         * used for storage for lookup in the output.*/
        int** table;
        int tableLength;
        /** The file to be used for input.*/
        std::string inputName;
        /** The counter for the set number.*/
        int setNum;

        /**
         * Execute the bottom-up algorithm to find the optimal solution of
         * coins using the denominations specified.
         *
         * @param n The number of cents to be divide among the denominations.
         * @return The total number of coins used to solve n optimally.
         */
        int bottomUp(int n);

        /**
         * Execute the resursive algorithm with memoization to find the optimal
         * solution of coins using the denominations specified.
         *
         * @param n The number of cents to be divide among the denominations.
         * @param d The index of the current denomination.
         * @param dRecur True if recursing through the denominations, false if
         * recursing through the values.
         * @return The total number of coins used to solve n optimally.
         */
        int recursiveMemo(int n, int d, bool dRecur);

        /**
         * Execute the recursive algorithm without memoization to find the
         * optimal solution of coins using the denominations specified. This is
         * horribly inefficient in any situation and becomes unbearable much
         * beyond a value of twenty and unbearable with values of triple digits
         * and beyond.
         *
         * The only line that is different within this method is the line that
         * declares and sets tempTotal; instead of doing a table lookup, it
         * performs a recursion to solve the next number down.
         *
         * @param n The number of cents to be divide among the denominations.
         * @param d The index of the current denomination.
         * @param dRecur True if recursing through the denominations, false if
         * recursing through the values.
         * @return The total number of coins used to solve n optimally.
         */
        int recursiveNoMemo(int n, int d, bool dRecur);

        /**
         * Parses the input file. Stores the something number of denominations
         * in the denom array and k number of values in the first dimension of
         * the two-dimensional values array.
         *
         * @param input The reader which reads from the input file.
         * @param start The first line read from the set of data.
         * @return Returns 0 if the parsing was successful, -1 for a number
         * format exception.
         */
        int parseInputFile(std::ifstream& input, std::string start);

        /**
         * Formats a number to not contain any decimals and to separate each
         * third digit with a comma.
         *
         * @param number The number to be formatted.
         * @return A formatted representation of number.
         */
        std::string numberFormat(int number);

        /**
         * Displays the results to the output stream, listing the denominations
         * from biggest to smallest.
         *
         * @param totalTime The total time for the problem.
         * @param last True if this is the last problem in the set, false
         * otherwise.
         */
        void output(long totalTime, bool last);

    public:
        /**
         * Responsible for constructing the class and initializing class
         * variables.
         */
        MakeChange();

        /**
         * Responsible for destructing the class and deleting objects.
         */
        ~MakeChange();

        /**
         * Calls the execution methods.
         *
         * @return Returns 0 for a successful run, -1 if an error occured.
         */
        int run();
};

#endif

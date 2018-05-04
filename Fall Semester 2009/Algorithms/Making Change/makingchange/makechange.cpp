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
 * @file makechange.cpp
 * @version 0.1
 */

// system includes
#include <iostream>
#include <fstream>
#include <sstream>

// local includes
#include "makechange.h"

MakeChange::MakeChange()
{
    inputName = "input.txt";
    setNum = 1;
}

MakeChange::~MakeChange()
{
    
}

int MakeChange::parseInputFile(std::ifstream& input, std::string start)
{
    std::string line;
    
    // retrieve the denominations and store them in an array
    int n = atoi(start.c_str());
    if(n < 0)
    {
        return -1;
    }
    denom = new int[n];
    denomLength = n;
    for(int i = 0; i < n; ++i)
    {
        std::getline(input, line);
        int temp = atoi(line.c_str());
        if(temp <= 0)
        {
            return -1;
        }
        denom[i] = temp;
    }

    // retrieve the values and store them in an array
    std::getline(input, line);
    int k = atoi(line.c_str());
    if(k < 0)
    {
        return -1;
    }
    values = new int[k];
    valuesLength = k;
    for(int i = 0; i < k; ++i)
    {
        std::getline(input, line);
        int temp = atoi(line.c_str());
        if(temp < 0)
        {
            return -1;
        }
        values[i] = temp;
    }

    return 0;
}

int MakeChange::bottomUp(int n)
{
    // solve each coin problem from 1 to n
    for(int i = 1; i <= n; ++i)
    {
        // solve each denomination for i
        for(int j = 0; j < denomLength; ++j)
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
            if(next >= 0)
            {
                // find the total using this denomination
                tempTotal = table[next][denomLength];

                // store in table if this total is less than the total
                // currently stored in the table
                if(tempTotal < table[i][denomLength] || j == 0)
                {
                    for(int k = 0; k < denomLength + 1; ++k)
                    {
                        table[i][k] = table[next][k];
                    }
                    // increment the denomination just found and total
                    ++table[i][j];
                    ++table[i][denomLength];
                }
            }
        }
    }

    return table[n][denomLength];
}

int MakeChange::recursiveMemo(int n, int d, bool dRecur)
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
            return table[n][denomLength];
        }

        // recurse to the next denomination
        recursiveMemo(n, d - 1, true);
        // subtract the current denomination off n
        int next = n - denom[d];

        // the denomination is larger than the next number
        if(next < 0)
        {
            return table[n][denomLength];
        }

        // find the total using this denomination and a lookup
        int tempTotal = table[next][denomLength];

        // store in table if this total is less than the total currently
        // stored in the table
        if(tempTotal < table[n][denomLength] || d == 0)
        {
            for(int k = 0; k < denomLength + 1; ++k)
            {
                table[n][k] = table[next][k];
            }
            // increment the denomination just found and total
            ++table[n][d];
            ++table[n][denomLength];
        }
    }

    return table[n][denomLength];
}

int MakeChange::recursiveNoMemo(int n, int d, bool dRecur)
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
            return table[n][denomLength];
        }

        // recurse to the next denomination
        recursiveNoMemo(n, d - 1, true);
        // subtract the current denomination off n
        int next = n - denom[d];

        // the denomination is larger than the next number
        if(next < 0)
        {
            return table[n][denomLength];
        }

        // find the optimal total down from this point
        int tempTotal = recursiveNoMemo(next, denomLength - 1, false);

        // store in table if this total is less than the total
        // currently stored in the table
        if(tempTotal < table[n][denomLength] || d == 0)
        {
            for(int k = 0; k < denomLength + 1; ++k)
            {
                table[n][k] = table[next][k];
            }
            // increment the denomination just found and total
            ++table[n][d];
            ++table[n][denomLength];
        }
    }

    return table[n][denomLength];
}

std::string MakeChange::numberFormat(int number)
{
    std::stringstream ss;
    ss << number;
    std::string strNum = ss.str();

    // iterate down the string, adding commas
    for(int i = strNum.length() - 3; i > 0; i -= 3)
    {
        strNum.insert(i, ",");
    }

    return strNum;
}

void MakeChange::output(long totalTime, bool last)
{
    bool firstPrint = false;
    std::cout << "---" << std::endl;
    std::cout << numberFormat(tableLength - 1) << " cents = ";

    // print out each denomination and how many of each coin was used
    for(int i = denomLength - 1; i >= 0; --i)
    {
        if(table[tableLength - 1][i] > 0)
        {
            firstPrint = true;
            std::cout << denom[i] << ":" << table[tableLength - 1][i] << " ";
        }
    }

    // validate that at least some denomination was output
    if(firstPrint)
    {
        std::cout << std::endl;
    }
    else if(tableLength == 1)
    {
        std::cout << "No coins are used for the zero case." << std::endl;
    }
    else
    {
        std::cout << "Error: No solution was found. Ensure you have pennies as "
            << "a minimum denomination." << std::endl;
    }

    // output the total time used
    std::cout << "Total Time: " << numberFormat(totalTime * 1000) << " ns" << std::endl;

    // output the footer if the last item in a set
    if(last)
    {
        std::cout << "---" << std::endl;
    }
}

int MakeChange::run()
{
    // instantiate the input reader and the first line
    std::ifstream input(inputName.c_str());
    std::string start;

    if (!input.is_open())
    {
        std::cout << "--Error: File Not Found--" << std::endl;
        std::cout << "The file " << inputName << " could not be found. ";
        std::cout << "If this is not the correct filename,\nyou may change ";
        std::cout << "the name by changing the String variable inputName.\n";
    }

    // continue grabbing problem sets until the end of the file
    while(std::getline(input, start))
    {
        std::cout << "--Problem Set #" << setNum << "--" << std::endl;
        bool last = false;

        // parse the problem set from the file
        int retVal = parseInputFile(input, start);
        if(retVal == -1)
        {
            std::cout << "--Error: Invalid Number Format--" << std::endl;
            std::cout << "The file specified, " << inputName << ", contains ";
            std::cout << "elements that are not positive integers.\nThe ";
            std::cout << "parseInputFile method only knows how to parse files ";
            std::cout << "containing positive integer\nvalues that can be ";
            std::cout << "manipulated mathematically.\nThis may occur if the ";
            std::cout << "file contains any elements other than integers or,\n";
            std::cout << "more specifically, if the number of expected ";
            std::cout << "denominations or values specified\nexceeds the ";
            std::cout << "number of denominations or values that follow.\n";
            return retVal;
        }
        
        // solve the problem set
        for(int i = 0; i < valuesLength ; ++i)
        {
            // set the last flag
            if(i == valuesLength - 1)
            {
                last = true;
            }

            tableLength = values[i] + 1;
            table = new int*[tableLength];
            
            if(table != NULL) 
            {
                for(int i = 0; i < tableLength; ++i)
                {
                    table[i] = new int[denomLength + 1];
                    
                    // zero table
                    for(int j = 0; j <= denomLength; ++j)
                    {
                        table[i][j] = 0;
                    }
                }
            }

            long startTime = clock();
            bottomUp(values[i]);
            //recursiveMemo(values[i], sizeof(denom) - 1, false);
            //recursiveNoMemo(values[i], sizeof(denom) - 1, false);
            long endTime = clock();
            long totalTime = endTime - startTime;
            output(totalTime, last);
        }

        ++setNum;
        std::cout << std::endl;
    }

    // close our connection ot the file
    input.close();

    return 0;
}

/**
 * Responsible for executing the application, then immedietly calls the
 * run() function in the MakeChange class.
 *
 * @param argc The number of command-line arguments at argv.
 * @param argv A pointer to the array of command-line arguments.
 */
int main(int argc, char** argv)
{
    MakeChange makeChange;
    return makeChange.run();
}

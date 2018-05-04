#!/usr/bin/env python

# system modules
import locale, os, sys, time
# set the locale
locale.setlocale(locale.LC_ALL, '')

## Parsing input from a file, this program will solve numerous problem sets for
# the minimal coin usage given a particular set of denominations. The input
# can have multiple problem sets, each parsed line by line. The first line
# must contain the number of denominations to be used, and it should be
# followed by that number of integers (each on a new line).
# 
# Following that, an integer should be given that is the number of values that
# need to be solved. That number of integer values should then given. This
# procedure can be repeated then multiple times.
#
# Double spacing should never be used, one value per line, all values must be
# in integer form.
#
# @author Alex Laird
# @file MakeChange.py
# @version 0.1
class MakeChange:
    ## Instantiate variables for the class and call the main method.
    #
    # @param self A pointer to the local class.
    def __init__(self):
        ## The file to be used for input.
        self.inputName = "input.txt"
        
        ## The array to store all denominations in a problem set.
        self.denom = []
        ## The array to store all the values in a problem set.
        self.values = []
        ## The table to store the solutions. This table is used for lookup, if
        # a method implementing lookup is being used, otherwise it is only used
        # for storage for lookup in the output.
        self.table = []
        
        self.main()
    
    ## Parses the input file. Stores the something number of denominations in
    # the denom array and k number of values in the first dimension of the
    # two-dimensional values array.
    #
    # @param self A pointer to the local class.
    # @param inputFile The input file.
    # @param start The first line read from the set of data.
    def parseInputFile(self, inputFile, start):
        # retrieve the denominations and store them in an array
        n = int(start)
        if(n < 0):
            return -1
        self.denom = [0 for x in range(n)]
        
        lines = []
        for line in inputFile:
            lines.append(line.rstrip('\r\n'))
        
        # retrieve the denominations and store them in an array
        for i in range(0, n):
            self.denom[i] = int(lines[i])
            if self.denom[i] <= 0:
                return -1
        
        # retrieve the values and store them in an array
        k = int(lines[n])
        if k < 0:
            return -1
        self.values = [0 for x in range(k)]
        for i in range(0, k):
            self.values[i] = int(lines[n + i + 1])
            if self.values[i] < 0:
                return -1
        
        return 0
            
    ## Execute the bottom-up algorithm to find the optimal solution of
    # coins using the denominations specified.
    #
    # @param self A pointer to the local class.
    # @param n The number of cents to be divide among the denominations.
    # @return The total number of coins used to solve n optimally. 
    def bottomUp(self, n):
        # solve each coin problem from 1 to n
        for i in range(1, n + 1):
            #solve each denomination for i
            for j in range(0, len(self.denom)):
                # declare the temp total for comparison to our table lookup
                tempTotal = None
                # subtract the current denomination off i
                next = i - self.denom[j]
                
                # the next denomination is larger than the current number
                if next < 0:
                    break
                if next >= 0:
                    # find the total using this denomination
                    tempTotal = self.table[next][len(self.table[next]) - 1]
                    
                    # store in table if this total is less than the total
                    # currently stored in the table
                    if tempTotal < self.table[i][len(self.denom)] or j == 0:
                        for k in range(0, len(self.denom) + 1):
                            self.table[i][k] = self.table[next][k]
                        
                        # increment the denomination just found and total
                        self.table[i][j] += 1
                        self.table[i][len(self.denom)] += 1
        
        return self.table[n][len(self.denom)]
    
    ## Execute the resursive algorithm with memoization to find the optimal
    # solution of coins using the denominations specified.
    #
    # @param self A pointer to the local class.
    # @param n The number of cents to be divide among the denominations.
    # @param d The index of the current denomination.
    # @param dRecur True if recursing through the denominations, False if
    # recursing through the values.
    # @return The total number of coins used to solve n optimally. 
    def recursiveMemo(self, n, d, dRecur):
        # recurse up once zero is reached to begin building the table
        if n < 0:
            return 0

        # skip if recursing over through denominations
        if not dRecur:
            # recurse to the next value down the list
            self.recursiveMemo(n - 1, d, False)

        # at a valid value that needs to be solved and/or looked up
        if n > 0:
            # break out of the recursion once last denomination is reached
            if d < 0:
                return self.table[n][len(self.denom)]

            # recurse to the next denomination
            self.recursiveMemo(n, d - 1, True)
            # subtract the current denomination off n
            next = n - self.denom[d]

            # the denomination is larger than the next number
            if next < 0:
                return self.table[n][len(self.denom)]
            
            # find the total using this denomination and a lookup
            tempTotal = self.table[next][len(self.denom)]

            # store in table if this total is less than the total currently
            # stored in the table
            if tempTotal < self.table[n][len(self.denom)] or d == 0:
                for k in range(0, len(self.denom) + 1):
                    self.table[n][k] = self.table[next][k]
                # increment the denomination just found and total
                self.table[n][d] += 1
                self.table[n][len(self.denom)] += 1

        return self.table[n][len(self.denom)]
    
    ## Execute the recursive algorithm without memoization to find the optimal
    # solution of coins using the denominations specified. This is horribly
    # inefficient in any situation and becomes unbearable much beyond a value
    # of twenty and unbearable with values of triple digits and beyond.
    #
    # The only line that is different within this method is the line that
    # declares and sets tempTotal; instead of doing a table lookup, it
    # performs a recursion to solve the next number down.
    #
    # @param self A pointer to the local class.
    # @param n The number of cents to be divide among the denominations.
    # @param d The index of the current denomination.
    # @param dRecur True if recursing through the denominations, False if
    # recursing through the values.
    # @return The total number of coins used to solve n optimally.
    def recursiveNoMemo(self, n, d, dRecur):
        # recurse up once zero is reached to begin building the table
        if n < 0:
            return 0
            
        # skip if recursing over through denominations
        if not dRecur:
            # recurse to the next value down the list
            self.recursiveNoMemo(n - 1, d, False)

        # at a valid value that needs to be solved and/or looked up
        if n > 0:
            # break out of the recursion once last denomination is reached
            if d < 0:
                return self.table[n][len(self.denom)]

            # recurse to the next denomination
            self.recursiveNoMemo(n, d - 1, True)
            # subtract the current denomination off n
            next = n - self.denom[d]

            # the denomination is larger than the next number
            if next < 0:
                return self.table[n][len(self.denom)]
            
            # find the optimal total down from this point
            tempTotal = self.recursiveNoMemo(next, len(self.denom) - 1, False)

            # store in table if this total is less than the total currently
            # stored in the table
            if tempTotal < self.table[n][len(self.denom)] or d == 0:
                for k in range(0, len(self.denom) + 1):
                    self.table[n][k] = self.table[next][k]
                # increment the denomination just found and total
                self.table[n][d] += 1
                self.table[n][len(self.denom)] += 1

        return self.table[n][len(self.denom)]
    
    ## Displays the results to the output stream, listing the denominations
    # from biggest to smallest.
    #
    # @param self A pointer to the local class.
    # @param totalTime The total time for the problem.
    # @param last True if this is the last problem in the set, False otherwise. 
    def output(self, totalTime, last):
        firstPrint = False
        print '---'
        print str(locale.format("%.*f", (0, len(self.table) - 1), True)) + ' cents = ',
        
        # print out each denomination and how many of each was used
        i = len(self.denom) - 1
        while i >= 0:
            if self.table[len(self.table) - 1][i] > 0:
                firstPrint = True
                print str(self.denom[i]) + ':' + str(self.table[len(self.table) - 1][i]) + ' ',
            i -= 1
        
        # validate that at least some denomination was output
        if firstPrint:
            print ''
        elif len(self.table) == 1:
            print 'No coins are used for the zero case.'
        else:
            print 'Error: No solution was found. Ensure you have pennies as ' \
                'a minimum denomination.'

        # output the total time used
        print 'Total Time: ' + str(locale.format("%.*f", (0, totalTime * 1000000000), True)) + ' ns'

        # output the footer if the last item in a set
        if last:
            print '---'
    
    ## Calls the execution methods.
    #
    # @param self A pointer to the local class.
    def main(self):
        try:
            # instantiate the file
            if not os.path.exists(self.inputName):
                print '--Error: File Not Found--\n'
                print 'The file ' + inputName + ' could not be found. '
                print 'If this is not the correct filename,\nyou may change '
                print 'the name by changing the String variable inputName.\n'
                return -1
            inputFile = open(self.inputName, 'r')
            setNum = 1
            
            # continue grabbing problem sets until the end of the file
            for start in inputFile:
                print '--Problem Set #' + str(setNum) + '--'
                last = False
                
                # parse the problem set from the file
                retVal = self.parseInputFile(inputFile, start)
                if retVal == -1:
                    print '--Error: Invalid Number Format--\n'
                    print 'The file specified, ' + inputName + ', contains '
                    print 'elements that are not positive integers.\nThe '
                    print 'parseInputFile method only knows how to parse files '
                    print 'containing positive integer\nvalues that can be '
                    print 'manipulated mathematically.\nThis may occur if the '
                    print 'file contains any elements other than integers or,\n'
                    print 'more specifically, if the number of expected '
                    print 'denominations or values specified\nexceeds the '
                    print 'number of denominations or values that follow.\n'
                    return retVal
                    
                # solve the problem set
                for i in range(0, len(self.values)):
                    # set the last flag
                    if i == len(self.values) - 1:
                        last = True
                    
                    # instantiate table
                    self.table = []
                    for j in range(0, self.values[i] + 1):
                        self.table.append([0 for x in range(len(self.denom) + 1)])
                    startTime = time.time()
                    self.bottomUp(self.values[i])
                    #self.recursiveMemo(self.values[i], len(self.denom) - 1, False)
                    #self.recursiveNoMemo(self.values[i], len(self.denom) - 1, False)
                    endTime = time.time()
                    totalTime = endTime - startTime
                    self.output(totalTime, last)
                
                setNum += 1
                print ''
            
            # close the connection to the file
            inputFile.close()
            
            return 0
        except:
            print 'Oops!  Some unknown error occured on the value ' \
                + str(self.values[i]) + '.'

## This method calls the main method and gracefully terminates upon completion
if __name__ == "__main__":
    makeChange = MakeChange()
    sys.exit(0)

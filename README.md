# Three Digits Puzzle

In this assignment, I implemented a number of search algorithms (BFS, DFS, IDS, Greedy, A* and Hill‐climbing) to solve the 3‐digit puzzle.

Given are two 3‐digit numbers called 􏰀 (start) and 􏰁 (goal) and also a set of 3‐digit numbers called 􏰂􏰃􏰄􏰅􏰆􏰇􏰇􏰈􏰉. To solve the puzzle, we want to get from 􏰀 to 􏰁 in the smallest number of moves. A move is a transformation of one number into another number by adding or subtracting 1 to one of its digits. For example, a move can take you from 123 to 124 by adding 1 to the last digit or from 953 to 853 by subtracting 1 from the first digit. Moves must satisfy the following constraints:
1. You cannot add to the digit 9 or subtract from the digit 0;
2. You cannot make a move that transforms the current number into one of the forbidden
numbers;
3. You cannot change the same digit twice in two successive moves.


### Compile the script
'''
javac ThreeDigits.java Node.java
'''

### Run the script
'''
java ThreeDigits A puzzle.txt
'''
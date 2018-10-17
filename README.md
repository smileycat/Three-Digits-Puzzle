# Three Digits Puzzle

In this assignment, I implemented a number of search algorithms (BFS, DFS, IDS, Greedy, A* and Hill‐climbing) to solve the 3‐digit puzzle.

Given are two 3‐digit numbers called 􏰀*S*(start) and 􏰁*G*(goal) and also a set of 3‐digit numbers called 􏰂􏰃􏰄􏰅􏰆􏰇􏰇􏰈􏰉. To solve the puzzle, we want to get from 􏰀 to 􏰁 in the smallest number of moves. A move is a transformation of one number into another number by adding or subtracting 1 to one of its digits. For example, a move can take you from 123 to 124 by adding 1 to the last digit or from 953 to 853 by subtracting 1 from the first digit. Moves must satisfy the following constraints:
1. You cannot add to the digit 9 or subtract from the digit 0;
2. You cannot make a move that transforms the current number into one of the forbidden
numbers;
3. You cannot change the same digit twice in two successive moves.


### Compile the script
```
javac ThreeDigits.java Node.java
```

### Run the script
```
java ThreeDigits [search algorithm] [input filename]

Example:
java ThreeDigits A puzzle.txt
```

Search algoirthms:
* A = A*
* B = BFS
* D = DFS
* G = Greedy
* H = Hill Climbing
* I = IDS

Input file example:

320
110

### Result
There will be two lines. The first line will contain the solution path found from the start node to the goal node.
The second line will contain the order of nodes expanded during the search process.

Example:
```
320,220,210,110
320,220,420,310,330,321,210,230,221,410,430,421,210,410,
311,230,430,331,221,421,311,331,110
```
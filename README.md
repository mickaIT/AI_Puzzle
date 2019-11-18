# 15Puzzle
AI project in java.
Program solves a popular 15 Puzzle Game. It uses various searching algorithms to find the solution.
3 sizes of board are available. Goal patterns are set to:
0    1
2    3
for 3 puzzles;
0    1   2
3    4   5
6    7   8
for 8 puzzles;
0    1   2   3
4    5   6   7
8    9   10  11
12   13  14  15
for 15 puzzles;

6 searching algorithms are available:
-    Breadth-First Search
-    Depth-First Search
-    Recursive Depth-First Search
-    Iterative Deepening Depth-First Search
-    A*
-    Best-First Search

The program takes up to four parameters:
1. Puzzle size  -   number (3, 8, 15)
2. Search type  -   String (bfs | dfs | rdfs | idfs | ass | bst)
3. Input file name - String (name of the file located in project's dir, without ".txt")
4. Only for ass & bst! - number (1 or 2)
     1   -   Simple heuristic function that calculates how many puzzles are in a wrong position
     2   -   Manhattan Distance function, which calculates the value based on how far the tiles are from their
             positions
Examples of invocation:
8 bfs 8_2
15 ass 15_1 2
8  bst 8_1 1

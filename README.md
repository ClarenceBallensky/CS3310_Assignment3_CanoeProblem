# Canoe Problem
Canoes are available for rent at a sequence of n trading posts along the river, numbered 0, 1, . . . , n − 1. The trip begins at post 0 and ends at post n − 1. However, individuals do not have to keep the same canoe for the entire trip; one can stop at any post, drop off the canoe used to reach that post, and rent another canoe. One may make as many stops on the river as desired. There is no added charge for exchanging canoes at a post. Travel down the river is one-way (downstream). For all pairs (a, b) with a < b, the cost of renting a canoe at post a and dropping it off at post b is given by a two-dimensional array C[a, b]. 

Using a dynamic programming algorithm, my program then computes the optimal costs of traveling between any two posts (i, j) where i < j. The goal is to determine the optimal cost for (0, n − 1). After performing that calculation, my program prints the optimal cost matrix: i.e. the optimal cost between any two posts (i, j) for all values 0 ≤ i < j ≤ n − 1. Additionally, my program prints the actual sequence of rentals to be used for the route between posts 0 and n − 1 (not just its optimal cost).

## Input File Specifications 
The first line of the input file must contain a positive integer n, giving the number of posts along the river. The remaining n − 1 lines of the file must contain the positive integers of the cost matrix, delimited by white space, omitting unnecessary entries. 

A sample cost matrix is shown below (with n = 4):

<img width="209" height="158" alt="image" src="https://github.com/user-attachments/assets/9307ff20-95c3-4cc4-a452-3c01417eef5d" />

This cost matrix would be represented in an input text file as: 
```
4
  10 15 50
  40 20
  35
```
## Output
This is the result of running my program with the above text file:

<img width="271" height="254" alt="image" src="https://github.com/user-attachments/assets/368d7b21-2462-44cf-8d38-5b508a53f3c6" />

## How to Run
1. Clone this GitHub repository
2. Open up the command prompt terminal on your machine
3. Use the change directory command "cd" and navigate to CS3310_Assignment3_CanoeProblem > src > main > java
4. Compile the program using the "javac" command
5. Run the program using the "java" command, including the full path to your text file as a command line argument

Example:
<img width="1598" height="175" alt="image" src="https://github.com/user-attachments/assets/400b05af-3059-4831-89ca-5a11699864ff" />




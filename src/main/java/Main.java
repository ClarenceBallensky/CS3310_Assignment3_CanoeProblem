/**
 * Clarence Ballensky
 * CS 3310.01, Fall 2025
 * Programming Assignment 3: Canoe Problem
 * Main class:
 *      - processes text file input to construct a cost matrix
 *      - computes the minimum cost to travel from point i to j
 *      - reconstructs the actual path used
 *      - print results
 */

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;


public class Main {
    public static void main (String[] args) {


        //makes sure that the filename is the only command line argument
        if (args.length != 1) {
            throw new IllegalArgumentException("Please ONLY provide a file name as a command line argument.");
        }

        String fileName = args[0];

        MatrixData data = readCostMatrix(fileName);

        int[][] costMatrix = data.costMatrix;
        int numberTradingPosts = data.numberTradingPosts;

        int[][] optimalMatrix = new int[numberTradingPosts][numberTradingPosts];
        int[][] nextMatrix = new int[numberTradingPosts][numberTradingPosts];

        computeOptimalCosts(costMatrix, optimalMatrix, nextMatrix, numberTradingPosts);

        printOptimalCostMatrix(optimalMatrix, numberTradingPosts);

        int startPost = 0; //route starts at post 0
        //if there are 4 posts, the posts are numbered 0, 1, 2, 3--ending at post 3
        int endPost = numberTradingPosts - 1;

        ArrayList<Integer> routeList = reconstructRoute(startPost, endPost, nextMatrix);
        printRoute(routeList);

    }

    /**
     * Method: readCostMatrix
     * Purpose: read the cost values from a text input file and store it in a
     *          2D integer matrix
     * @param fileName first command line argument
     * @return a 2D integer array storing the cost to travel between posts AND an integer storing the number of trading
     * posts; made possible by the MatrixData helper class
     */
    public static MatrixData readCostMatrix(String fileName)
    {
        File inputFile = new File(fileName); //opens up the file specified in the command line

        //read the contents of the file
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

            //get the number of trading posts from the first line
            String firstLine = br.readLine();
            int numberTradingPosts = Integer.parseInt(firstLine.trim());

            //create a matrix to hold the values from the input file
            int[][] costMatrix = new int[numberTradingPosts][numberTradingPosts];

            String line;
            int row = 0; //start at row 0

            while ((line = br.readLine()) != null && row < numberTradingPosts - 1) {

                String[] parts = line.trim().split("\\s+");

                for (int k = 0; k < parts.length; k++){
                    int cost = Integer.parseInt(parts[k]);

                    int col = row + k + 1; //start at column 1
                    costMatrix[row][col] = cost;
                }

                row++;
            }

            return new MatrixData(costMatrix, numberTradingPosts);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error: File not found --> " + fileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    /**
     * Method: computeOptimalCosts
     * Purpose: use dynamic programming to compute the optimal canoe route, with the optimal route defined as the
     *          cheapest route
     * @param costMatrix cost of traveling from any post to any other post (user-provided)
     * @param optimalMatrix stores the minimum cost from any post to any other post
     * @param nextMatrix stores the sequence of canoe rentals in the optimal order
     * @param numberTradingPosts the number of trading posts on the user-provided canoe route
     */
    public static void computeOptimalCosts(int[][] costMatrix, int[][] optimalMatrix, int[][] nextMatrix,
                                           int numberTradingPosts)
    {
        //initialize optimalMatrix with infinity values, and nextMatrix with -1 values
        for (int i = 0; i < numberTradingPosts; i++) {
            for (int j = 0; j < numberTradingPosts; j++) {
                optimalMatrix[i][j] = Integer.MAX_VALUE;
                nextMatrix[i][j] = -1;
            }
        }

        //set the cost of travelling from a post to itself to 0
        for (int i = 0; i < numberTradingPosts; i++) {
            optimalMatrix[i][i] = 0;
        }

        /*
         * Length is the difference between the end post and the start post
         *         for example,
         *                  when length = 1, compute optimalMatrix[0][1], optimalMatrix[1][2], optimalMatrix[2][3], ..
         *                  when length = 2, compute optimalMatrix[0][2], optimalMatrix[1][3], optimalMatrix[2][4], ...
         *
         * This ensures that all smaller subproblems have already been computed.
         */
        for (int length = 1; length < numberTradingPosts; length++) {
            for (int startPost = 0; startPost + length < numberTradingPosts; startPost++) {

                int endPost = startPost + length; //endPost is length distance away from startPost

                //start by assuming the best solution is a direct rental from the startPost to the endPost
                int bestCost = costMatrix[startPost][endPost]; //best cost for travelling between two posts
                int bestNext = endPost; //bestNext is the next post you should go to after startPost to minimize cost

                //try all intermediate stops where startPost < intermediatePost < endPost
                for (int intermediatePost = startPost + 1; intermediatePost < endPost; intermediatePost++) {
                    //cost to rent a canoe from startPost to intermediatePost + optimal cost from k to j
                    //handles any other necessary intermediate stops recursively
                    int cost = costMatrix[startPost][intermediatePost] + optimalMatrix[intermediatePost][endPost];

                    //if stopping at the intermediatePost yields a cheaper route (this is desirable),
                    //update bestCost and bestNext
                    if (cost < bestCost) {
                        bestCost = cost;
                        bestNext = intermediatePost;
                    }
                }

                //store the results
                optimalMatrix[startPost][endPost] = bestCost; //best minimal cost from startPost to stopPost
                nextMatrix[startPost][endPost] = bestNext; //first stop after startPost on the optimal route
            }
        }
    }

    /**
     * takes the nextMatrix and turns it into the actual route by following the chain of best next-hop decisions
     * @param startPost first post in the route
     * @param endPost last post in the route
     * @param nextMatrix for every combination of posts, it stores the first stop after one post on the optimal route
     *                   to another post
     * @return an ArrayList representing the route
     */
    public static ArrayList<Integer> reconstructRoute(int startPost, int endPost, int[][] nextMatrix) {
        ArrayList<Integer> routeList = new ArrayList<Integer>();
        routeList.add(startPost);

        while (startPost != endPost) {
            int nextStop =  nextMatrix[startPost][endPost];
            routeList.add(nextStop);
            startPost = nextStop; //increments the loop
        }

        return routeList;
    }

    /**
     * Method: printOptimalCostMatrix
     * Purpose: print the lowest cost for each transition from a given post to any other given post
     * @param optimalMatrix stores the minimum cost from any post to any other post
     * @param numberTradingPosts the number of trading posts on the user-provided canoe route
     */
    public static void printOptimalCostMatrix(int[][] optimalMatrix, int numberTradingPosts) {
        System.out.println("\nOptimal cost matrix:");
        System.out.print("      ");
        ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                                                      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                                                                      'u', 'v', 'w', 'x', 'y', 'z'));
        for (int currLetter = 0; currLetter < numberTradingPosts; currLetter++) {
            System.out.printf("%4c ", alphabet.get(currLetter));
        }

        System.out.println();
        System.out.print("      ");

        for (int currLetter = 0; currLetter < numberTradingPosts; currLetter++) {
            System.out.print("-----");
        }

        System.out.println();

        for (int  i = 0; i < numberTradingPosts; i++) {
            System.out.printf("%4c |", alphabet.get(i));
            for (int j = 0; j < numberTradingPosts; j++) {
                if (i < j) {
                    System.out.printf("%4d ", optimalMatrix[i][j]);
                }
                else {
                    System.out.printf("%4s ", "__");
                }
            }
            System.out.println("\n");
        }
    }

    /**
     * Method: printRoute
     * Purpose: print the optimal route (lowest-cost route) in the correct order
     * @param routeList an ArrayList representing the route
     */
    public static void printRoute(ArrayList<Integer> routeList) {
        System.out.print("\nOptimal route: ");
        System.out.print(routeList.getFirst());
        for (int post = 1; post < routeList.size(); post++) {
            System.out.print(" -> " + routeList.get(post));
        }

        System.out.println();
    }
}
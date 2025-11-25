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

package edu.cpp;

import java.io.*;
import java.util.*;


public class Main {
    public static void main (String[] args) {


        //makes sure that the filename is the only command line argument
        if (args.length != 1) {
            throw new IllegalArgumentException("Please ONLY provide a file name as a command line argument.");
        }

        String fileName = args[0];

        readCostMatrix(fileName);

    }

    /**
     * Method: readCostMatrix
     * Purpose: read the cost values from a text input file and store it in a
     *          2D integer matrix
     * @param fileName first command line argument
     * @return a 2D integer array storing the cost to travel between posts
     */
    public static int[][] readCostMatrix(String fileName)
    {
        File inputFile = new File(fileName); //opens up the file specified in the command line

        int lineCount = 0; //keep track of which line of the i

        //read the contents of the file
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

            //get the number of trading posts from the first line
            String firstLine = br.readLine();
            int numberTradingPosts = Integer.parseInt(firstLine.trim());

            //create a matrix to hold the values from the input file
            int[][] costMatrix = new int[numberTradingPosts][numberTradingPosts];

            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < numberTradingPosts - 1) {

                String[] parts = line.trim().split("\\s+");

                for (int k = 0; k < parts.length; k++){ //start at row 0
                    int cost = Integer.parseInt(parts[k]);

                    int col = row + k + 1; //start at column 1
                    costMatrix[row][col] = cost;
                }

                row++;
            }

            return costMatrix;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found --> " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file --> " + e.getMessage());
        }

        return null; //in event of an error
    }

    /**
     * Method: computeOptimalCosts
     * Purpose: use dynamic programming to compute the optimal canoe route, with the optimal route defined as the
     *          cheapest route
     * @param costMatrix cost of traveling from any post to any other post
     * @param optimalMatrix stores the minimum cost from any post to any other post
     * @param nextMatrix stores the sequence of canoe rentals in the optimal order
     * @param numberTradingPosts the number of trading posts on the user-provided canoe route
     */
    public static void computeOptimalCosts(int[][] costMatrix, int[][] optimalMatrix, int[][] nextMatrix,
                                           int numberTradingPosts)
    {
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
        for (int length = 1; length < numberTradingPosts - 1; length++) {
            for (int startPost = 0; startPost < numberTradingPosts - length - 1; startPost++) {
                int endPost = startPost + length; //endPost is length distance away from startPost

                //start by assuming the best solution is a direct rental from the startPost to the endPost
                int bestCost =  costMatrix[startPost][endPost]; //best cost for travelling between two posts
                int bestNext = endPost; //bestNext is the next post you should go to after startPost to minimize cost

                //try all intermediate stops where startPost < intermediatePost < endPost
                for (int intermediatePost = startPost + 1; intermediatePost < endPost -1; intermediatePost++) {
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
}
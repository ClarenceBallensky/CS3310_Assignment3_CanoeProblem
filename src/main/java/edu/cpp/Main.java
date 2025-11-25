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
}
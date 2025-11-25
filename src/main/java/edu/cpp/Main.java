package edu.cpp;

import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main (String[] args) {


        //makes sure that the filename is the only command line argument
        if (args.length != 1) {
            throw new IllegalArgumentException("Please ONLY provide a file name as a command line argument.");
        }

        File inputFile = new File(args[0]); //opens up the file specified in the command line

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
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found --> " + args[0]);
        } catch (IOException e) {
            System.out.println("Error reading file --> " + e.getMessage());
        }


    }
}
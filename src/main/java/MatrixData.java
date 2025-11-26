/**
 * Clarence Ballensky
 * CS 3310.01, Fall 2025
 * Programming Assignment 3: Canoe Problem
 * MatrixData class: helper class for returning the cost matrix and the number of trading posts realized by the
 *                   readCostMatrix(fileName) method
 */

public class MatrixData {
    public int[][] costMatrix;
    public int numberTradingPosts;

    public MatrixData(int[][] costMatrix, int numberTradingPosts) {
        this.costMatrix = costMatrix;
        this.numberTradingPosts = numberTradingPosts;
    }

}


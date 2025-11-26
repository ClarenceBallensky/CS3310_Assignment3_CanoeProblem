# Analysis
## 1. Recursive Formula for Optimal Cost
costMatrix[i][j] = cost of renting a canoe directly from post i to post j

optimalMatrix[i][j] = minimum possible cost of travelling from post i to post j

If i = j, no travel is needed.

If i > j, no travel is needed because we cannot travel upstream. 

If i < j, we must choose whether to go to post j directly, or stop at some intermediate post k.
Every valid route from post i to post j must either:
  1) Travel directly from post i to post j, or
  2) Travel from post i to an intermediate post k, then travel from post k to post j.

The recursive formula is:

<img width="641" height="58" alt="image" src="https://github.com/user-attachments/assets/a9d51dbe-2fe7-4da9-9866-143f205702d2" />

nextMatrix[i][j] records which k produced the minimum cost, allowing us to later reconstruct the optimal route. 

## 2. Theoretical Runtime Analysis 
My program computs optimal costs using the following three for-loops:
```
for (int length = 1; length < numberTradingPosts; length++) {            // Loop A
    for (int startPost = 0; startPost + length < numberTradingPosts; startPost++) {   // Loop B
        int endPost = startPost + length;

        for (int intermediatePost = startPost + 1; intermediatePost < endPost; intermediatePost++) {   // Loop C
            ...
        }
    }
}
```
Loop A has an O(n) time complexity, Loop B has an up to O(n) time complexity, and Loop C has an up to O(n) time complexity.

Combined, the loops produce a theoretical runtime of O(n<sup>3</sup>).



# Analysis
## Recursive Formula for Optimal Cost
costMatrix[i][j] = cost of renting a canoe directly from post i to post j

optimalMatrix[i][j] = minimum possible cost of travelling from post i to post j

If i = j, no travel is needed.

If i > j, no travel is needed because we cannot travel upstream. 

If i < j, we must choose whether to go directly, or stop at some intermediate post k.
Every valid route from post i to post j must either:
  1) Travel directly from post i to post j, or
  2) Travel from post i to an intermediate post k, then travel from post k to post j.

The recursive formula is:

<img width="641" height="58" alt="image" src="https://github.com/user-attachments/assets/a9d51dbe-2fe7-4da9-9866-143f205702d2" />

nextMatrix[i][j] records which k produced the minimum cost, allowing us to later reconstruct the optimal route. 

## Theoretical Runtime Analysis 



# Analysis
costMatrix[i][j] = cost of renting a canoe directly from post i to post j

optimalMatrix[i][j] = minimum possible cost of travelling from post i to post j

If i = j, no travel is needed.

If i > j, no travel is needed because we cannot travel upstream. 

If i < j, we must choose whether to go directly, or stop at some intermediate post k.
Every valid route from post i to post j must either:
  1) Travel directly from post i to post j, or
  2) Travel from post i to an intermediate post k, then travel from post k to post j.

The recursive formula is:

<img width="594" height="59" alt="image" src="https://github.com/user-attachments/assets/356d5309-85e1-4e0d-8b37-8bd541cafaa8" />

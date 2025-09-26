## Assignment 1

* **Author:** Tulegenova Karina  
* **Group:** SE-2419


## Overview

**Algorithms Used:**  
MergeSort, QuickSort, Select, and Closest Pair.

**Technologies:**  
Java 17, Maven, JUnit5.

**Tracked Metrics:**  
Execution time, recursion depth, number of comparisons, swaps/writes, and memory allocations.


## Project Structure

<img width="398" height="664" alt="image" src="https://github.com/user-attachments/assets/9ccb1902-9fa8-4532-be9f-0efebc150c9c" />


## How to Build & Run

mvn -q clean test


## CLI: Run Algorithms and Export Metrics to CSV

<img width="1116" height="99" alt="image" src="https://github.com/user-attachments/assets/90eb5358-9248-4ef4-9474-efd4109a21e3" />


## CSV Files

Execution results for each algorithm are exported as CSV files and include performance metrics per input size:

<img width="559" height="812" alt="image" src="https://github.com/user-attachments/assets/74e46bf5-788d-4c72-be10-c584175715b4" />

<img width="790" height="811" alt="image" src="https://github.com/user-attachments/assets/dfd0036a-fb6d-4fbf-97cf-232c3c86762f" />

<img width="1364" height="804" alt="image" src="https://github.com/user-attachments/assets/37402099-6015-45da-b617-29b823fb23ab" />

<img width="800" height="786" alt="image" src="https://github.com/user-attachments/assets/ba63d6b7-b1e0-46fb-b2eb-4f574f88f1d4" />


## Recurrence Analysis

Performance results align closely with expected theoretical complexity:

- **MergeSort** and **Closest Pair** grow at the expected rate of Θ(n log n), including in metrics like comparisons and recursion depth. MergeSort also shows high write counts due to buffer merges.
- **QuickSort** follows Θ(n log n) for average case, with controlled recursion depth thanks to tail-recursion into the larger half.
- **Select** (MoM5) scales linearly (Θ(n)) in both comparisons and depth, consistent with Akra-Bazzi bounds.
- Depth grows logarithmically across all recursive algorithms, while swaps and writes scale proportionally to input size.

<img width="679" height="415" alt="image" src="https://github.com/user-attachments/assets/67734c1f-469f-46d8-b5bc-87587e23cd49" />

<img width="1453" height="715" alt="image" src="https://github.com/user-attachments/assets/fcb94f10-9bd7-41b3-b91c-f8a540ac2266" />

**Average case performance** comparison:

<img width="786" height="491" alt="image" src="https://github.com/user-attachments/assets/4680b937-295c-41d6-b18f-bc4183d88990" />


## Architecture Notes

- **MergeSort & QuickSort**:
  - Use a shared `Metrics` object to monitor all recursive steps.
  - MergeSort allocates a single buffer reused throughout recursion.
  - QuickSort always recurses into the smaller partition, limiting recursion depth. Insertion sort is used for small arrays.

- **Select (MoM5)**:
  - Splits the input into fixed-size groups and selects a pivot recursively.
  - Ensures worst-case linear performance by avoiding full traversal.
  - Works in-place without additional memory allocation.

- **Closest Pair**:
  - Uses sorting by x and y coordinates, with buffers reused across recursion.
  - Logarithmic recursion depth is ensured by consistent array splitting.
  - Uses brute force for small subproblems.


## Constant-Factor Effects: JVM, GC, and Cache

- For very small input sizes (e.g., n = 1), timing anomalies appear due to Java Virtual Machine warm-up, JIT (Just-In-Time) compilation, and garbage collection.
- MergeSort and Closest Pair have higher memory usage due to buffer copies and auxiliary arrays.
- QuickSort and Select use in-place processing, resulting in fewer cache misses and more consistent performance.
- MergeSort’s larger constant overhead is attributed to merging operations; Closest Pair’s comes from sorting and maintaining multiple lists.


## Summary:
  - Timing outliers at small input sizes are caused by JVM behavior, not algorithm inefficiency.
  - MergeSort writes more data due to buffer usage.
  - Select consistently outperforms others at larger scales due to its efficient in-place design.

- Overall, measurements match theory well, with system-level effects like caching and GC accounting for most deviations.

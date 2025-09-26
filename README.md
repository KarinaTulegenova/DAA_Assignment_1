## Assignment1
* Author: Tulegenova Karina
* Group: SE-2419

## Overview
**Algorithms:** MergeSort, QuickSort, Select, Closest Pair.

**Tech:** Java 17, Maven, JUnit5.

**Metrics:** time, recursion depth, comparisons, swaps/writes, allocations.

## Project structure

<img width="398" height="664" alt="image" src="https://github.com/user-attachments/assets/9ccb1902-9fa8-4532-be9f-0efebc150c9c" />


## How to build & run
mvn -q clean test

# CLI: run algorithm and dump metrics to CSV
<img width="1116" height="99" alt="image" src="https://github.com/user-attachments/assets/90eb5358-9248-4ef4-9474-efd4109a21e3" />


## CSV files.

<img width="559" height="812" alt="image" src="https://github.com/user-attachments/assets/74e46bf5-788d-4c72-be10-c584175715b4" />

<img width="790" height="811" alt="image" src="https://github.com/user-attachments/assets/dfd0036a-fb6d-4fbf-97cf-232c3c86762f" />

<img width="1364" height="804" alt="image" src="https://github.com/user-attachments/assets/37402099-6015-45da-b617-29b823fb23ab" />

<img width="800" height="786" alt="image" src="https://github.com/user-attachments/assets/ba63d6b7-b1e0-46fb-b2eb-4f574f88f1d4" />





## Algorithms & implementation notes

### 1) MergeSort — D&C, Master Case 2

* This Java implementation of MergeSort recursively sorts an array using a divide-and-conquer strategy with several optimizations. It employs a single reusable buffer to avoid repeated memory allocations during merging, and switches to insertion sort for small subarrays (cut-off threshold = 16) to improve performance. During sorting, it tracks metrics such as comparisons, writes, swaps, allocations, and recursion depth using the Metrics class. Before merging, it checks whether the array is already sorted to skip unnecessary work. The merge function performs linear merging by copying the current segment into the buffer and writing sorted elements back to the array. The optional main method demonstrates sorting a sample array and prints out the recorded metrics..

### 2) QuickSort (robust)

* This implementation of QuickSort applies a robust divide-and-conquer strategy with randomized pivot selection and safe recursion patterns. It uses the Lomuto partition scheme and places the random pivot at the end of the subarray. To reduce stack depth, the algorithm always recurses into the smaller partition and processes the larger one via tail recursion, ensuring that recursion depth remains bounded by O(log n). For small subarrays (below a cutoff threshold, default 16), it switches to insertion sort. All operations are instrumented via a Metrics object, tracking comparisons, writes, swaps, and recursion depth. The swap method avoids unnecessary operations when indices are equal, and partitionLomuto performs a linear scan to rearrange elements around the pivot.

### 3) Deterministic Select — Median‑of‑Medians (MoM5), O(n)

* This implementation of Deterministic Select (Median-of-Medians algorithm) finds the k-th smallest element in an unsorted array in linear time (O(n)), using a divide-and-conquer strategy. The array is divided into groups of 5, each group is sorted via insertion sort, and their medians are recursively used to select a good pivot (guaranteeing worst-case linear performance). The array is then partitioned into three parts: less than, equal to, and greater than the pivot. Only the relevant partition is recursively processed, and the algorithm always prefers to recurse into the smaller subrange to limit recursion depth. For small input sizes (≤12), it falls back to insertion sort directly. All operations are instrumented with a Metrics object to track comparisons, writes, swaps, allocations, and recursion depth.

### 4) Closest Pair of Points (2D), O(n log n)

* This class implements the classic **Closest Pair of Points** algorithm in 2D with an overall **O(n log n)** runtime. It first clones and sorts the input points by x‑coordinate, then runs a recursive `solve` that splits the array, solves the left and right halves, and maintains for each range an auxiliary array `py` that is kept **sorted by y**. After combining the halves (by merging their y‑sorted lists), it builds a central **strip** of points within distance `δ` from the median x (where `δ` is the best distance from the halves) and, in y‑order, checks only the next **≤7 neighbors** per point to possibly improve the best pair. Small subproblems (≤3 points) are handled by a brute‑force scan. Temporary arrays are reused to avoid extra allocations, and, if provided, a `Metrics` object records comparisons, writes, allocations, and recursion depth throughout the computation.

## Recurrence analysis 

* The results strongly align with theoretical expectations. Both MergeSort and Closest Pair show the expected Θ(n log n) growth in comparisons and recursion depth, with MergeSort also displaying high write counts due to its linear merge phase. QuickSort, with randomized pivot and smaller-first recursion, maintains Θ(n log n) comparisons and controlled recursion depth (O(log n)), as reflected in the plots. Select demonstrates near-linear scaling in comparisons and stable depth growth, consistent with the Θ(n) bound from Akra–Bazzi. Across all algorithms, depth metrics grow logarithmically, and write/swaps increase moderately with input size. Constant-factor differences are also visible: MergeSort and Closest Pair show higher absolute writes due to array copying and strip construction, while Select and QuickSort benefit from in-place operations.
<img width="679" height="415" alt="image" src="https://github.com/user-attachments/assets/67734c1f-469f-46d8-b5bc-87587e23cd49" />

<img width="1453" height="715" alt="image" src="https://github.com/user-attachments/assets/fcb94f10-9bd7-41b3-b91c-f8a540ac2266" />

* Average case:

<img width="786" height="491" alt="image" src="https://github.com/user-attachments/assets/4680b937-295c-41d6-b18f-bc4183d88990" />

### Architecture Notes

- **MergeSort & QuickSort**:
  - Both algorithms use a shared `Metrics` object passed through recursive calls to track comparisons, recursion depth, memory allocations, and writes/swaps.
  - `MergeSort` uses a single pre-allocated buffer to reduce repeated memory allocations during merging.
  - `QuickSort` always recurses into the smaller partition to keep stack depth bounded. For small arrays (size < 16), it switches to insertion sort.

- **Select (MoM5)**:
  - The deterministic select algorithm divides the array into groups of 5 and finds the median of medians to ensure linear worst-case time.
  - Only the relevant part is processed recursively, reducing unnecessary stack depth.
  - Small arrays (size ≤ 12) are handled via insertion sort. No extra memory is allocated — the algorithm is fully in-place.

- **Closest Pair 2D**:
  - Reuses temporary arrays sorted by X and Y to avoid repeated allocations.
  - Recursive divide-and-conquer algorithm with depth limited by log₂(n).
  - Uses in-place merging, and brute-force method for subproblems with ≤ 3 elements.

### Constant-Factor Effects: GC, Cache, JIT

- For small input sizes (e.g. N=1), execution times showed large outliers due to JVM warm-up, Just-In-Time (JIT) compilation, and garbage collection (GC).
- MergeSort and ClosestPair allocate more memory and perform more writes, which increases cache misses and memory traffic.
- QuickSort and Select are fully in-place and allocate minimal memory, making them more stable and cache-efficient.
- MergeSort's larger constant factors stem from copying to and from a buffer, while ClosestPair's come from cloning and sorting auxiliary arrays.


### Summary: Theory vs Measurements

- All algorithms showed empirical performance consistent with their theoretical time complexities:
  - MergeSort and ClosestPair grew with Θ(n log n),
  - Select followed a linear Θ(n) pattern,
  - QuickSort averaged around Θ(n log n) despite some variance from randomized pivoting.

- Notable observations:
  - High variance at small N due to JVM behavior (JIT, GC).
  - MergeSort had higher write counts and memory usage due to merge buffer.
  - Select performed fastest for large N, thanks to linear scaling and in-place strategy.

- Overall, the measured trends align with theoretical expectations, though constant factors and system-level effects (memory, GC, recursion stack) influenced absolute timings.





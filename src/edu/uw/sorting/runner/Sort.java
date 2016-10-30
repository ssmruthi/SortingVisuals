package edu.uw.sorting.runner;

public class Sort {

	private long array[];
	private int length;



	/*
	 * Performs Bubble sort. The control loops through the entire array and
	 * compares 2 adjacent values in array and swaps them if value at index i is
	 * larger than index i+1 (order of increasing value). This pass is performed
	 * until no swaps are done for an entire array pass.
	 */
	public long[] bubbleSort(long[] list) {

		boolean swap = true;

		while (swap) {
			swap = false;
			for (int index = 0; index < list.length - 1; index++) {
				if (list[index + 1] < list[index]) {

					swapAt(index, index + 1, list);
					swap = true;
				}
			}
		}
		return list;

	}

	public long[] insertSort(long[] list) {

		for (int j = 1; j < list.length; j++) {

			for (int i = j - 1; i >= 0; i--) {

				if (list[j] < list[i]) {
					swapAt(i, j, list);
					j--;
				}

			}

		}

		return list;

	}
	
	public long[] quickSort(long[] inputArr) {

		if (inputArr == null || inputArr.length == 0) {
			return new long[1];
		}
		this.array = inputArr;
		length = inputArr.length;
		recQuickSort(0, length - 1);

		return inputArr;
	}
	
	private void recQuickSort(int lowerIndex, int higherIndex) {

		int i = lowerIndex;
		int j = higherIndex;
		// calculate pivot number, I am taking pivot as middle index number
		long pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];
		// Divide into two arrays
		while (i <= j) {
			/**
			 * In each iteration, we will identify a number from left side which
			 * is greater then the pivot value, and also we will identify a
			 * number from right side which is less then the pivot value. Once
			 * the search is done, then we exchange both numbers.
			 */
			while (array[i] < pivot) {
				i++;
			}
			while (array[j] > pivot) {
				j--;
			}
			if (i <= j) {
				swapAt(i, j, array);
				// move index to next position on both sides
				i++;
				j--;
			}
		}
		// call quickSort() method recursively
		if (lowerIndex < j)
			recQuickSort(lowerIndex, j);
		if (i < higherIndex)
			recQuickSort(i, higherIndex);
	}

	
	
	
	public long[]selectionSort(long[]list) {

		int currMin = 0;
		int i = 0, j = 0;

		while (i < list.length - 1) {

			currMin = i;

			for (j = i + 1; j < list.length; j++) {

				if (list[j] < list[currMin]) {
					currMin = j;
				}
			}
			if (i != j)
				swapAt(i, currMin, list);
			i++;
		}
		return list;

	}

	
	
	/*MERGE SORT*/
	
	// Main function that sorts arr[l..r] using
	// merge()
	public long[] mergeSort(long arr[], int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);
			merge(arr, left, mid, right);
		}
		return arr;
	}

	private void merge(long arr[], int left, int middle, int right) {
		// Find sizes of two subarrays to be merged
		int n1 = middle - left + 1;
		int n2 = right - middle;

		/* Create temp arrays */
		long L[] = new long[n1];
		long R[] = new long[n2];

		/* Copy data to temp arrays */
		for (int i = 0; i < n1; ++i)
			L[i] = arr[left + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[middle + 1 + j];

		/* Merge the temp arrays */

		// Initial index of merged subarry array
		int i = 0, j = 0, k = left;
		for (; i < n1 && j < n2; k++) {

			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
		}

		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	/*
	 * Function swaps the value in the list at position i and j in a array 
	 */
	private void swapAt(int i, int j, long[] list) {
		long temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
}
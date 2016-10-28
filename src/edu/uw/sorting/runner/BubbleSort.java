package edu.uw.sorting.runner;

public class BubbleSort {

	

    /*
	 * Performs Bubble sort. The control loops through the entire array and
	 * compares 2 adjacent values in array and swaps them if value at index i is larger than index i+1
	 * (order of increasing value). This pass is performed until no swaps are done for an
	 * entire array pass.
	 */
	public int[] sort(int[] list){
    
		boolean swap=true;
		
    	while(swap){
    		swap=false;
			for(int index=0;index<list.length-1;index++){			
	    		if(list[index+1]<list[index]){
	    			
	    			swapAt(index,list);
	    			swap=true;
	    		}
	    	}
    	}
    	return list;
    	
	}

	/*
	 * Function swaps the value in the list at position i and i+1
	 */
	private void swapAt(int i,int[] list) {
		int temp=list[i];
		list[i]=list[i+1];
		list[i+1]=temp;
	}
}
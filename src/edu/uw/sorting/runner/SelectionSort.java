package edu.uw.sorting.runner;

public class SelectionSort {

    
    public int[] sort(int[] list) {

    	int currMin=0;
    	int i=0,j=0;
		
	   while(i<list.length-1){
    	
		   currMin=i;	   
		   
		   
		   for(j=i+1;j<list.length;j++){
	    	
	    	if(list[j]<list[currMin]){
				currMin=j;
			}
		   }
			if(i!=j)
		   swapAt(i,currMin,list);
			i++;
	   }	
		return list;

	}

	/*
	 * Function swaps the value in the list at position i and i+1
	 */
	private void swapAt(int i, int j,int[] list) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
}
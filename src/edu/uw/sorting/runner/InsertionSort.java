package edu.uw.sorting.runner;

public class InsertionSort {

	    
    public int[] sort(int[] list) {

    	  	    	   		      
		   for(int j=1;j<list.length;j++){
			   
			 for(int i=j-1;i>=0;i--){
				 
			   if(list[j] <list[i]){
				   swapAt(i,j,list);
				   j--;
			   }
			   
			 }
			   
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
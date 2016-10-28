package edu.uw.sorting.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Runner extends Application{

	static int maxSize;
	static int scaleFactor;
	
	static int[] unsortedData;
	static int[] sortedData;

	static Map<String,Map<Long,Long[]>> sortPerformance= new HashMap<String,Map<Long,Long[]>>();
	Map<Long,Long[]> performance= null;	//represents time taken and memory consumed (arraySize, [0]=timetaken,[1]=memory)
	
	
	static BubbleSort bubble= new BubbleSort();  
	static SelectionSort select= new SelectionSort();
	static InsertionSort insert= new InsertionSort();
	static QuickSort quick= new QuickSort();
	static MergeSort merge = new MergeSort();

    public static void main(String[] args) {
    	
    	Runner run= new Runner();
    	

    	Scanner sc;
		try {
			sc = new Scanner(new File("datascript"));
			maxSize= sc.nextInt();
			sc.nextLine();
			scaleFactor=sc.nextInt();
			sc.close();
			
					
			for(int j=1;j<maxSize;){	
		  		
				unsortedData=generateRandom(j);
				run.bubble();
				run.selection();
				run.insert();
				run.quick();
				run.merge();
				
                j=j*scaleFactor;

			}
		
			run.printPerf();
	    	//create graphs
			launch();		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private  void merge() {

    	Long[] perf= new Long[2];
    	System.out.println("Merge Sort\n-------------------------------------");
    	
    	long timestamp=System.currentTimeMillis();		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		
         sortedData=merge.sort(unsortedData,0,unsortedData.length-1);
         
         performance(perf, timestamp,"Merge");
         print(sortedData);
	}

	private void insert() {
		Long[] perf= new Long[2];
    	System.out.println("Insertion Sort\n----------------------------------");
    	
    	long timestamp=System.currentTimeMillis();		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
                      
    	sortedData=insert.sort(unsortedData);
    
    	//Caluclating time taken and memory utilized
    	performance(perf, timestamp,"Insertion");	
    	print(sortedData);
	}

	private void quick() {

		Long[] perf= new Long[2];
    	System.out.println("Quick Sort\n-----------------------");
    	
    	long timestamp=System.currentTimeMillis();
		Runtime.getRuntime().gc();
		
    	sortedData=quick.sort(unsortedData);
    	
    	performance(perf,timestamp,"Quick");
    	print(sortedData);
		
	}

	private void selection() {
		
		Long[] perf= new Long[2];
    	System.out.println("Selection Sort\n-----------------------");
    	
    	long timestamp=System.currentTimeMillis();
		Runtime.getRuntime().gc();
		
    	sortedData=select.sort(unsortedData);
    	
    	performance(perf,timestamp,"Selection");
    	print(sortedData);
		
    	
	}

	
    public void bubble(){   				
			
    	System.out.println("Bubble Sort\n-----------------------");
    	Long[] perf= new Long[2];	

		long timestamp = System.currentTimeMillis();
		Runtime.getRuntime().gc();

		sortedData = select.sort(unsortedData);
		performance(perf,timestamp,"Bubble");
		print(sortedData);
    }
    
	private void print(int[] data) {
			
			
		for(int k=0;k<data.length;k++){
				System.out.print(data[k]+",");
			}
			
	}
	private  void printPerf() {
		
		for (String sortName:sortPerformance.keySet()) {
        	Map<Long,Long[]> perf=sortPerformance.get(sortName);
        	
        	System.out.println(sortName+" Sort");
        	for(long key:perf.keySet()){
		
				System.out.print("\nTime taken : "+perf.get(key)[0]);
				System.out.println("  Memory consumed: "+perf.get(key)[1]);
			}
		}
			
	}
	
	public static int[] generateRandom(int n){
	    	int[] arr= new int[n];
	    	Random rand = new Random();
	    	
	    	
	    	for(int i=0;i<n;i++){
	    		arr[i]= rand.nextInt(maxSize);
	    	}    	
	    	
	    	return arr;
	    }

    
    
	@Override
	public void start(Stage stage) throws Exception {

        stage.setTitle("Sorting Performace measure in terms of Time Taken");
        
        NumberAxis xAxisTime = new NumberAxis();        
        NumberAxis xAxisMemory = new NumberAxis();
        NumberAxis yAxisTime = new NumberAxis();
        NumberAxis yAxisMemory= new NumberAxis();
        
        
        xAxisTime.setLabel("Size of Array");
        xAxisMemory.setLabel("Size of Array");
        
        xAxisTime.setMinorTickVisible(false);
        yAxisTime.setMinorTickVisible(false);
        xAxisMemory.setMinorTickVisible(false);
        yAxisMemory.setMinorTickVisible(false);

        
        yAxisTime.setLabel("Time taken in milliseconds");
        yAxisMemory.setLabel("Memory utilized in Bytes");

	        LineChart<Number,Number> lineChartTime=new LineChart<Number, Number>(xAxisTime, yAxisTime);;
	        LineChart<Number,Number> lineChartMemory=new LineChart<Number, Number>(xAxisMemory, yAxisMemory);
	        
	        lineChartTime.setTitle("Sorting Performance in Time Taken");
			lineChartMemory.setTitle("Sorting Performance in Memory  Utilization");
			lineChartMemory.setCreateSymbols(false);
			

	        XYChart.Series<Number,Number> series;
	        XYChart.Series<Number,Number> series1;

	        for (String sortName:sortPerformance.keySet()) {
	        	Map<Long,Long[]> perf=sortPerformance.get(sortName);
	        	
				//defining a series
				series = new XYChart.Series<Number, Number>();
				series1 = new XYChart.Series<Number, Number>();

				series.setName(sortName + " Sort");
				series1.setName(sortName + " Sort");
				
				//populating the series with data
				for (long key : perf.keySet()) {
					series.getData().add(new XYChart.Data(key, perf.get(key)[0])); //gets first elemnt of value=time taken
					series1.getData().add(new XYChart.Data(key, perf.get(key)[1])); //gets first elemnt of value=time taken

				}
				lineChartTime.getData().add(series);
				 lineChartMemory.getData().add(series1);
			}
	        
	        

		FlowPane root = new FlowPane();
		root.getChildren().addAll(lineChartTime, lineChartMemory);

		Scene scene  = new Scene(root,1000,500);
		 scene.getStylesheets().add("Charts.css");
        stage.setScene(scene);
        stage.show();
	}
	
	private void performance(Long[] perf, long timestamp,String sortnam) {
		perf[0]=(System.currentTimeMillis()-timestamp);  //time taken
    	
	    perf[1]=(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()); //memory
        
        //Storing time taken and memory utilized in a map with key as array size
       
	    if(sortPerformance.containsKey(sortnam)){
	    	performance=sortPerformance.get(sortnam);
	    }else{
	    	performance= new HashMap<Long,Long[]>();
	    }
	    
	    performance.put((long)unsortedData.length,perf);	
    	sortPerformance.put(sortnam,performance);
    	
	}
    

}
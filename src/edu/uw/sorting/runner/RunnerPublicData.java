package edu.uw.sorting.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class RunnerPublicData extends Application{

	static int maxSize;
	static int scaleFactor;
	static int[] degreeArr={10,50,100};
	static int degreeOfSortedness=10;
	StringBuilder content= new StringBuilder();
	
	long[] unsortedData;
	long[] sortedData;
	

	static TreeMap<String,TreeMap<Long,Long[]>> sortPerformance= new TreeMap<String,TreeMap<Long,Long[]>>();
	TreeMap<Long,Long[]> performance= null;	//represents time taken and memory consumed (arraySize, [0]=timetaken,[1]=memory)
	TreeMap<Long,Long[]> degPerformance=null;
	private boolean degree =false;
	
	static Sort sort= new Sort();
	
    public static void main(String[] args) {
    	
    	RunnerPublicData run= new RunnerPublicData();
    	Scanner sc=null;
		try {
				sc=new Scanner(new File("dataScript"));
				degreeOfSortedness=0;
				run.unsortedData=populatePublicData(sc.nextLine());//generateRandom(j);
				run.bubble();
				run.selection();
				run.insert();
				run.quick();
				run.merge();
			
			
			//Perform sorting for varying degree of sortedness :0-100% with array size fixed.
		
			int i=0;
			while(i<degreeArr.length){
				degreeOfSortedness=degreeArr[i];
				run.degree=true;
				run.unsortedData=populatePublicData(sc.nextLine());//generateRandom(maxSize);
				run.bubble();
				run.selection();
				run.insert();
				run.quick();
				run.merge();
				i++;
			}			

	    	//create graphs
			launch();		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("Please try again.");
			e.printStackTrace();
		}finally{
			sc.close();
		}
    	
    }
    
    private void merge() {

    	Long[] perf= new Long[2];
    	
    	long timestamp=System.currentTimeMillis();		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		
         sortedData=sort.mergeSort(unsortedData,0,unsortedData.length-1);
         
         performance(perf, timestamp,"Merge");
         print(sortedData,"Merge");
	}

	private void insert() {
		Long[] perf= new Long[2];

    	
    	long timestamp=System.currentTimeMillis();		
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
                      
    	sortedData=sort.insertSort(unsortedData);
    
    	//Caluclating time taken and memory utilized
    	performance(perf, timestamp,"Insertion");	
    	print(sortedData,"Insertion");
	}

	private void quick() {

		Long[] perf= new Long[2];

    	
    	long timestamp=System.currentTimeMillis();
		Runtime.getRuntime().gc();
		
    	sortedData=sort.quickSort(unsortedData);
    	
    	performance(perf,timestamp,"Quick");
    	print(sortedData,"Quick");
		
	}

	private void selection() {
		
		Long[] perf= new Long[2];

    	
    	long timestamp=System.currentTimeMillis();
		Runtime.getRuntime().gc();
		
    	sortedData=sort.selectionSort(unsortedData);
    	
    	performance(perf,timestamp,"Selection");
    	print(sortedData,"Selection");
	}

	
    private void bubble(){   				
			

    	Long[] perf= new Long[2];	

		long timestamp = System.currentTimeMillis();
		Runtime.getRuntime().gc();

		sortedData = sort.bubbleSort(unsortedData);
		performance(perf,timestamp,"Bubble");
		print(sortedData,"Bubble");
    }
    
    private void print(long[] data,String sortName) {
		
    	
		try {
		
			if(data.length==maxSize){
					content.append("\n\n------------------------------------------------------------------------\n");
					content.append("\t\t"+sortName + " Sort\n");
					content.append("---------------------------------------------------------------------------\n");
					
					content.append("Sorted Data :  ");
					for(int k=0;k<data.length;k++){
						content.append(data[k]+",");
					}
					
					
					if(degree){			
						Map<Long, Long[]> perf = sortPerformance.get("deg"+sortName);
						content.append("\nData size : " + maxSize);
						content.append(" Degree of sortedness : " + degreeOfSortedness+" %  ");
						content.append(" Time taken : " + perf.get((long)degreeOfSortedness)[0]+" msec  ");
						content.append("  Memory consumed : " + perf.get((long)degreeOfSortedness)[1]+" bytes\n");	
					}else{
						Map<Long, Long[]> perf = sortPerformance.get(sortName);
						
						content.append("\nData Size : " + data.length+"  ");
						content.append(" Time taken : " + perf.get((long)data.length)[0]+" msec  ");
						content.append("  Memory consumed : " + perf.get((long)data.length)[1]+" bytes\n");
					}
					
					System.out.println(content.toString());	
					File file = new File("output.txt");
		
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(content.toString());
					bw.close();
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	
	
		
	private static long[] populatePublicData(String filename) throws FileNotFoundException, ParseException{
		
		long[] arr= null;
			int i = 0;
			Scanner sc = new Scanner(new File(filename));
			if (filename.endsWith("i")) {
				arr= new long[200006];
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
				
				while (sc.hasNextLine()) {
					String dateStr = sc.nextLine();
					Date startDate = (Date) formatter.parse(dateStr);
					arr[i] = (int) startDate.getTime();
					i++;
				} 
				
			}else{
				arr=new long[233];
				while (sc.hasNextLine()) {
					
					arr[i] =(int)(sc.nextDouble()*1000000);
					i++;
				} 
			}
			maxSize=arr.length;
		
		return arr;
	}
    
    
	@Override
	public void start(Stage stage) throws Exception {

        stage.setTitle("Sorting Performace measure");
        
        CategoryAxis xAxisTime = new CategoryAxis();        
        CategoryAxis xAxisMemory = new  CategoryAxis();
        NumberAxis yAxisTime = new NumberAxis();
        NumberAxis yAxisMemory= new NumberAxis();
        
        CategoryAxis xAxisTimeDeg = new CategoryAxis();        
        CategoryAxis xAxisMemoryDeg = new CategoryAxis();
        NumberAxis yAxisTimeDeg = new NumberAxis();
        NumberAxis yAxisMemoryDeg= new NumberAxis();
        
        
        xAxisTime.setLabel("Size of Array");
        xAxisMemory.setLabel("Size of Array");        
        xAxisTimeDeg.setLabel("Degree of Sortedness in %");
        xAxisMemoryDeg.setLabel("Degree of Sortedness in %");
        
        yAxisTime.setMinorTickVisible(false);
        yAxisTimeDeg.setMinorTickVisible(false);
        yAxisMemory.setMinorTickVisible(false);
        
        
        yAxisTime.setLabel("Time taken in milliseconds");
        yAxisMemory.setLabel("Memory utilized in Bytes");
        yAxisTimeDeg.setLabel("Time taken in milliseconds");
        yAxisMemoryDeg.setLabel("Memory utilized in Bytes");

	    LineChart<String,Number> lineChartTime=new LineChart<String, Number>(xAxisTime, yAxisTime);
        LineChart<String,Number> lineChartTimeDeg=new LineChart<String, Number>(xAxisTimeDeg, yAxisTimeDeg);
        
	    BarChart<String,Number> barChartMemory=new BarChart<String, Number>(xAxisMemory, yAxisMemory);
        BarChart<String,Number> barChartMemoryDeg=new BarChart<String, Number>(xAxisMemoryDeg, yAxisMemoryDeg);

        lineChartTime.setTitle("Sorting Performance(Time Taken)");
        barChartMemory.setTitle("Sorting Performance(Memory Utilization)");	
		
        barChartMemory.setMaxHeight(300);
		barChartMemoryDeg.setMaxHeight(300);
		lineChartTime.setMaxHeight(300);
		lineChartTimeDeg.setMaxHeight(300);
		
		//lineChartTime.setCreateSymbols(false);
		lineChartTimeDeg.setCreateSymbols(false);


	    XYChart.Series<String,Number> series;
	    XYChart.Series<String,Number> series1;
	    XYChart.Series<String,Number> series2;
	    XYChart.Series<String,Number> series3;

	        for (String sortName:sortPerformance.keySet()) {
	        	
	        	if (sortName.startsWith("deg")) {
					Map<Long, Long[]> perfDeg = sortPerformance.get(sortName);
					//defining a series
					series2 = new XYChart.Series<String, Number>();
					series3 = new XYChart.Series<String, Number>();
					series2.setName(sortName.replace("deg", "") + " Sort");
					series3.setName(sortName.replace("deg", "") + " Sort");
					
					//populating the series with data
					
					for (long key : perfDeg.keySet()) {if(key==0){
						
						series2.getData().add(new XYChart.Data("Random", perfDeg.get(key)[0]));
						series3.getData().add(new XYChart.Data("Random", perfDeg.get(key)[1])); //gets first elemnt of value=time taken
						}else if(key==100){
							series2.getData().add(new XYChart.Data("Reverse", perfDeg.get(key)[0]));
							series3.getData().add(new XYChart.Data("Reverse", perfDeg.get(key)[1])); //gets first elemnt of value=time taken
						}else{						
							series2.getData().add(new XYChart.Data(key+"% ", perfDeg.get(key)[0]));
							series3.getData().add(new XYChart.Data(key+"% ", perfDeg.get(key)[1])); //gets first elemnt of value=time taken
						}
					}
					
					lineChartTimeDeg.getData().add(series2);
					barChartMemoryDeg.getData().add(series3);
				}else{
	        		Map<Long,Long[]> perf=sortPerformance.get(sortName);
	        	
	        		//defining a series
					series = new XYChart.Series<String, Number>();
					series1 = new XYChart.Series<String, Number>();
									
					series.setName(sortName + " Sort");
					series1.setName(sortName + " Sort");
					//populating the series with data
					series.getData().add(new XYChart.Data("0 ", 0));
					for (long key : perf.keySet()) {
						series.getData().add(new XYChart.Data(key+" ", perf.get(key)[0])); //gets first elemnt of value=time taken
						series1.getData().add(new XYChart.Data(key+" ", perf.get(key)[1])); //gets first elemnt of value=time taken
	
					}
					
					 lineChartTime.getData().add(series);
					 barChartMemory.getData().add(series1);
	        	}
	        	
			}

		FlowPane root = new FlowPane();
		root.getChildren().addAll(lineChartTime, barChartMemory,lineChartTimeDeg, barChartMemoryDeg);

		Scene scene  = new Scene(root,1000,600);
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
	    	performance= new TreeMap<Long,Long[]>();
	    }
	    
	    if(sortPerformance.containsKey("deg"+sortnam)){
	    	degPerformance=sortPerformance.get("deg"+sortnam);
	    }else{
	    	degPerformance= new TreeMap<Long,Long[]>();
	    }
	    
	    
	    performance.put((long)unsortedData.length,perf);	   	
	    degPerformance.put((long)degreeOfSortedness,perf);
	   
	    sortPerformance.put(sortnam,performance);
	    sortPerformance.put("deg"+sortnam,degPerformance);
	    
	 
    	
	}
    

}
package edu.uw.sorting.runner;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Runner extends Application{

	static long[] yValue=null;
	static int[] xValue= {1,10,100,1000,10000};
    public static void main(String[] args) {
    	
    	
    	BubbleSort bubble= new BubbleSort();
    	
    	
    	yValue=new long[100];
    	for(int j=1,k=0;j<100;k++){
	    	int[] arr= generateRandom(j*100);  	
	    	bubble.setList(arr);

	    	long timestamp=System.currentTimeMillis();
	    	bubble.sort();
	    	j=j+10;
			yValue[k]=System.currentTimeMillis()-timestamp;
			System.out.println("in");    	}
    	System.out.println("out");
    	launch();

    }
    
    public static int[] generateRandom(int n){
    	int[] arr= new int[n];
    	Random rand = new Random();
    	
    	
    	for(int i=0;i<n;i++){
    		arr[i]= rand.nextInt(1000);
    	}    	
    	
    	return arr;
    }

	@Override
	public void start(Stage stage) throws Exception {

        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Size of Array");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Run time Performace");
        //defining a series
        
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
    	
        series.setName("Runtime Performance");
        //populating the series with data
       
        
    	for(int i=0;i<xValue.length;i++){
    			series.getData().add(new XYChart.Data(xValue[i], yValue[i]));
    		
    	}
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    
		
	}
    

}
package edu.uw.sorting.runner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class LineChartApp extends Application {
 
	public static int[]xValue = null;
     public static long[]yValue = null;


	@Override public void start(Stage stage) {
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
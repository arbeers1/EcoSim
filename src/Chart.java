import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart {
  private static LineChart<Number,Number> lineChart;
  private static XYChart.Series series;
  
  public Chart() {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Time (Cycles)");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Population of Bunnies");
    
    lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    lineChart.setTitle("Rabbit Population Over Time");
    
    series = new XYChart.Series();
  }
  
  public static void add(long cycle, int population) {
    series.getData().add(new XYChart.Data(cycle, population));
  }
  
  public static LineChart getChart() {
    lineChart.getData().add(series);
    return lineChart;
  }
}

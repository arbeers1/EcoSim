import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * @author Alexander Beers
 * Allows documentation of data into linechart
 */
public class Chart {
  private static LineChart<Number,Number> lineChart;
  private static XYChart.Series series;
  
  /**
   * Creates a new chart
   */
  public Chart() {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Time (Cycles)");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Population of Bunnies");
    
    lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    lineChart.setTitle("Rabbit Population Over Time");
    
    series = new XYChart.Series();
  }
  
  /**
   * Adds a data set into the chart
   * @param cycle - time / current iteration of sim
   * @param population - current bunny population
   */
  public static void add(long cycle, int population) {
    series.getData().add(new XYChart.Data(cycle, population));
  }
  
  /**
   * @return The linechart with the most up to data information
   */
  public static LineChart getChart() {
    lineChart.getData().add(series);
    return lineChart;
  }
}

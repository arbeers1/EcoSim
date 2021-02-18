import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Alexander Beers
 * The Display / Gui for the program
 */
public class Render extends Application {
  private EcoRunner ecoRun;
  private static Pane container;
  private BorderPane subMenu;
  private Slider grassSlider;
  private Slider bunnySlider;
  private boolean started;
  private static LineChart graph;
  
  /**
   * Initializes class variables. Makes display visible.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    started = false;
    new Chart();
    graph = Chart.getChart();
    graph.setVisible(false);
    ecoRun = new EcoRunner();
    //New thread needed for logic loop 
    new Thread() {
      @Override
      public void run() {
        ecoRun.loop();
      }
    }.start();

    //Build Scene
    Scene scene = new Scene(buildContainer(), 0, 0);
    container.getChildren().add(graph);
    scene.getStylesheets().add(Render.class.getResource("style.css").toExternalForm());
    scene.setOnKeyPressed(e -> {
      switch(e.getCode()) {
        case M: menu(); break;
        case G: graph(); break;
      }
    });
    
    //Show display
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Ecosystem Simulator");
    stage.setMaximized(true);
    stage.show();
    //Stop ecoThread on display close
    stage.setOnCloseRequest(e -> System.exit(0)); 
    
    graph.setMinWidth(stage.getWidth() * .5);
    graph.setMinHeight(stage.getWidth() * .5);
    subMenu.setMinWidth(stage.getWidth() * .3);
    subMenu.setMinHeight(stage.getHeight() * .3);
  }
  
  /**
   * Initializes main display
   * @return - Pane Container
   */
  private Pane buildContainer() {
    container = new Pane();
    Pane pane = new Pane();
    
    //The menu in which the user will interact with the program
    subMenu = new BorderPane();
    Label label = new Label("Menu");
    BorderPane.setAlignment(label, Pos.TOP_CENTER);
    
    VBox sliderContainer = new VBox(5);
    grassSlider = new Slider(0, 100, 50);
    bunnySlider = new Slider(0, 100, 50);
    Label grassLabel = new Label("Starting grass population: " + (int) grassSlider.getValue());
    Label bunnyLabel = new Label("Starting bunny population: " + (int) bunnySlider.getValue());
    sliderContainer.getChildren().addAll(grassLabel, grassSlider, bunnyLabel, bunnySlider);
    
    VBox wrapper = new VBox(5);
    HBox buttonContainer = new HBox(5);
    Button start = new Button("Start"); start.setId("start");
    Button stop = new Button("Stop"); stop.setId("stop");
    HBox.setHgrow(start, Priority.ALWAYS); start.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(stop, Priority.ALWAYS); stop.setMaxWidth(Double.MAX_VALUE);
    buttonContainer.getChildren().addAll(stop, start);
    Label console = new Label();
    wrapper.getChildren().addAll(buttonContainer, console);
    
    subMenu.setTop(label);
    subMenu.setCenter(sliderContainer);
    subMenu.setBottom(wrapper);
    
    container.getChildren().addAll(pane, subMenu);
    StackPane.setAlignment(subMenu, Pos.TOP_LEFT);
    
    // Listeners for menu components
    grassSlider.valueProperty().addListener(e -> grassLabel.setText("Starting grass population: " + (int)grassSlider.getValue()));
    bunnySlider.valueProperty().addListener(e -> bunnyLabel.setText("Starting bunny population: " + (int)bunnySlider.getValue()));
    start.setOnAction(e -> {
      if(!started) {
        console.setText("Simulation Started");
        ecoRun.start((int) grassSlider.getValue(), (int) bunnySlider.getValue(), container.getHeight(), container.getWidth());
        started = true;
      }else {
        console.setText("Simulation must be reset");
      }
    });
    stop.setOnAction(e -> {
      console.setText("Simulation Stopped"); 
      ecoRun.stop();});
    return container;
  }
  
  /**
   * sets graph visible/invisible
   */
  private void graph() {
    if(graph.isVisible()) {
      graph.setVisible(false);
    }else {
      graph.setVisible(true);
    }
  }
  
  /**
   * Adds an image to the scene
   * @param img - image to add
   */
  public static void add(ImageView img) {
    container.getChildren().add(img);
    graph.toFront();
  }
  
  /**
   * Toggles visibility of menu on call
   */
  private void menu() {
    subMenu.setVisible(!subMenu.isVisible());
  }

  /**
   * @return screen height
   */
  public static double getScreenHeight() {
    return container.getHeight();
  }
  
  /**
   * @return screen width
   */
  public static double getScreenWidth() {
    return container.getWidth();
  }
   
  /**
   * Starts display
   */
  public static void main(String[] args) {
    launch();
  }

}

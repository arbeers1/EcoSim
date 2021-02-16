import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Render extends Application {
  private EcoRunner ecoRun;
  private BorderPane subMenu;
  private Label grassLabel;
  private Label bunnyLabel;
  
  /**
   * Initializes class variables. Makes display visible.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    ecoRun = new EcoThread().initialize();
    
    //Build Scene
    Scene scene = new Scene(buildContainer(), 0, 0);
    scene.getStylesheets().add(Render.class.getResource("style.css").toExternalForm());
    scene.setOnKeyPressed(e -> {
      switch(e.getCode()) {
        case M: menu(); break;
      }
    });
    
    //Show display
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Ecosystem Simulator");
    stage.setMaximized(true);
    stage.show();
    
    subMenu.setMaxWidth(stage.getWidth() * .3);
    subMenu.setMaxHeight(stage.getHeight() * .3);
  }
  
  /**
   * Initializes main display
   * @return - Pane Container
   */
  private StackPane buildContainer() {
    StackPane container = new StackPane();
    Pane pane = new Pane();
    
    //The menu in which the user will interact with the program
    subMenu = new BorderPane();
    Label label = new Label("Menu");
    BorderPane.setAlignment(label, Pos.TOP_CENTER);
    
    VBox sliderContainer = new VBox(5);
    grassLabel = new Label("Starting grass population:");
    bunnyLabel = new Label("Starting bunny population:");
    Slider grassSlider = new Slider(0, 100, 50);
    Slider bunnySlider = new Slider(0, 100, 50);
    sliderContainer.getChildren().addAll(grassLabel, grassSlider, bunnyLabel, bunnySlider);
    
    HBox buttonContainer = new HBox(5);
    Button start = new Button("Start"); start.setId("start");
    Button stop = new Button("Stop"); stop.setId("stop");
    Button reset = new Button("Reset"); 
    HBox.setHgrow(start, Priority.ALWAYS); start.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(stop, Priority.ALWAYS); stop.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(reset, Priority.ALWAYS); reset.setMaxWidth(Double.MAX_VALUE);
    buttonContainer.getChildren().addAll(reset, stop, start);
    
    subMenu.setTop(label);
    subMenu.setCenter(sliderContainer);
    subMenu.setBottom(buttonContainer);
    
    container.getChildren().addAll(pane, subMenu);
    StackPane.setAlignment(subMenu, Pos.TOP_LEFT);
    
    //TODO Listeners for menu components
    grassSlider.valueProperty().addListener(e -> grassLabel.setText("Starting grass population: " + (int)grassSlider.getValue()));
    return container;
  }
  
  /**
   * Toggles visibility of menu on call
   */
  private void menu() {
    subMenu.setVisible(!subMenu.isVisible());
  }

  /**
   * Starts display
   */
  public static void main(String[] args) {
    launch();
  }

}

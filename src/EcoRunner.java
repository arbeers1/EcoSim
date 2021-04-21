import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;

/**
 * @author Alexander Beers
 * Handles sim logic
 */
public class EcoRunner {
  private boolean running;
  private int grassPop;
  private int bunnyPop;
  private long cycle;
  private Random r;
  private double screenHeight;
  private double screenWidth;
  private ArrayList<Grass> grassList;
  private ArrayList<Bunny> bunnyList;
  
  public EcoRunner() {
    cycle = 0;
    running = false;
    grassList = new ArrayList<Grass>(100);
    bunnyList = new ArrayList<Bunny>(100);
    r = new Random();
  }
  
  /**
   * Starts Iteration cycle
   * @param grassPop - initial grass population
   * @param bunnyPop - initial bunny population
   * @param screenHeight - height of screen
   * @param screenWidth - width of screen
   */
  public void start(int grassPop, int bunnyPop, double screenHeight, double screenWidth) {
    this.grassPop = grassPop;
    this.bunnyPop = bunnyPop;
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
    
    for(int i = 0; i < grassPop; i++) {
      grassList.add(new Grass(r.nextInt((int) screenWidth), r.nextInt((int) screenHeight)));
    }
    for(int i = 0; i < bunnyPop; i++) {
      int currX = r.nextInt((int) screenWidth);
      int currY = r.nextInt((int) screenHeight);
      bunnyList.add(new Bunny( currX, currY));
    }
    running = true;
  }
  
  /**
   * Stops iteration cycle
   */
  public void stop() {
    running = false;
    System.out.println("running is false");
  }
  
  /**
   * The logic loop - controls cycling through each step of the simulation.
   */
  public void loop() {
    long startTime = System.currentTimeMillis();
    //Polling loop
    while(true) {
      //Iteration loop consisting of logic cycle and draw
      System.out.print(""); //Unsure why but this print helps update display and sim threads.
      
      while(running) { //This loop updates the logic
        if(System.currentTimeMillis() - startTime > 10) {
          startTime = System.currentTimeMillis();
          cycle++;
          //Updates each bunny in the list
          Platform.runLater(() -> {
            for(int i = 0; i < bunnyPop; i++) {
              if(bunnyList.get(i).update(cycle) == -1) { //Case if bunny dies
                bunnyPop--;
                bunnyList.remove(i);
              }
            }
          });
          //New grass every 250 cycles
          if(cycle % 250 == 0) {
            Platform.runLater(() -> {
              Chart.add(cycle, bunnyPop);
              grassList.add(Grass.update());
              grassPop++;
              });
          }
          //Bunnies reproduce every 3000 cycles
          if(cycle % 3000 == 0) {
            int breedNum = bunnyPop/2;
            for(int i = 0; i < breedNum; i++) {
              Platform.runLater(() -> {
                bunnyList.add(new Bunny(r.nextInt((int) Render.getScreenWidth()), r.nextInt((int) Render.getScreenHeight())));
                bunnyPop++;
              });
            }
          }
        }
      }

    }
  }
  
}

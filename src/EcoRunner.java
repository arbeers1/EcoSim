import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;

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
  
  public void reset() {
    
  }
  
  public void loop() {
    long startTime = System.currentTimeMillis();
    //Polling loop
    while(true) {
      //Iteration loop consisting of logic cycle and draw
      System.out.print(""); //This statement is needed for loop to work due to concurrency
      
      while(running) {
        
        //This loop updates the logic
        while(System.currentTimeMillis() - startTime > 5) {
          grassPop = Grass.getGrassPopulation();
          System.out.println("grass pop: " + grassPop + "  " + "bunny pop: " + bunnyPop);
          startTime = System.currentTimeMillis();
          cycle++;
          //Updates each bunny in the list
          Platform.runLater(() -> {
            for(int i = 0; i < bunnyPop; i++) {
              if(bunnyList.get(i).update(cycle) == -1) {
                bunnyPop--;
                bunnyList.remove(i);
              }
            }
          });
          if(cycle % 500 == 0) {
            Platform.runLater(() -> {grassList.add(Grass.update()); grassPop++;});
          }
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

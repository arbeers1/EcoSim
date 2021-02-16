import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bunny {
  private long birthDate;
  private long age;
  private int hunger;
  private boolean mature;
  private double screenWidth;
  private double screenHeight;
  private ImageView bunnyImage;
  int currX;
  int currY;
  int targetX;
  int targetY;
  private Image bunnyLeft;
  private Image bunnyRight;
  private Random r;
  
  public Bunny(long birthDate,int x, int y, double screenWidth, double screenHeight) {
    r = new Random();
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.birthDate = birthDate;
    hunger = 1000;
    age = 0;
    bunnyLeft = new Image(getClass().getResource("bunnyLeft.png").toExternalForm());
    bunnyRight = new Image(getClass().getResource("bunnyRight.png").toExternalForm());
    bunnyImage = new ImageView(bunnyLeft);
    currX = x;
    currY = y;
    targetX = currX;
    targetY = currY;
    bunnyImage.setX(x);
    bunnyImage.setY(y);
    Render.add(bunnyImage);
  }
  
  /**
   * @param cycle - The current iteration of the loop
   * @return -1 if dead, 0 if healthy, 1 if mature and healthy
   */
  public int update(long cycle) {
    //Checks death conditions
    hunger--;
    if(hunger <= 0) { //Rabbit can starve
      die();
      return -1;
    }
    age = cycle - birthDate;
    if(age > 3000) { //Rabbit can die of age
      die();
      return -1;
    }
    if(age > 350) { //Age at which rabbit can breed
      mature = true;
    }
    if( hunger < 50) { //Rabbit can get hungry
      eat();
    }
    
    //Random chance to move around the map
    int n = r.nextInt(10000);
    System.out.println(n);
    if(n < 10) {
      targetX = r.nextInt((int) screenWidth - 100) + 50;
      targetY = r.nextInt((int) screenHeight - 100) + 50;
    }
    move();
    
    if(mature) {
      return 1;
    }else {
      return 0;
    }
  }
  
  /**
   * Searches for closest grass to eat
   */
  private void eat() {
    
  }
  
  /**
   * Moves to a random point on the screen, solely to add motion to the sim
   */
  private void move() {
    if(currX < targetX) {
      //TODO Set Left/Right image Modifiers
      currX++;
      bunnyImage.setImage(bunnyRight);
    }else if(currX > targetX) {
      currX--;
      bunnyImage.setImage(bunnyLeft);
    }
    bunnyImage.setX(currX);
    
    if(currY < targetY) {
      currY++;
    }else if(currY > targetY) {
      currY--;
    }
    bunnyImage.setY(currY);
  }
  
  /**
   * Removes the bunny from the scene
   */
  private void die() {
    bunnyImage.setImage(null);
  }
}

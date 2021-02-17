import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bunny {
  private long age;
  private int hunger;
  private ImageView bunnyImage;
  int currX;
  int currY;
  int targetX;
  int targetY;
  private Image bunnyLeft;
  private Image bunnyRight;
  private Random r;
  private Grass grass;
  
  public Bunny(int x, int y) {
    r = new Random();
    hunger = 3000;
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
    grass = null;
    Render.add(bunnyImage);
  }
  
  /**
   * @param cycle - The current iteration of the loop
   * @return -1 if dead, 0 if healthy
   */
  public int update(long cycle) {
    //Checks death conditions
    hunger--;
    if(hunger <= 0) { //Rabbit can starve
      die();
      System.out.println("died from hunger");
      return -1;
    }
    age++;
    if(age > 30000) { //Rabbit can die of age
      die();
      System.out.println("died from age");
      return -1;
      //return 0;
    }
    if( hunger < 1600 && grass == null) { //Rabbit can get hungry
      eat();
    }
    
    //Random chance to move around the map
    int n = r.nextInt(10000);
    if(n < 10 && grass == null) {
      targetX = r.nextInt((int) Render.getScreenWidth() - 100) + 50;
      targetY = r.nextInt((int) Render.getScreenHeight() - 100) + 50;
    }
    move();
    
    return 0;
  }
  
  /**
   * Searches for closest grass to eat
   */
  private void eat() {
    Grass g = Grass.findFood();
    if(g != null) {
      grass = g;
      targetX = g.getX();
      targetY = g.getY();
    }
  }
  
  /**
   * Moves to a random point on the screen, solely to add motion to the sim
   */
  private void move() {
    if(currX < targetX) {
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
    if(currX == targetX && currY == targetY && grass != null) {
      grass.delete();
      grass = null;
      hunger = 3000;
    }
  }
  
  /**
   * Removes the bunny from the scene
   */
  private void die() {
    bunnyImage.setImage(null);
  }
}

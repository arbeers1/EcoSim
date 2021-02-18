import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Alexander Beers
 * A grass object in the sim. Food source of bunnies
 */
public class Grass {
  private static ArrayList<Grass> grassList = new ArrayList<Grass>(); //List of all grass to exist
  private static final Random r = new Random();
  private ImageView grass;
  private int x;
  private int y;
  
  /**
   * @param x - x location to spawn
   * @param y - y location to spawn
   */
  public Grass(int x, int y) {
    Image img = new Image(getClass().getResource("grass.png").toExternalForm());
    grass = new ImageView(img);
    this.x = x;
    this.y = y;
    grass.setX(x);
    grass.setY(y);
    Render.add(grass);
    grassList.add(this);
  }
  
  /**
   * @return Creates a new grass on call and returns it
   */
  public static Grass update() {
    return new Grass(r.nextInt((int) Render.getScreenWidth()), r.nextInt((int) Render.getScreenHeight()));
  }
  /**
   * Takes the youngest grass from the list and returns it
   * @return The last grass on the list
   */
  public static Grass findFood() {
    Grass g = null;
    if(grassList.size() > 0) {
      g = grassList.get(grassList.size()-1);
      grassList.remove(g);
    }
    return g;
  }
  
  /**
   * @return x location
   */
  public int getX() {
    return x;
  }
  
  /**
   * @return y location
   */
  public int getY() {
    return y;
  }
  
  /**
   * Removes grass from scene
   */
  public void delete() {
    grass.setImage(null);
  }
  
  /**
   * @return population size of grass
   */
  public static int getGrassPopulation() {
    return grassList.size();
  }
  
}

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Grass {
  private static ArrayList<Grass> grassList = new ArrayList<Grass>();
  private static final Random r = new Random();
  private ImageView grass;
  private int x;
  private int y;
  
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
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void delete() {
    grass.setImage(null);
  }
  
  public static int getGrassPopulation() {
    return grassList.size();
  }
  
}

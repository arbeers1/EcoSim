
public class EcoThread extends Thread{
  private EcoRunner ecoRun;
  
  public EcoRunner initialize() {
    this.start();
    return ecoRun;
  }
  @Override
  public void run() {
    ecoRun = new EcoRunner();
  }
}

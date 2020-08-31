import javax.swing.*;
import java.awt.*;

/**
 * Map Panel class - represents single instance of Map Panel
 */
public class MapPanel extends JPanel implements  Runnable {

    /** Map object */
    private Map map;

    /** Hero object */
    private Hero hero;

    /**
     * Repaint the screen
     * @param g graphics object to be painted
     */
    public void paintComponent(Graphics g){
        setPreferredSize(new Dimension(250,250));
        map.draw(g, hero.getLocation());
    }

    /**
     * Map panel constructor - constructs single representation of a Map panel
     * @param map map object
     * @param hero hero object
     */
    public MapPanel(Map map, Hero hero){
        this.map = map;
        this.hero = hero;
    }

    /**
     * Refresh the screen
     */
    @Override
    public void run() {
        while( true ){
            repaint();
            try{
               Thread.sleep(45);
            } catch(InterruptedException IE){

            }
        }
    }
}

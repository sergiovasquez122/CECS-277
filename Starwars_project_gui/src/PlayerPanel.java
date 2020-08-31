import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Player panel for stats of the player
 * @author Sergio Vasquez
 */
public class PlayerPanel extends JPanel implements Runnable, MouseListener {
    /** Hero object */
    private Hero hero;

    /** Item object */
    private Item item;

    /** the current game state */
    private String gameState;

    /** the rectangle of the hero inventory */
    private Rectangle [] rectangles;

    /** Map object */
    private Map map;

    /** GamePanel oject */
    private GamePanel gamePanel;

    /**
     * Repaint the screen
     * @param g graphics object for painting
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        hero.draw(g);
    }


    /**
     * Represents single instance of a panel
     * @param h the hero object
     */
    public PlayerPanel(Hero h, String gameState, Map map){
        this.hero = h;
        this.gameState = gameState;
        this.rectangles = hero.getRectangle();
        this.map = map;
        setPreferredSize(new Dimension(300,250));
        addMouseListener(this);
    }

    /**
     * set the item the playerPanel holds onto
     * @param i the item to assigned
     */
    public void setItem(Item i){
        this.item = i;
    }
    /**
     * Refresh the screen
     */
    @Override
    public void run() {
        while (true) {
            repaint();
            try{
                Thread.sleep(45);
            } catch(InterruptedException IE){

            }
        }
    }

    /**
     * Set the current state of the panel
     * @param gameState the new state of the panel
     */
    public void setState(String gameState){
        this.gameState = gameState;
    }

    /**
     * Handle event if clicked on panel
     * @param e the event to be handled
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // if the gamestate is in drop state find the right item from the inventory to drop
        if(gameState.equals("drop")) {
            for (int i = 0; i < rectangles.length; ++i) {
                if (rectangles[i].contains(e.getPoint())){
                    hero.removeItem(i);
                    map.removeCharAtLoc(hero.getLocation());
                    hero.pickUpItem(item);
                    gameState = "traveling";
                    gamePanel.setGameState(gameState);
                    return;
                }
            }
        }
    }

    /**
     * set the Game Panel the object communicates with
     * @param panel the game to connect too
     */
    public void setGamePanel(GamePanel panel){
        this.gamePanel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

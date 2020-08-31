import com.sun.glass.ui.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * GamePanel - Represents single instance of a GamePanel
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener, Runnable {

    /** JTextarea to display useful information */
    private JTextArea textArea;
    /** JButton for when user is choosing to fight an enemy */
    private JButton fightButton;
    /** Enemy Generator object */
    private EnemyGenerator enemyGenerator;
    /** Enemy Object */
    private Enemy enemy;
    /** Item object */
    private Item i;
    /** Item generator object */
    private ItemGenerator itemGenerator;
    /** JButton for when user is choosing to run */
    private JButton runButton;
    /** JButton for when user is choosing to use a medkit */
    private JButton medKitButton;
    /** JButton for when user is choosing to use a force choke */
    private JButton forceChokeButton;
    /** JButton for when user is choosing to use a force push */
    private JButton forcePushButton;
    /* JButton for when user is choosing to use a force slam */
    private JButton forceSlamButton;
    /** JButton for when user is fighting an enemy  with a holocron avaiable*/
    private JButton blasterButton;
    /** JButton for when user is in the finish room or fighting enemy */
    private JButton forceButton;
    /** JButton for when user is in the finish room */
    private JButton dontUseForceButton;
    /** The state of the game represented as a string */
    private String gameState;
    /** Hero object */
    private Hero hero;
    /** Map object */
    private Map map;
    /** player panel */
    private PlayerPanel playerPanel;

    /**
     * GamePanel Constructor - Constructs single instance of GamePanel
     * @param hero Hero object to be used in Main game
     * @param map Map object to be used in Main game
     * @param gameState The current state of the game
     */
    public GamePanel(Hero hero, Map map, String gameState, PlayerPanel playerPanel){
        this.hero = hero;
        this.map = map;
        this.playerPanel = playerPanel;
        this.gameState = gameState;
        itemGenerator = ItemGenerator.getInstance();
        enemyGenerator = EnemyGenerator.getInstance(itemGenerator);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        textArea = new JTextArea(3, 3);
        textArea.setEditable(false);
        forceButton = new JButton("Force");
        fightButton = new JButton("Fight");
        runButton = new JButton("Run");
        medKitButton = new JButton("Use Medkit");
        forceChokeButton = new JButton("Force Choke");
        forcePushButton = new JButton("Force Push");
        forceSlamButton = new JButton("Force Slam");
        blasterButton = new JButton("Use blaster");
        dontUseForceButton = new JButton("Don't use force button");
        add(textArea);
        add(fightButton);
        add(runButton);
        add(medKitButton);
        add(forceChokeButton);
        add(forcePushButton);
        add(forceSlamButton);
        add(blasterButton);
        add(forceButton);
        add(dontUseForceButton);

        fightButton.addActionListener(this);
        runButton.addActionListener(this);
        medKitButton.addActionListener(this);
        forceChokeButton.addActionListener(this);
        forcePushButton.addActionListener(this);
        forceSlamButton.addActionListener(this);
        blasterButton.addActionListener(this);
        forceButton.addActionListener(this);
        dontUseForceButton.addActionListener(this);

        forceButton.setFocusable(true);
        medKitButton.setFocusable(true);
        runButton.setFocusable(true);
        fightButton.setFocusable(true);
        forceChokeButton.setFocusable(true);
        forcePushButton.setFocusable(true);
        forceSlamButton.setFocusable(true);
        blasterButton.setFocusable(true);
        dontUseForceButton.setFocusable(true);
        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Refresh the screen
     * @param g graphics object
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(gameState.equals("traveling") || gameState.equals("drop")){
            travelingState();
        } else if(gameState.equals("fightOrFlight")){
            fightOrFlight();
        } else if(gameState.equals("fightOptions")){
            fightOptions();
        } else if(gameState.equals("forceOptions")){
            forceOptions();
        } else if(gameState.equals("finishRoomOptions")){
            finishRoomOptions();
        } else if(gameState.equals("deadHero")){
            deadHeroState();
        }
    }

    /**
     * Display the game information when
     * the hero has died
     */
    private void deadHeroState(){
        travelingState(); // disables all buttons
        textArea.setText("You have died!");
    }

    /**
     * Display the game information when
     * the hero decides to fight
     * an enemy with the force
     */
    private void forceOptions(){
        travelingState(); // disables all buttons
        forceSlamButton.setVisible(true);
        forcePushButton.setVisible(true);
        forceChokeButton.setVisible(true);
    }

    /**
     * Displays game information when
     * hero decides to fight an enemy
     * and the hero has a holocron
     */
    private void fightOptions(){
        travelingState(); // disables all buttons
        blasterButton.setVisible(true);
        forceButton.setVisible(true);
    }

    /**
     * Displays game Information when
     * hero encounters an enemy
     */
    private void fightOrFlight(){
        travelingState(); // traveling state deactivated all buttons
        fightButton.setVisible(true);
        runButton.setVisible(true);
        if(hero.hasMedKit()){
            medKitButton.setVisible(true);
        }
        textArea.setText(enemy.toString());
    }

    /**
     * Display game information when the
     * hero is roaming
     */
    private void travelingState(){
        fightButton.setVisible(false);
        runButton.setVisible(false);
        medKitButton.setVisible(false);
        forceChokeButton.setVisible(false);
        forcePushButton.setVisible(false);
        forceSlamButton.setVisible(false);
        blasterButton.setVisible(false);
        forceButton.setVisible(false);
        dontUseForceButton.setVisible(false);
    }

    /**
     * Refresh the screen
     */
    @Override
    public void run() {
        // Stop running when the hero has died
        while(hero.getHP()!=0){
            // update necessary information
            playerPanel.setState(gameState);
            requestFocusInWindow(true);
            if(hero.getNumItems() < 5 && gameState.equals("drop")){
                gameState = "traveling";
            }
            repaint();
            try{
                Thread.sleep(20);
            } catch(InterruptedException IE){

            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final int ORIGIN = 0;
        final int SIZE = 600;
        frame.setBounds(ORIGIN,ORIGIN,SIZE,SIZE);
        String gameState = "traveling";
        Map map = Map.getInstance();
        Hero hero = new Hero("Luke", map);
        MapPanel mapPanel = new MapPanel(map, hero);
        PlayerPanel playerPanel = new PlayerPanel(hero,gameState,map);
        GamePanel gamePanel = new GamePanel(hero, map, gameState,playerPanel);
        playerPanel.setGamePanel(gamePanel);

        Thread a = new Thread(gamePanel);
        a.start();
        Thread b = new Thread(playerPanel);
        b.start();
        Thread c = new Thread(mapPanel);
        c.start();
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(playerPanel,BorderLayout.EAST);
        frame.add(gamePanel,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * handles button events
     * @param e the event to be handled
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == runButton && gameState.equals("fightOrFlight")){
            runAway(hero);
            gameState = "traveling";
            textArea.setText("");
        } else if(e.getSource() == fightButton && gameState.equals("fightOrFlight")){
            if(hero.hasHolocron()){
                gameState = "fightOptions";
            } else {
                fight(hero, enemy, "blaster");
            }
        } else if(e.getSource() == medKitButton && gameState.equals("fightOrFlight")){
            hero.removeItem("Med Kit");
            final int HEAL_AMOUNT = 25;
            hero.heal(HEAL_AMOUNT);
            enemyHitHero(hero, enemy);
        }
        else if(e.getSource() == blasterButton && gameState.equals("fightOptions")){
            fight(hero, enemy, "blaster");
        } else if(e.getSource() == forceButton && gameState.equals("fightOptions")){
            gameState = "forceOptions";
        } else if(e.getSource() == forceSlamButton && gameState.equals("forceOptions")) {
            fight(hero, enemy, "forceSlam");
        } else if(e.getSource() == forcePushButton && gameState.equals("forceOptions")){
            fight(hero, enemy, "forcePush");
        } else if(e.getSource() == forceChokeButton && gameState.equals("forceOptions")){
            fight(hero, enemy, "forceSlam");
        } else if(e.getSource() == forceButton && gameState.equals("finishRoomOptions")){
            finishRoom(hero, map);
        } else if(e.getSource() ==  dontUseForceButton && gameState.equals("finishRoomOptions")){
            gameState = "traveling";
        }
    }

   /**
   * method when hero attempts to enter the finish with
   * with a holocron
   * @param hero the hero object
   * @param map the map object
   */
   private void finishRoom(Hero hero, Map map){
        hero.removeItem("Holocron");
        final double THRESHOLD = .15;
        boolean success = (Math.random() >= THRESHOLD);
        if(success){
            hero.increaseLevel();
            map.loadMap(hero.getLevel());
        }
        gameState = "traveling";
    }

    /**
     * One round where the enemy hits the hero
     * @param hero the hero object
     * @param enemy the enemy object
     */
    public void enemyHitHero(Hero hero, Enemy enemy){
            gameState = "fightOrFlight";
            if(hero.hasArmor()) {
                String armorName = hero.removeFirstArmorItem();
                textArea.append(hero.getName() + " defended himself with " + armorName + "\n");
            } else {
                // Change code later
                int enemyAttackPower = enemy.attack(hero);
                textArea.append(enemy.getName() + " hits " + hero.getName() + " for " + enemyAttackPower + "dmg\n");
                // The hero is now dead
                if( hero.getHP() == 0){
                    gameState = "deadHero";
                }
            }
    }

    /**
     * One round fight between hero and enemy
     * @param hero hero object
     * @param enemy enemy object
     * @param heroAttackType the type of attack for the hero the hit the enemy with
     */
    private void fight(Hero hero, Enemy enemy, String heroAttackType){
        // Hero attacking portion
        int heroAttackPower = 0;
        if( heroAttackType.equals("blaster") ){
            // for now change to regular attack later
            heroAttackPower = hero.attack(enemy);
        }  else if( heroAttackType.equals("forceSlam") ){
            hero.removeItem("Holocron");
            heroAttackPower = hero.forceSlam();
            enemy.takeDamage(heroAttackPower);
        } else if( heroAttackType.equals("forcePush")){
            hero.removeItem("Holocron");
            heroAttackPower = hero.forcePush();
            enemy.takeDamage(heroAttackPower);
        } else if( heroAttackType.equals("forceChoke") ){
            hero.removeItem("Holocron");
            heroAttackPower = hero.forceChoke();
            enemy.takeDamage(heroAttackPower);
        }
        textArea.setText(hero.getName() + " hits " + enemy.getName() + " with " + heroAttackType + " for " + heroAttackPower + "dmg\n");
        // enemy not dead go back to fight or flight state
        if( enemy.getHP() != 0 ){
            gameState = "fightOrFlight";
            if(hero.hasArmor()) {
                hero.removeFirstArmorItem();
            } else {
                // Change code later
                int enemyAttackPower = enemy.attack(hero);
                textArea.append(enemy.getName() + " hits " + hero.getName() + " for " + enemyAttackPower + "dmg\n");
                // The hero is now dead
                if( hero.getHP() == 0){
                    gameState = "deadHero";
                }
            }
        } else {
            // The enemy is dead display the victory screen
            textArea.append("You defeated the " + enemy.getName() + "!\n");
            i = enemy.getItem();
            playerPanel.setItem(i);
            textArea.append("You received a " + i.getName() + " from the enemy.\n");
            if( !hero.pickUpItem( i )){
                gameState = "drop";
                textArea.append("Too much items in your inventory. Drop an item");
            } else {
                gameState = "traveling";
            }
            map.removeCharAtLoc(hero.getLocation());
        }
    }


    /**
     * hero moves a random direction on the map
     * @param hero the hero of the game
     */
    public static void runAway(Hero hero) {
        Random random = new Random();
        Point oldLocation = hero.getLocation();

        // While the hero location has not changed
        while ( oldLocation.equals( hero.getLocation() ) ) {

            // hero has four directions they can go
            final int BOUND = 4;
            int walkDirection = random.nextInt( BOUND );

            switch ( walkDirection ) {
                case 0:
                    hero.goNorth();
                    break;
                case 1:
                    hero.goSouth();
                    break;
                case 2:
                    hero.goWest();
                    break;
                case 3:
                    hero.goEast();
                    break;
            }
        }
    }

    /**
     * Handles key events for game panel
     * @param e the event to be handled
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if(gameState.equals("traveling")){
            char c = e.getKeyChar();
            char nextLocation = 'n';
            switch (c){
                case 'w':
                    nextLocation = hero.goNorth();
                    break;
                case 'a':
                    nextLocation = hero.goWest();
                    break;
                case 's':
                    nextLocation = hero.goSouth();
                    break;
                case 'd':
                    nextLocation = hero.goEast();
                    break;
            }

            switch (nextLocation){
                case 'e':
                    enemy = enemyGenerator.generateEnemy(hero.getLevel());
                    gameState = "fightOrFlight";
                    fightOrFlight();
                    break;
                case 'i':
                    Item item = itemGenerator.generateItem();
                    textArea.setText("You found a " + item.getName());
                    if(hero.pickUpItem(item)){
                        map.removeCharAtLoc(hero.getLocation());
                    } else {
                        textArea.append("Too much items in your inventory. Drop an item");
                        gameState = "drop";
                        i = item;
                        playerPanel.setItem(i);
                    }
                    break;
                case 'f':
                    if( hero.hasKey() ){
                        hero.removeItem("Key");
                        hero.increaseLevel();
                        map.loadMap(hero.getLevel());
                    } else if(hero.hasHolocron() ){
                        gameState = "finishRoomOptions";
                        finishRoomOptions();
                    } else {
                        gameState = "traveling";
                    }
                    break;
                case'n':
                    textArea.setText("nothing");
                    break;
            }
        } else if(gameState.equals("drop") && hero.getNumItems() == 5){
            char c = e.getKeyChar();
            switch (c){
                case '1':
                    hero.removeItem(0);
                    break;
                case '2':
                    hero.removeItem(1);
                    break;
                case '3':
                    hero.removeItem(2);
                    break;
                case '4':
                    hero.removeItem(3);
                    break;
                case '5':
                    hero.removeItem(4);
                    break;
            }
            // Player has dropped an item
            if(hero.getNumItems() < 5){
                gameState = "traveling";
                textArea.setText("You picked up a " + i.getName());
                hero.pickUpItem(i);
                map.removeCharAtLoc(hero.getLocation());
            }
        }
    }

    /**
     * Set the panel gameState to a new state
     * @param gameState the new state of the game
     */
    public void setGameState(String gameState){
        this.gameState = gameState;
    }

    /**
     * Set the screen to allow user
     * either try to open the finish room door
     * or not
     */
    private void finishRoomOptions(){
        travelingState();
        forceButton.setVisible(true);
        dontUseForceButton.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

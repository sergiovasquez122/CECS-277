import java.util.Random;

/**
 * Force User class - constructs single instance of a Force user
 */
public class ForceUser extends Decorator implements Force {


    /**
     * ForceUser constructor - constructs single instance of a force user
     * @param e
     */
    public ForceUser(Enemy e){
        super(e, e.getName().contains("Force Fighter") ? e.getName() : e.getName() + " Force Fighter", 2);
    }

    /**
     * Calculates the damage to take the entity
     * @param e the entity to attack
     * @return the damage to the enemy
     */
    @Override
    public int attack(Entity e) {
        int initialDamage = super.attack(e);
        // force enemy has a 1/3 chance in using all different types of attacks
        final int BOUND = 3;
        Random random = new Random();
        int choice = random.nextInt(BOUND);

        int additionalDamage = 0;
        // 0 : regular attack
        // 1 : force push
        // 2 : force choke
        // 3 : force slam
        switch(choice){
            case 0:
                additionalDamage = forcePush();
                break;
            case 1:
                additionalDamage = forceChoke();
                break;
            case 2:
                additionalDamage = forceSlam();
                break;
        }

        e.takeDamage( additionalDamage );
        return additionalDamage + initialDamage;
    }

    /**
     * Calculates the amount of damage of a force push
     * @return the amount of damage of a force push
     */
    @Override
    public int forcePush() {
        double prob = Math.random();
        final double THRESHOLD = 0.5;

        // return different damage amount based on if probability reached certain threshold
        final int LOW_DAMAGE = 3;
        final int MEDIUM_DAMAGE = 5;
        if(Double.compare(prob, THRESHOLD) < 0){
            return LOW_DAMAGE * getLevel();
        } else {
            return MEDIUM_DAMAGE * getLevel();
        }
    }

    /**
     * Calculates the amount of damage of a force choke
     * @return the amount of damage of a force choke
     */
    @Override
    public int forceChoke() {
        final int MULTIPLIER = 2;
        return getLevel() * MULTIPLIER;
    }

    /**
     * Calculates the amount of damage of a force slam
     * @return the amount of damage of the force slam
     */
    @Override
    public int forceSlam() {
        int damage = (int) (Math.random() * getLevel()) + 1;
        double prob = Math.random();
        final double THRESHOLD = 0.75;
        /* return different damage amount based on if probability reached certain threshold
         * force slam is a high-risk high reward attack  */
        if (Double.compare(prob, THRESHOLD) < 0) {
            final int LOW_DAMAGE = 1;
            return LOW_DAMAGE;
        } else {
            final int MULTIPLIER = 2;
            return damage * MULTIPLIER;
        }
    }
}

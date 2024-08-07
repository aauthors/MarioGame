package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.items.Coin;
import game.items.Fire;
import game.items.Key;
import game.items.SuperMushroom;
import game.items.WeaponPart.GoombaLeather;
import game.items.WeaponPart.ShellFragment;
import game.status.Status;

/**
 * Special Action for attacking other Actors.
 * <p>
 * REQ3: Enemies
 * --	Relates to the ability to turn a Koopa into a dormant
 * shell on the map, and if the shell is attacke with a
 * Wrench and broken, drops a Super Mushroom. Otherwise,
 * it removes the Goomba from the map.
 * <p>
 * REQ4: Magical Items
 * --	Relates to Magical Items as, if the player has consumed
 * a Magical Item and gained a special status, then it
 * applies the correct actions towards the enemies.
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * The direction of incoming attack.
     */
    protected String direction;

    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor for a new AttackAction
     *
     * @param target    The target of the Attack
     * @param direction The direction of the target.
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Method that executes the action to be performed.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return result The String of what happened.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        // The weapon of the Actor performing the action.
        Weapon weapon = actor.getWeapon();

        // Checking if the hit is successful or not.
        if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }
        // Actor hits target
        if (actor.hasCapability(Status.POWERSTAREFFECT)) {
            if(target.hasCapability(Status.BOWSER)){
                map.locationOf(target).addItem(new Key());
            }
            map.removeActor(target);
            return actor + " OBLITERATES " + target;

            // if attacker is Bowser
        } else if (actor.hasCapability(Status.BOWSER)) {
            result = bowsersAttackActions(actor, map, result);
        } else if (target.hasCapability(Status.BOWSER)) {
            result = playerAttacksBowser(actor, map);
        } else {
            if (target.hasCapability(Status.POWERSTAREFFECT)) {
                return actor + " " + weapon.verb() + " " + target + " for 0 damage";
            } else {
                result = removeSuperMushroomEffect(actor, weapon);
                // check if target still conscious
                result = getString(actor, map, result);
            }
        }

        return result;
    }

    /**
     * method that performs the attacks on Bowser
     *
     * @param actor the actor acting
     * @param map   the map the actors are on
     * @return A string description of the actions that occurred.
     */
    private String playerAttacksBowser(Actor actor, GameMap map) {
        String result;
        // check bowser is alive
        if (!target.isConscious()) {
            map.locationOf(target).addItem(new Key());
            map.removeActor(target);
            result = "Bowser has been defeated! He has dropped a key!";
        } else {
            target.hurt(actor.getWeapon().damage());
            result = actor + " " + actor.getWeapon().verb() + " " + target + " for " + actor.getWeapon().damage();
        }
        return result;
    }

    /**
     * method that performs the attacks on the Player by bowser
     *
     * @param actor  the actor acting, in this case Bowser
     * @param map    the map the actors are on
     * @param result A string description of the actions that occurred.
     * @return A string description of the actions that occurred.
     */
    private String bowsersAttackActions(Actor actor, GameMap map, String result) {
        if (target.isConscious() && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            // punch the player and drop a fire on player position
            int damage = actor.getWeapon().damage();
            target.hurt(damage);
            map.locationOf(target).addItem(new Fire());
            result = actor + " " + actor.getWeapon().verb() + " " + target + " for " + damage + " damage.";
        }
        return result;
    }

    /**
     * Method that returns the actions that have been performed by an actor
     * attacking
     *
     * @param actor  the actor performing the action
     * @param map    the map the actors are on
     * @param result a String describing the actions that were performed.
     * @return A string description of the actions that occurred.
     */
    private String getString(Actor actor, GameMap map, String result) {
        if (!target.isConscious()) {
            dropActions(actor, map);
            // remove actor only if actor is not Koopa

            if (!target.hasCapability(Status.DORMANT)) {
                if (getSelectedWeapon(actor) == Status.WARIOSMININGPICKAXE) {
                    map.locationOf(actor).addItem(new Coin(true, rand.nextInt(50)));
                }
                if (target.hasCapability(Status.GOOMBA)) {
                    if (rand.nextInt(100) < 5) {
                        map.locationOf(target).addItem(new GoombaLeather());
                    }
                }

                map.removeActor(target);
                result += System.lineSeparator() + target + " is killed.";
            } else {
                // Since Koopa is defeated give SHELL status and display char = D
                result = koopaInteractions(actor, map, result);
            }
        }
        return result;
    }

    /**
     * Method that returns the actions concerning the interactions with Koopa
     *
     * @param actor  the actor acting
     * @param map    the map the actors are on.
     * @param result a String
     * @return A String describing the actions that were performed.
     */
    private String koopaInteractions(Actor actor, GameMap map, String result) {
        // check if actor has wrench in inv
        boolean hasWrench = actorHasWrench(actor);

        if (target.hasCapability(Status.SHELL) && hasWrench) {
            result = placeShell(map, result);
        } else if (target.hasCapability(Status.SHELL) && !hasWrench) {
            if (rand.nextInt(100) < 5) {
                map.locationOf(actor).addItem(new ShellFragment());
            }

            result += System.lineSeparator() + target + " was struck but nothing happened";
        } else {
            target.addCapability(Status.SHELL);
            result += System.lineSeparator() + target + " is now DORMANT";
        }
        return result;
    }

    /**
     * Method that checks if the actor has Wrench item
     *
     * @param actor The actor that is being checked for the Wrench
     * @return a Boolean value indicating whether a wrench was found.
     */
    private boolean actorHasWrench(Actor actor) {
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(Status.WRENCH)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that places shell down once Koopa shell has been broken
     *
     * @param map    The map the the actors are on.
     * @param result a description String.
     * @return a String that describes the actions that were performed.
     */
    private String placeShell(GameMap map, String result) {
        map.locationOf(target).addItem(new SuperMushroom(true));
        map.removeActor(target);
        result += System.lineSeparator() + target + " dropped a Magical Item!";
        return result;
    }

    /**
     * Method that drops actions and items.
     *
     * @param actor The actor performing the drops
     * @param map   The map the actor is on.
     */
    private void dropActions(Actor actor, GameMap map) {
        ActionList dropActions = new ActionList();
        // drop all items
        for (Item item : target.getInventory())
            dropActions.add(item.getDropAction(actor));

        for (Action drop : dropActions)
            drop.execute(target, map);
    }

    /**
     * Method that removes the Super Mushroom effect from the
     * Player once they have been hit and received damage.
     *
     * @param actor  The actor acting.
     * @param weapon The weapon belonging to the actor attacking
     * @return A String describing the actions performed.
     */
    private String removeSuperMushroomEffect(Actor actor, Weapon weapon) {
        String result;
        int damage = weapon.damage();
        result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        target.hurt(damage); // apply the damage to the target.
        weaponEffect(actor, weapon);
        if (target.hasCapability(Status.SUPERMUSHROOM)) {
            target.removeCapability(Status.SUPERMUSHROOM);
            target.removeCapability(Status.TALL);
        }
        return result;
    }

    private Status getSelectedWeapon(Actor actor) {

        for (Item item : actor.getInventory()) {
            if (item.hasCapability(Status.KINGBOOSSCEPTER)) {
                return Status.KINGBOOSSCEPTER;
            } else if (item.hasCapability(Status.WARIOSMININGPICKAXE)) {
                return Status.WARIOSMININGPICKAXE;
            } else if (item.hasCapability(Status.WALUIGISLUCKYBLADE)) {
                return Status.WALUIGISLUCKYBLADE;
            }
        }
        return null;

    }

    private void weaponEffect(Actor actor, Weapon weapon) {
        Status status = getSelectedWeapon(actor);
        if (status != null) {
            switch (status) {
                case KINGBOOSSCEPTER -> {
                    actor.heal(weapon.damage() / 2);
                    System.out.println(actor + " absorbs " + weapon.damage() / 2 + " hp from " + target);
                }


                case WALUIGISLUCKYBLADE -> {
                    if (rand.nextInt(100) < 45) {
                        target.hurt(weapon.damage() / 2);
                        System.out.println(actor + " critically strikes " + target + " for " + weapon.damage() / 2);
                    }
                }
            }
        }
    }


    /**
     * Method that returns a description of the actor performing the action.
     *
     * @param actor The actor performing the action.
     * @return String -- description of what happened.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction;
    }
}

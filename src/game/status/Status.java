package game.status;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 * <p>
 * Related to:
 * REQ2: Jump Up Super Star!
 * --   uses SUPERMUSHROOM to indicate that the Super Mushroom has been consumed
 * which gives the Player the ability to jump with 100% success rate.
 * <p>
 * REQ3: Enemies
 * --   uses HOSTILE_TO_ENEMY to indicate that the Player can attack the actor.
 * uses POWERSTAR to indicate that the Player has consumed a Power Star
 * and is invincible for a certain amount of time.
 * <p>
 * REQ4: Magical Items
 * --   See above, relates to Super Mushroom and Power Star
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL, // use this status to tell that current instance has "grown".
    SUPERMUSHROOM, //use this status to tell that current instance has consumed a Super Mushroom
    POWERSTAR,          //Identifies an item as a 'POWERSTAR'
    POWERSTAREFFECT,    // use this to indicate the Actor is currently under the effect of a Power Star
    CONSUMED,           // use this to indicate that a item has been consumed by the actor
    RESET,              // use this status to tell whether an actor has already performed a 'ResetAction'
    WALLET,             // Identity's an item as a 'Wallet' (ONLY USE WITH WALLET CLASS)
    WRENCH,             // Identity's an item as a 'Wrench' (ONLY USE WITH Wrench CLASS)
    HASWRENCH,          // use this to indicate that the player currently has a Wrench in their inventory
    COIN,               // Identity's an item as a 'COIN' (ONLY USE WITH Coin CLASS)
    TREE,               // Identity's a ground as a 'Tree' (ONLY USE FOR CLASSES THAT INHERIT 'Tree)
    DIRT,               //Identifies a ground as 'Dirt' (ONLY USE WITH Dirt CLASS)
    HIGH,               // use this for terrain that is above MEDIUM
    UNJUMPABLE,         // use this on enemies to indicate inability to perform jump action.
    SAPLINGTYPE,        // use to indicate the type of terrain for jump
    SPROUTTYPE,         // use to indicate the type of terrain for jump
    WALLTYPE,           // use to indicate the type of terrain for jump
    DIRTTYPE,           // use to indicate the type of terrain for jump
    FLOORTYPE,          // use to indicate the type of terrain for jump
    ENTER_FLOOR,        // Use this to indicate that an actor can enter Floor.
    SHELL,              // use this to indicate that the koopa is now a shell.
    DORMANT,            // use this status to indicate that the enemy can be a dormant Kopa
    HOSTILE_TO_PLAYER,  // use this status to differentiate between player and enemy (enemy cannot attack another enemy) .
    POWER_WATER,
    INCREASED_DAMAGE,
    BOTTLE,
    GOOMBALEATHER,      // Identifies an item as 'Goomba Leather'
    SHELLFRAGMENT,      // Identifies an item as 'Shell Fragment'
    TREEBARK,           // Identifies an item as 'Tree Bark'
    KINGBOOSSCEPTER,    // Identifies a weapon as 'King Boos Scepter'
    WALUIGISLUCKYBLADE, // Identifies a weapon as 'Waluigis Lucky Blade'
    WARIOSMININGPICKAXE,// Identifies a weapon as 'Warios Mining Pickaxe'
    WEAPON,
    GOOMBA,             // Identifies a class as 'Goomba'
    GAMBLEABLE,         // Specifies whether the class object can be interacted to gamble with
    FLY,                // use this status to indicate that the actor can fly.
    BOWSER,             // use this to show that the actor is bowser, used in attack action for fire punch attack.
    KEY,                // use this for the Key class. indicates that the item is a key.
}

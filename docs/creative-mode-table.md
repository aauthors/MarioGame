# Requirement 4

**Title**:
_Gambling_

**Description**:
_This requirement adds the functionality of being able to gamble in the game using the coins 
collected. This is achieved through implementing a 'Slot Machine' which will be spawned around
the map and have an assigned value corresponding to each one. From which the player will have
an action to gamble their coins and a display is outputted displaying the pairings of various 
slot elements. The corresponding elements in the slot machine possess varied assigned 'occurrences'
per spin as well has having corresponding 'multipliers' for the inserted value._

**Explanation why it adheres to SOLID principles** (WHY):
_The requirement adheres to the solid principles due to it possessing the convention of 'Single 
Responsibility'. It does such by having a slot machine ground class that's souly responsible for 
being the entity and instantiating the price as well as having a corresponding slot machine gamble 
action that separates the main functional complexities. The design obeys the open close principle by
separating the actions into creating an internal 'SlotElement' class. This allows for configuration
and expansion of new slot elements with difference 'Occurrences' and 'Values' without needing to 
reconfigure the action. It also adheres to Liskov's substitution rule as the Casino Action does not 
lose any functionality that the original parent the action provides. As such it retains all of its 
gambling functionality and outputs its display using the abstract action's execute method. Does not 
violate the interface segregation principle as the requirement does not define any new interfaces. 
Additionally, the requirement does not break the Dependency inversion principle either as there are
no higher level modules depending on lower level modules as well as not having any abstractions that
depend upon details_-

| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| ----------------------------------------------------------------------------------------------------------------------- |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Must use at least two (2) classes from the engine package                                                               | This requirement will use the following classes from the engine package:<br/>-Action Class: This will be used for the extending the SlotMachineAction from. <br/> - Ground Class. This will be used to extend the Slot Machine from to create the object on the map.                                                                                                                                                                                                                                                                                                                                                 |
| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | The trading system developed for the Toad in assignment 2 implemented a wallet system as well as a set of actions to allow the actor to purchase items. This requirement extends of that ground work by have the 'SlotMachineAction' extending from the trade action interface and using the Wallet class in the transaction. The reason being that the action of gambling with the coins can be thought as an exchange. By extending from the trade action the details of how such a wallet is obtained is seperated out of the scope of the action allowing for the gambling to just specify how much is lost/won. |
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                  | The SlotMachine action makes use of the 'TradeAction' interface. Doing such seperated the responsibility of managing the wallet and further allows for any alterations for the wallet system to be easily done abstracting the gambling class from the responsiblity.                                                                                                                                                                                                                                                                                                                                                |
| Must use existing or create new capabilities                                                                            | The capability was added to identifing the slot machine as 'Gambleable'. As such it shows that such a class can be interacted with from the player to recieve an action in which they can gamble with.                                                                                                                                                                                                                                                                                                                                                                                                               |

---

# Requirement 5


**Title**:
_Unique Weapon Crafting_

**Description**:
_This requirement will allow the user to collect unique items that have a rare drop chance
and obtained through different methods (or bought from a new vendor, Toadette) which will then allow the player
to craft a selection of 3 new unique weapons, with different effects upon hit/kill_

**Explanation why it adheres to SOLID principles** (WHY):
It would adhere to the Single Responsibility as the entire purpose of this requirement is to craft unique weapons, the open
closed principle will be adhered to, as the weapon and item classes will be closed but available to be used by other classes (ie. Craft Action and Attack action)
It would adhere to Liskov's substitutions, as the Weapon classes and Item classes would not result in any undefined behaviour if it was substituted with it's 
parent counterpart. Furthermore, the interface segregation is also achieved as interfaces are used during the trade action that occurs with the new vendor. Toadette 
-

| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----------------------------------------------------------------------------------------------------------------------- |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Must use at least two (2) classes from the engine package                                                               | This requirement will use the following classes from the engine package:<br/>- Item Class. This will be used for the new Unique Weapons and the weapon parts required for its craft <br/>- Action Class: This will be used for the action used to actually craft the weapons<br/>- Ground Class; This will be used to generate a new Anvil ground that is used to craft the weapons                                                                                                                                                                                                                                                                                |
| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | The developed attack action feature from assignment 2 was developed upon to ensure that specific items would drop from killing or attacking specific enemies, and that the effect of the unique weapons would perform correctly. For example, changes were made to the attack action class such as new methods to obtain the players current weapon to ensure that the right buff is given to the player after they kill an enemy or land a hit, furthermore, new conditions were made to identify the enemy killed to see if the Goomba Leather would drop, this is similar to when Mario hits the dormant shell, and a shell fragment may drop as a result of it |
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                  | This feature introduced a new vendor, Toadette to sell the crafting items are an enlarged price given the fact that they are so rare. Doing this will mean that the created interface, TradeAction will be used to ensure that Toadette is able to set up shop and sell us the items                                                                                                                                                                                                                                                                                                                                                                               |
| Must use existing or create new capabilities                                                                            | New Capabilities are added to identify the weapons used to ensure that the correct buff is given to the user during the attack action, but also to identify that a specific action has occured which will trigger a drop of an item. For example, the dormant Capability will be reused, as when the player hits the shell without a wrench, there is a chance that the Koopa will also drop a shell fragment.                                                                                                                                                                                                                                                     |

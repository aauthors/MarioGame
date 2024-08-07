package game.npc;

import edu.monash.fit2099.engine.actors.Actor;
import java.util.List;

public interface Speakable {
    public String getLine(Actor caller);

    public List<String> availableDialogue(Actor caller);
}

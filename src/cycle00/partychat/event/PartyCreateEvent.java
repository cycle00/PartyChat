package cycle00.partychat.event;

import com.sun.istack.internal.NotNull;
import cycle00.partychat.data.Party;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyCreateEvent extends Event implements Cancellable {
    private final Party party;
    private boolean isCancelled;

    public PartyCreateEvent(Party party) {
        this.party = party;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Party getParty() {
        return this.party;
    }
}

package cycle00.partychat;

import cycle00.partychat.commands.GlobalChatCommand;
import cycle00.partychat.commands.PartyChatCommand;
import cycle00.partychat.commands.PartyCommand;
import cycle00.partychat.event.PlayerChatListener;
import cycle00.partychat.event.PlayerJoinLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyChat extends JavaPlugin {

    // For anybody reading this, I sincerely apologise for the shitshow that is this code.
    public static PartyChat instance;
    public static PartyChat getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        // Commands
        new PartyCommand(this);
        new PartyChatCommand(this);
        new GlobalChatCommand(this);

        // Listeners
        new PlayerChatListener(this);
        new PlayerJoinLeaveListener(this);
    }
}

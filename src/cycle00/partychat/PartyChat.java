package cycle00.partychat;

import cycle00.partychat.commands.PartyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyChat extends JavaPlugin {

    public static PartyChat instance;
    public static PartyChat getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        new PartyCommand(this);
    }
}

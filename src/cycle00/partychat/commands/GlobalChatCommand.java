package cycle00.partychat.commands;

import cycle00.partychat.PartyChat;
import cycle00.partychat.event.PlayerChatListener;
import cycle00.partychat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalChatCommand implements CommandExecutor {

    PartyChat plugin;
    public GlobalChatCommand(PartyChat plugin) {
        this.plugin = plugin;
        plugin.getCommand("globalchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cThis is not a console command."));
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Utils.chat("&cPlease write the message you want to say."));
            return false;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        PlayerChatListener.fromCommand = true;
        player.chat(message.toString());

        return true;
    }
}

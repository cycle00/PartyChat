package cycle00.partychat.commands;

import cycle00.partychat.PartyChat;
import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyChatCommand implements CommandExecutor {

    PartyChat plugin;
    public static Map<Player, Boolean> inPartyChat = new HashMap<>();

    public PartyChatCommand(PartyChat plugin) {
        this.plugin = plugin;
        plugin.getCommand("partychat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cThis is not a console command."));
            return true;
        }

        Player player = (Player) sender;

        if (!Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou are not in a party."));
            return true;
        }

        Party party = Party.getParty(player);

        if (args.length == 0) {
            if (!(inPartyChat.get(player))) {
                inPartyChat.replace(player, true);
                player.sendMessage(Utils.chat("&aYou have gone into &9party &achat."));
            } else {
                inPartyChat.replace(player, false);
                player.sendMessage(Utils.chat("&aYou have gone into &9global &achat."));
            }
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member != null) {
                member.sendMessage(Utils.chat("&9Party &8> &f<" + player.getDisplayName() + "> " + message));
            }
        }

        return true;
    }
}

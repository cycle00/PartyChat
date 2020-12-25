package cycle00.partychat.commands;

import cycle00.partychat.PartyChat;
import cycle00.partychat.commands.party.PartyAdd;
import cycle00.partychat.commands.party.PartyCreate;
import cycle00.partychat.commands.party.PartyList;
import cycle00.partychat.utils.TabCompleterBase;
import cycle00.partychat.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartyCommand implements CommandExecutor, TabExecutor {

    PartyChat plugin;
    public PartyCommand(PartyChat plugin) {
        this.plugin = plugin;
        plugin.getCommand("party").setExecutor(this);
        plugin.getCommand("party").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cThis is not a console command.")); return true;
        }
        Player player = (Player) sender;

        if (args.length > 0) {
            switch (args[0]) {
                case "create":
                    PartyCreate.execute(player);
                    break;
                case "list":
                    PartyList.execute(player);
                    break;
                case "add":
                    PartyAdd.execute(player, args);
                    break;

                default:
                    player.sendMessage(Utils.chat("&cUnknown Command."));
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        //region args
        arguments.add("create"); arguments.add("list"); arguments.add("add");
        //endregion

        if (args.length == 1) {
            return TabCompleterBase.filterStartingWith(args[0], arguments);
        } else {
            switch (args[0]) {
                case "add":
                    return TabCompleterBase.getOnlinePlayers(args[1]).stream().filter(Objects::nonNull).collect(Collectors.toList());
            }
        }

        return null;
    }
}

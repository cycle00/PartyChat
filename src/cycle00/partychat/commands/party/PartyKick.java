package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class PartyKick {
    public static void execute(Player player, String[] args) {
        if (!Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou are not in a party."));
            return;
        }

        if (Party.getLeader(Party.getParty(player)) != player.getUniqueId()) {
            player.sendMessage(Utils.chat("&cOnly the party leader can perform this command."));
            return;
        }

        if (args.length == 1) {
            player.sendMessage(Utils.chat("&cPlease specify a player to kick."));
            return;
        }

        execute(player, args[1]);
    }

    public static void execute(Player player, String toKick) {
        OfflinePlayer playerToKick = null;
        for (OfflinePlayer joined : Bukkit.getOfflinePlayers()) {
            if (Objects.equals(joined.getName(), toKick)) {
                playerToKick = joined;
                break;
            }
        }

        if (playerToKick == null) {
            player.sendMessage(Utils.chat("&cThat player does not exist."));
            return;
        }

        if (Party.getParty(player.getUniqueId()) != Party.getParty(playerToKick.getUniqueId())) {
            player.sendMessage(Utils.chat("&cYou are not in the same party as the player you want to kick."));
            return;
        }

        //if all checks pass
        Party party = Party.getParty(player.getUniqueId());
        party.remove(playerToKick.getUniqueId());
        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member != null) {
                member.sendMessage(Utils.chat("&f" + playerToKick.getName() + "&c has been kicked from the party by &f" + player.getName()));
            }
        }
        if (playerToKick.isOnline()) {
            Player onlinePlayerToKick = (Player) playerToKick;
            onlinePlayerToKick.sendMessage(Utils.chat("&cYou have been kicked from the party by &f" + player.getName()));
        }
    }
}

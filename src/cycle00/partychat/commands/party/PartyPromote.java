package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class PartyPromote {
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
            player.sendMessage(Utils.chat("&cPlease specify a player to promote."));
            return;
        }

        execute(player, args[1]);
    }

    public static void execute(Player player, String toPromote) {
        OfflinePlayer playerToPromote = null;
        for (OfflinePlayer joined : Bukkit.getOfflinePlayers()) {
            if (Objects.equals(joined.getName(), toPromote)) {
                playerToPromote = joined;
                break;
            }
        }

        if (playerToPromote == null) {
            player.sendMessage(Utils.chat("&cThat player does not exist."));
            return;
        }

        if (Party.getParty(player.getUniqueId()) != Party.getParty(playerToPromote.getUniqueId())) {
            player.sendMessage(Utils.chat("&cYou are not in the same party as the player you want to promote."));
        }

        // if all checks pass
        Party party = Party.getParty(player);

        party.setLeader(playerToPromote.getUniqueId());

        UUID newLeader = playerToPromote.getUniqueId();
        party.members.remove(playerToPromote.getUniqueId());
        party.members.add(0, newLeader);

        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member != null) {
                member.sendMessage(Utils.chat("&f" + player.getDisplayName() + " &ahas promoted &f" + playerToPromote.getName() + " &ato party leader."));
            }
        }
    }
}

// TODO: test this

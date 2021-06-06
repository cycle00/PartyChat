package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyLeave {
    public static void execute(Player player) {
        if (!Party.inParty(player)) {
            if (player.isOnline()) {
                player.sendMessage(Utils.chat("&cYou are not in a party.")); return;
            }
        }

        Party party = Party.getParty(player);

        try {
            if (Party.getLeader(party) == player.getUniqueId()) {
                party.setLeader(party.members.get(1));
                for (UUID memberUUID : party.members) {
                    Player member = Bukkit.getPlayer(memberUUID);
                    if (member != null) {
                        member.sendMessage(Utils.chat("&f" + player.getDisplayName() + " &ais now party leader."));
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) { }

        party.remove(player);
        if (player.isOnline()) {
            player.sendMessage(Utils.chat("&aYou have left the party."));
        }

        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member != null) {
                member.sendMessage(Utils.chat("&f" + player.getDisplayName() + " &chas left the party."));
            }
        }
    }
}

// TODO: add /p promote and test this
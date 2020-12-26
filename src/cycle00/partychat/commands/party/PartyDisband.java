package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyDisband {
    public static void execute(Player player) {
        if (!Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou are not in a party."));
            return;
        }

        Party party = Party.getParty(player);
        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member != null) {
                member.sendMessage(Utils.chat("&f" + player.getName() + " &chas disbanded the party."));
            }
        }
        Party.disband(party);
    }
}

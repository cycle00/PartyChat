package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyList {

    public static void execute(Player player) {
        if (!Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou are not in a party.")); return;
        }

        Party party = Party.getParty(player);

        StringBuilder members = new StringBuilder();
        for (UUID memberUUID : party.members) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(memberUUID);
            if (member.isOnline()) {
                members.append(Utils.chat("&a•&f " + member.getName() + ", "));
            } else {
                members.append(Utils.chat("&c•&f " + member.getName() + ", "));
            }
        }

        String cleanMembers = members.toString().trim();
        cleanMembers = cleanMembers.substring(0, cleanMembers.length() - 1);

        player.sendMessage(Utils.chat("&6Members&f: " + cleanMembers));
    }
}

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
        String leader = "";
        for (UUID memberUUID : party.members) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(memberUUID);

            if (member.getUniqueId() == party.leader) {
                if (member.isOnline()) {
                    leader = "&a•&f " + member.getName();
                } else {
                    leader = "&c•&f " + member.getName();
                }
            } else {
                if (member.isOnline()) {
                    members.append(Utils.chat("&a•&f " + member.getName() + ", "));
                } else {
                    members.append(Utils.chat("&c•&f " + member.getName() + ", "));
                }
            }
        }

        player.sendMessage(Utils.chat("&6Leader&f:\n" + leader + "\n"));

        if (!members.isEmpty()) {
            String cleanMembers = members.toString().trim();
            cleanMembers = cleanMembers.substring(0, cleanMembers.length() - 1);
            player.sendMessage(Utils.chat("\n&6Members&f:\n" + cleanMembers));
        }
    }
}

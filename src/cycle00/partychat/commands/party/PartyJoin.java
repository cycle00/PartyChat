package cycle00.partychat.commands.party;

import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyJoin {
    public static void execute(Player player, String[] args) {
        if (Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou are already in a party. Do &f/p leave&c to be able to join a different party."));
            return;
        }

        if (args.length == 1) {
            player.sendMessage(Utils.chat("&cPlease specify the name of the player you want to join."));
            return;
        }

        execute(player, args[1]);
    }

    public static void execute(Player player, String toJoin) {
        Player inviter = Bukkit.getPlayer(toJoin);

        Party party;
        if (inviter != null) {
            party = Party.getParty(inviter);
        }
        else {
            player.sendMessage(Utils.chat("&cThat player is offline."));
            return;
        }
        if (party == null) {
            player.sendMessage(Utils.chat("&cThat player is not in a party."));
            return;
        }

        if (party.pendingInvitations.size() > 0) {
            if (party.pendingInvitations.contains(player)) {
                party.pendingInvitations.remove(player);
                for (UUID memberUUID : party.members) {
                    Player member = Bukkit.getPlayer(memberUUID);
                    if (member != null) {
                        member.sendMessage(Utils.chat("&6" + player.getDisplayName() + "&a has joined the party."));
                    }
                }
                party.add(player);
                player.sendMessage(Utils.chat("&aYou have successfully joined &f" + inviter.getDisplayName() + "'s &aparty."));
                return;
            }
        }

        player.sendMessage(Utils.chat("&cYou do not have an invitation from this player."));
    }
}

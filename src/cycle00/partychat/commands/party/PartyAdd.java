package cycle00.partychat.commands.party;

import cycle00.partychat.PartyChat;
import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyAdd {
    public static void execute(Player player, String[] args) {
        if (!Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou have to be in a party to use this command. Run &f/p create &cto create a party."));
        }

        if (args.length == 1) {
            player.sendMessage(Utils.chat("&cPlease specify a player to add.")); return;
        }

        execute(player, args[1]);
    }

    public static void execute(Player player, String newPlayer) {
        Party party = Party.getParty(player.getUniqueId());
        Player invitee = Bukkit.getPlayer(newPlayer);
        if (invitee == null) {
            player.sendMessage(Utils.chat("&cThat specified player is not online.")); return;
        }

        if (invitee == player) {
            player.sendMessage(Utils.chat("&cYou cannot add yourself.")); return;
        }

        for (UUID memberUUID : party.members) {
            Player member = Bukkit.getPlayer(memberUUID);
            if (member == invitee) {
                player.sendMessage(Utils.chat("&cThat player is already in the party."));
            }
        }

        TextComponent msg = new TextComponent(Utils.chat("&aClick here to join &f" + player.getDisplayName() + "'s&a party."));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p join " + player.getDisplayName()));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Utils.chat("&aClick to join &f" + player.getDisplayName() + "'s &aparty."))));
        invitee.spigot().sendMessage(msg);
        party.pendingInvitations.add(invitee);
        player.sendMessage(Utils.chat("&aSuccessfully sent party invite to &f" + invitee.getDisplayName() + "&a."));

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PartyChat.instance, () -> {
            if (party.pendingInvitations.contains(invitee)) {
                party.pendingInvitations.remove(invitee);
                player.sendMessage(Utils.chat("&cThe party invite to &f" + invitee.getDisplayName() + "&c has expired."));
                invitee.sendMessage(Utils.chat("&cThe party invite from &f" + player.getDisplayName() + "&c has expired."));
            }
        }, 1200);
    }
}

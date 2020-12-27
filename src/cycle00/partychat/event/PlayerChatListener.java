package cycle00.partychat.event;

import cycle00.partychat.PartyChat;
import cycle00.partychat.commands.PartyChatCommand;
import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    public static boolean fromCommand = false;

    public PlayerChatListener(PartyChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PartyChatCommand.inPartyChat.get(player) != null) {
            if (Party.inParty(player) && PartyChatCommand.inPartyChat.get(player) && !fromCommand) {
                event.setCancelled(true);
                String message = event.getMessage();
                Party party = Party.getParty(player);

                for (UUID memberUUID : party.members) {
                    Player member = Bukkit.getPlayer(memberUUID);
                    if (member != null) {
                        member.sendMessage(Utils.chat("&9Party &8> &f<" + player.getDisplayName() + "> " + message));
                    }
                }
            }

            fromCommand = false;

            if (!Party.inParty(player) && PartyChatCommand.inPartyChat.get(player)) {
                event.setCancelled(true);
                PartyChatCommand.inPartyChat.replace(player, false);
                player.sendMessage(Utils.chat("&cSince you are not in a party, you have been moved to &9global&c chat"));
            }
        }
    }
}

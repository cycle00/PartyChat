package cycle00.partychat.event;

import cycle00.partychat.PartyChat;
import cycle00.partychat.commands.party.PartyKick;
import cycle00.partychat.commands.party.PartyLeave;
import cycle00.partychat.data.Party;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinLeaveListener implements Listener {

    boolean cancelDisconnect = false;

    public PlayerJoinLeaveListener(PartyChat plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        cancelDisconnect = true;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (Party.inParty(event.getPlayer())) {
            cancelDisconnect = false;
            Party party = Party.getParty(event.getPlayer().getUniqueId());
            for (UUID memberUUID : party.members) {
                Player member = Bukkit.getPlayer(memberUUID);
                if (member != null)
                    member.sendMessage(Utils.chat("&f" + event.getPlayer().getDisplayName() + " &chas disconnected. They have 5 minutes to reconnect before they get kicked from the party."));
            }

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(PartyChat.instance, () -> {
                if (!cancelDisconnect) {
                    PartyLeave.execute(event.getPlayer());
                    for (UUID memberUUID : party.members) {
                        Player member = Bukkit.getPlayer(memberUUID);
                        if (member != null) {
                            member.sendMessage(Utils.chat("&f" + event.getPlayer().getDisplayName() + " &chas been kicked from the party."));
                        }
                    }
                }
            }, 6000);
        }
    }
}

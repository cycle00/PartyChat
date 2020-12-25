package cycle00.partychat.commands.party;

import cycle00.partychat.PartyChat;

import cycle00.partychat.data.Party;
import cycle00.partychat.event.PartyCreateEvent;
import cycle00.partychat.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyCreate {

    private static boolean cancelled = false;
    PartyChat plugin = PartyChat.getInstance();

    public static void execute(Player player) {
        if (Party.inParty(player)) {
            player.sendMessage(Utils.chat("&cYou already are in a party. Do &l&f/p leave &r&cto be able to create another."));
            return;
        }

        Party party = new Party(player);

        Bukkit.getScheduler().runTask(PartyChat.instance, () -> {
            PartyCreateEvent pce = new PartyCreateEvent(party);
            Bukkit.getPluginManager().callEvent(pce);
            if (pce.isCancelled())
                cancelled = true;
        });

        Bukkit.getScheduler().scheduleSyncDelayedTask(PartyChat.instance, () -> {
             if (cancelled) {
                 cancelled = false;
                 return;
             }
             Party.partyMap.put(player.getUniqueId(), party);
             player.sendMessage(Utils.chat("&6Successfully created the party."));
        }, 2);
    }
}

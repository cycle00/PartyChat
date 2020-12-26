package cycle00.partychat.data;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Party {
    public static Map<UUID, Party> partyMap = new HashMap<>();
    public List<UUID> members = new ArrayList<>();
    public List<Player> pendingInvitations = new ArrayList<>();

    public Party(Player member) {
        this.members.add(member.getUniqueId());
    }

    public void add(Player member) {
        if (!this.members.contains(member.getUniqueId())) {
            this.members.add(member.getUniqueId());
            partyMap.put(member.getUniqueId(), this);
        }
    }

    public void remove(Player member) {
        this.members.remove(member.getUniqueId());
        partyMap.remove(member.getUniqueId());
    }

    public void remove(UUID member) {
        this.members.remove(member);
        partyMap.remove(member);
    }

    public static void disband(Party party) {
        if (party.members.size() == 1) {
            partyMap.values().remove(party);
            party.members.clear();
        } else {
            for (UUID memberUUID : party.members) {
                party.remove(memberUUID);
            }
        }
    }

    public static Party getParty(Player player) {
        return partyMap.get(player.getUniqueId());
    }

    public static Party getParty(UUID uuid) {
        return partyMap.get(uuid);
    }

    public static boolean inParty(Player player) {
        return partyMap.containsKey(player.getUniqueId());
    }
}

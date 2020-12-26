package cycle00.partychat.data;

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

    public List<UUID> list() {
        return this.members;
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

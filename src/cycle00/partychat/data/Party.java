package cycle00.partychat.data;

import cycle00.partychat.commands.PartyChatCommand;
import org.bukkit.entity.Player;

import java.util.*;

public class Party {
    public static Map<UUID, Party> partyMap = new HashMap<>();
    public List<UUID> members = new ArrayList<>();
    public UUID leader;
    public List<Player> pendingInvitations = new ArrayList<>();

    public Party(Player member) {
        this.members.add(member.getUniqueId());
        PartyChatCommand.inPartyChat.put(member, false);
    }

    public void setLeader(Player player) {
        this.leader = player.getUniqueId();
    }

    public void setLeader(UUID player) {
        this.leader = player;
    }

    public void add(Player member) {
        if (!this.members.contains(member.getUniqueId())) {
            this.members.add(member.getUniqueId());
            partyMap.put(member.getUniqueId(), this);
            PartyChatCommand.inPartyChat.put(member, false);
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
        } else {
            for (UUID memberUUID : party.members) {
                party.remove(memberUUID);
            }
        }

        party.members.clear();
    }

    public static UUID getLeader(Party party) {
        return party.leader;
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

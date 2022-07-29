package net.nonswag.tnl.trolling.listeners;

import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;
import net.nonswag.tnl.listener.events.PlayerPacketEvent;
import net.nonswag.tnl.trolling.api.troll.Troll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class PacketListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPacket(@Nonnull PlayerPacketEvent event) {
        if (event.getDirection().isIncoming()) {
            if (Troll.NO_INCOMING_PACKETS.isVictim(event.getPlayer()) || Troll.TIMEOUT.isVictim(event.getPlayer())) {
                if (!(event.getPacket() instanceof PacketPlayInKeepAlive)) event.setCancelled(true);
            }
        } else {
            if (Troll.NO_CHUNK_LOADING.isVictim(event.getPlayer()) && event.getPacket() instanceof PacketPlayOutMapChunk) {
                event.setCancelled(true);
            } else if (Troll.NO_OUTGOING_PACKETS.isVictim(event.getPlayer()) || Troll.TIMEOUT.isVictim(event.getPlayer())) {
                if (!(event.getPacket() instanceof PacketPlayOutKeepAlive) && !(event.getPacket() instanceof PacketPlayOutKickDisconnect)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

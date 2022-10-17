package net.nonswag.tnl.trolling.listeners;

import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.TNLPlayerJoinEvent;
import net.nonswag.tnl.trolling.api.troll.Troll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(@Nonnull TNLPlayerJoinEvent event) {
        TNLPlayer player = event.getPlayer();
        Troll.TROLLS.forEach(troll -> {
            if (troll.isVictim(player)) troll.addVictim(player);
        });
    }
}

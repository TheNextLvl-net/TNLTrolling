package net.nonswag.tnl.trolling.listeners;

import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.TNLPlayerJoinEvent;
import net.nonswag.tnl.trolling.api.troll.Troll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(@Nonnull TNLPlayerJoinEvent event) {
        TNLPlayer player = event.getPlayer();
        for (Troll troll : Troll.getTrolls()) if (troll.isVictim(player)) troll.addVictim(player);
    }
}

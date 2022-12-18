package net.nonswag.tnl.trolling.listeners;

import net.nonswag.tnl.listener.events.TNLPlayerJoinEvent;
import net.nonswag.tnl.trolling.api.troll.Troll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(TNLPlayerJoinEvent event) {
        Troll.TROLLS.forEach(troll -> {
            if (troll.isVictim(event.getPlayer())) troll.addVictim(event.getPlayer());
        });
    }
}

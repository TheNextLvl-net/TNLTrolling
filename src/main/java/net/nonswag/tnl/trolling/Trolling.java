package net.nonswag.tnl.trolling;

import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.trolling.commands.TrollCommand;
import net.nonswag.tnl.trolling.listeners.JoinListener;
import net.nonswag.tnl.trolling.listeners.PacketListener;

public class Trolling extends TNLPlugin {

    @Override
    public void onEnable() {
        getCommandManager().registerCommands(new TrollCommand());
        getEventManager().registerListener(new PacketListener());
        getEventManager().registerListener(new JoinListener());
    }
}

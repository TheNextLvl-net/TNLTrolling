package net.nonswag.tnl.trolling;

import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.trolling.commands.TrollCommand;
import net.nonswag.tnl.trolling.listeners.JoinListener;
import net.nonswag.tnl.trolling.listeners.PacketListener;

public class Trolling extends TNLPlugin {

    @Override
    public void onEnable() {
        CommandManager.registerCommands(new TrollCommand());
        EventManager eventManager = EventManager.cast(this);
        eventManager.registerListener(new PacketListener());
        eventManager.registerListener(new JoinListener());
    }
}

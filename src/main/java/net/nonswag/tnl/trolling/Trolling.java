package net.nonswag.tnl.trolling;

import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.trolling.commands.TrollCommand;
import net.nonswag.tnl.trolling.listeners.JoinListener;
import net.nonswag.tnl.trolling.listeners.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Trolling extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandManager.registerCommands(new TrollCommand());
        EventManager eventManager = EventManager.cast(this);
        eventManager.registerListener(new PacketListener());
        eventManager.registerListener(new JoinListener());
    }
}
